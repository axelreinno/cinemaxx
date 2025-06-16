package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.request.BookingSeatsRequestDTO;
import com.academy.cinemaxx.entities.*;
import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.repositories.*;
import com.academy.cinemaxx.services.BookingSeatService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingSeatServiceImpl implements BookingSeatService {
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final UserRepository userRepository;

    public BookingSeatServiceImpl(
            SeatRepository seatRepository,
            ShowtimeRepository showtimeRepository,
            BookingRepository bookingRepository,
            BookingSeatRepository bookingSeatRepository,
            UserRepository userRepository
    ) {
        this.seatRepository = seatRepository;
        this.showtimeRepository = showtimeRepository;
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createBooking(BookingSeatsRequestDTO request) {
        Showtime showtime = showtimeRepository.findBySecureId(request.showtimeId()).orElseThrow();
        User user = userRepository.findBySecureId(request.userId()).orElseThrow();
        List<Seat> selectedSeats = seatRepository.findBySecureIdIn(request.seatIds());

        Booking bookingData = new Booking();
        bookingData.setUser(user);
        bookingData.setShowtime(showtime);
        bookingData.setBookingStatus(BookingStatus.PENDING);
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