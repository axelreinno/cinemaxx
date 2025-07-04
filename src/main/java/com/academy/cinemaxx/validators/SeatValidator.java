package com.academy.cinemaxx.validators;

import com.academy.cinemaxx.dtos.request.BookingSeatsRequestDTO;
import com.academy.cinemaxx.entities.Seat;
import com.academy.cinemaxx.entities.Showtime;
import com.academy.cinemaxx.enums.SeatStatus;
import com.academy.cinemaxx.repositories.BookingSeatRepository;
import com.academy.cinemaxx.repositories.SeatRepository;
import com.academy.cinemaxx.repositories.ShowtimeRepository;
import com.academy.cinemaxx.repositories.UserRepository;
import com.academy.cinemaxx.validators.annotations.ValidSeats;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SeatValidator implements ConstraintValidator<ValidSeats, BookingSeatsRequestDTO> {
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final BookingSeatRepository bookingSeatRepository;

    public SeatValidator(
            SeatRepository seatRepository,
            ShowtimeRepository showtimeRepository,
            BookingSeatRepository bookingSeatRepository,
            UserRepository userRepository
    ) {
        this.seatRepository = seatRepository;
        this.showtimeRepository = showtimeRepository;
        this.bookingSeatRepository = bookingSeatRepository;
    }

    @Override
    public boolean isValid(BookingSeatsRequestDTO request, ConstraintValidatorContext context) {
        if (request == null || request.showtimeId() == null || request.seatIds() == null) {
            return true;
        }

        Optional<Showtime> showtimeOpt = showtimeRepository.findBySecureId(request.showtimeId());
        if (showtimeOpt.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Showtime not found")
                    .addPropertyNode("showtimeId")
                    .addConstraintViolation();
            return false;
        }

        List<Seat> selectedSeats = seatRepository.findBySecureIdIn(request.seatIds());
        if (selectedSeats.size() != request.seatIds().size()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("One or more seats not found")
                    .addPropertyNode("seatIds")
                    .addConstraintViolation();
            return false;
        }

        List<Seat> unavailableSeats = selectedSeats.stream()
                .filter(seat -> seat.getStatus() != SeatStatus.AVAILABLE)
                .toList();

        if (!unavailableSeats.isEmpty()) {
            String seatLabels = unavailableSeats.stream()
                    .map(Seat::getLabel)
                    .collect(Collectors.joining(", "));

            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Selected seats are not available: " + seatLabels)
                    .addPropertyNode("seatIds")
                    .addConstraintViolation();
            return false;
        }

        List<Long> seatIds = selectedSeats.stream()
                .map(Seat::getId)
                .toList();

        var existingBookings = bookingSeatRepository.findExistingBookingsForSeats(request.showtimeId(), seatIds);
        if (!existingBookings.isEmpty()) {
            List<String> bookedSeats = existingBookings.stream()
                    .map(bs -> bs.getSeat().getLabel())
                    .collect(Collectors.toList());

            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Selected seats are already taken: " + String.join(", ", bookedSeats))
                    .addPropertyNode("seatIds")
                    .addConstraintViolation();
            return false;
        }


        List<Seat> allSeatsInHall = showtimeOpt.get().getHall().getSeats();
        var bookedSeats = bookingSeatRepository.findBookedSeatsByShowtimeSecureId(request.showtimeId());
        Set<Long> bookedSeatIds = bookedSeats.stream()
                .map(bs -> bs.getSeat().getId())
                .collect(Collectors.toSet());

        Map<Integer, List<Seat>> seatsByRow = allSeatsInHall.stream()
                .collect(Collectors.groupingBy(Seat::getRow));

        for (Map.Entry<Integer, List<Seat>> entry : seatsByRow.entrySet()) {
            Integer row = entry.getKey();
            List<Seat> rowSeats = entry.getValue().stream()
                    .filter(seat -> "SEAT".equals(seat.getSeatElement().getCode()))
                    .sorted(Comparator.comparing(Seat::getColumn))
                    .toList();

            List<Seat> selectedInRow = selectedSeats.stream()
                    .filter(seat -> seat.getRow().equals(row))
                    .toList();

            if (!selectedInRow.isEmpty() && !isValidSeatArrangement(selectedInRow, rowSeats, bookedSeatIds)) {
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate("Cannot leave a single empty seat between occupied seats")
                        .addPropertyNode("seatIds")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }

    private boolean isValidSeatArrangement(List<Seat> selectedSeats, List<Seat> allSeatsInRow, Set<Long> bookedSeatIds) {
        boolean[] seatOccupancy = new boolean[allSeatsInRow.size()];

        for (int i = 0; i < allSeatsInRow.size(); i++) {
            Seat seat = allSeatsInRow.get(i);
            seatOccupancy[i] = bookedSeatIds.contains(seat.getId());
        }

        for (Seat selectedSeat : selectedSeats) {
            int index = allSeatsInRow.indexOf(selectedSeat);
            if (index >= 0) {
                seatOccupancy[index] = true;
            }
        }

        for (int i = 1; i < seatOccupancy.length - 1; i++) {
            if (!seatOccupancy[i] && seatOccupancy[i - 1] && seatOccupancy[i + 1]) {
                return false;
            }
        }

        return true;
    }
} 