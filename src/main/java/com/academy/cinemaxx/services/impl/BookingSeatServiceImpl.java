package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.request.BookingSeatsRequestDTO;
import com.academy.cinemaxx.entities.*;
import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.repositories.*;
import com.academy.cinemaxx.services.BookingSeatService;
import com.academy.cinemaxx.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingSeatServiceImpl implements BookingSeatService {
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public BookingSeatServiceImpl(
            SeatRepository seatRepository,
            ShowtimeRepository showtimeRepository,
            BookingRepository bookingRepository,
            BookingSeatRepository bookingSeatRepository,
            UserRepository userRepository,
            UserService userService
    ) {
        this.seatRepository = seatRepository;
        this.showtimeRepository = showtimeRepository;
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void createBooking(BookingSeatsRequestDTO request) {
        User currentUser = userService.getCurrentUser();
        Showtime showtime = showtimeRepository.findBySecureId(request.showtimeId()).orElseThrow();
        List<Seat> selectedSeats = seatRepository.findBySecureIdIn(request.seatIds());

        Booking bookingData = new Booking();
        bookingData.setUser(currentUser);
        bookingData.setShowtime(showtime);
        bookingData.setBookingStatus(BookingStatus.PENDING);
        bookingData.setPaymentExpiredAt(LocalDateTime.now().plusHours(1));
        Booking booking = bookingRepository.save(bookingData);

        List<BookingSeat> bookingSeats = selectedSeats.stream()
                .map(seat -> {
                    BookingSeat bookingSeat = new BookingSeat();
                    bookingSeat.setBooking(booking);
                    bookingSeat.setSeat(seat);
                    bookingSeat.setPrice(showtime.getPrice());
                    return bookingSeat;
                })
                .toList();

        bookingSeatRepository.saveAll(bookingSeats);
    }
} 