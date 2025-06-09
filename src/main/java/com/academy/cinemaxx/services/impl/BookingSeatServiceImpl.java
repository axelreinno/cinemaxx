package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.BookingSeatsRequestDTO;
import com.academy.cinemaxx.entities.*;
import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.exceptions.BadRequestException;
import com.academy.cinemaxx.repositories.*;
import com.academy.cinemaxx.services.BookingSeatService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingSeatServiceImpl implements BookingSeatService {
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final UserRepository userRepository;
    private final SeatValidationService seatValidationService;

    public BookingSeatServiceImpl(
            SeatRepository seatRepository,
            ShowtimeRepository showtimeRepository,
            BookingRepository bookingRepository,
            BookingSeatRepository bookingSeatRepository,
            UserRepository userRepository,
            SeatValidationService seatValidationService
    ) {
        this.seatRepository = seatRepository;
        this.showtimeRepository = showtimeRepository;
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.userRepository = userRepository;
        this.seatValidationService = seatValidationService;
    }

    @Override
    @Transactional
    public void bookSeats(String showtimeId, BookingSeatsRequestDTO request) {
        Showtime showtime = showtimeRepository.findBySecureId(showtimeId)
                .orElseThrow(() -> new BadRequestException("Showtime not found"));

        User user = userRepository.findBySecureId(request.userId())
                .orElseThrow(() -> new BadRequestException("User not found"));

        List<Seat> selectedSeats = seatRepository.findBySecureIdIn(request.seatIds());
        if (selectedSeats.size() != request.seatIds().size()) {
            throw new BadRequestException("One or more seats not found");
        }

        List<Seat> allSeatsInHall = showtime.getHall().getSeats();

        seatValidationService.validateSeats(showtimeId, selectedSeats, allSeatsInHall);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowtime(showtime);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking = bookingRepository.save(booking);

        List<BookingSeat> bookingSeats = new ArrayList<>();
        for (Seat seat : selectedSeats) {
            BookingSeat bookingSeat = new BookingSeat();
            bookingSeat.setBooking(booking);
            bookingSeat.setSeat(seat);
            bookingSeat.setPrice(showtime.getPrice());
            bookingSeats.add(bookingSeat);
        }

        bookingSeatRepository.saveAll(bookingSeats);
    }
} 