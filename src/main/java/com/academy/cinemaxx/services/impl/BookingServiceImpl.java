package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.request.BookingSeatsRequestDTO;
import com.academy.cinemaxx.dtos.response.PaginationResponseDTO;
import com.academy.cinemaxx.dtos.response.BookingListResponseDTO;
import com.academy.cinemaxx.entities.*;
import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.projections.BookingListProjection;
import com.academy.cinemaxx.repositories.*;
import com.academy.cinemaxx.services.BookingService;
import com.academy.cinemaxx.services.UserService;
import com.academy.cinemaxx.utils.DateTimeUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final UserService userService;

    public BookingServiceImpl(
            SeatRepository seatRepository,
            ShowtimeRepository showtimeRepository,
            BookingRepository bookingRepository,
            BookingSeatRepository bookingSeatRepository,
            UserService userService
    ) {
        this.seatRepository = seatRepository;
        this.showtimeRepository = showtimeRepository;
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
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


    @Override
    public PaginationResponseDTO<BookingListResponseDTO> getBookings(String movie, String name, String email, BookingStatus status, Pageable pageable) {
        Page<BookingListProjection> bookings = bookingRepository.findAllBookingList(movie, name, email, status, pageable);

        return new PaginationResponseDTO<>(
                bookings.getSize(),
                bookings.getTotalPages(),
                bookings.getTotalElements(),
                bookings.map(booking -> new BookingListResponseDTO(
                        booking.getSecureId(),
                        booking.getEmail(),
                        booking.getName(),
                        booking.getMovieTitle(),
                        booking.getBookingStatus(),
                        booking.getPaymentAt() != null ? DateTimeUtils.toEpochSecond(booking.getPaymentAt()) : null,
                        DateTimeUtils.toEpochSecond(booking.getPaymentExpiredAt()),
                        booking.getTotalPrice(),
                        booking.getTotalSeats())
                ).getContent()
        );
    }
} 