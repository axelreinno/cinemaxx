package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.entities.BookingSeat;
import com.academy.cinemaxx.entities.Seat;
import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.enums.SeatStatus;
import com.academy.cinemaxx.exceptions.BadRequestException;
import com.academy.cinemaxx.repositories.BookingSeatRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeatValidationService {
    private final BookingSeatRepository bookingSeatRepository;

    public SeatValidationService(BookingSeatRepository bookingSeatRepository) {
        this.bookingSeatRepository = bookingSeatRepository;
    }

    public void validateSeats(String showtimeId, List<Seat> selectedSeats, List<Seat> allSeatsInHall) {
        validateSeatAvailability(selectedSeats);
        validateExistingBookings(showtimeId, selectedSeats);
        validateEmptySeatRule(showtimeId, selectedSeats, allSeatsInHall);
    }

    private void validateSeatAvailability(List<Seat> seats) {
        List<Seat> unavailableSeats = seats.stream()
                .filter(seat -> seat.getStatus() != SeatStatus.AVAILABLE)
                .toList();

        if (!unavailableSeats.isEmpty()) {
            String seatLabels = unavailableSeats.stream()
                    .map(Seat::getLabel)
                    .collect(Collectors.joining(", "));
            throw new BadRequestException("Selected element are not available: " + seatLabels);
        }
    }

    private void validateExistingBookings(String showtimeId, List<Seat> selectedSeats) {
        List<Long> seatIds = selectedSeats.stream()
                .map(Seat::getId)
                .toList();

        List<BookingSeat> existingBookings = bookingSeatRepository.findExistingBookingsForSeats(showtimeId, seatIds);

        if (!existingBookings.isEmpty()) {
            List<String> bookedSeats = existingBookings.stream()
                    .map(bs -> bs.getSeat().getLabel())
                    .collect(Collectors.toList());

            throw new BadRequestException("Selected seats are already taken: " + String.join(", ", bookedSeats));
        }
    }

    private void validateEmptySeatRule(String showtimeId, List<Seat> selectedSeats, List<Seat> allSeatsInHall) {
        List<BookingSeat> bookedSeats = bookingSeatRepository.findBookedSeatsByShowtimeSecureId(showtimeId);
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

            if (!selectedInRow.isEmpty()) {
                validateEmptySeatInRow(selectedInRow, rowSeats, bookedSeatIds);
            }
        }
    }

    private void validateEmptySeatInRow(List<Seat> selectedSeats, List<Seat> allSeatsInRow, Set<Long> bookedSeatIds) {
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
                throw new BadRequestException(
                    "Cannot leave a single empty seat between occupied seats"
                );
            }
        }
    }
} 