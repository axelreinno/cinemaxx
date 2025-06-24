package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.request.BookingSeatsRequestDTO;
import com.academy.cinemaxx.dtos.response.*;
import com.academy.cinemaxx.entities.*;
import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.exceptions.BadRequestRuntimeException;
import com.academy.cinemaxx.projections.BookingListProjection;
import com.academy.cinemaxx.repositories.*;
import com.academy.cinemaxx.services.BookingService;
import com.academy.cinemaxx.services.UserService;
import com.academy.cinemaxx.utils.DateTimeUtils;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public BookingDetailResponseDTO getBookingBySecureId(String secureId) {
        BookingListProjection booking = bookingRepository.findBookingBySecureId(secureId)
                .orElseThrow(() -> new BadRequestRuntimeException("Booking invalid"));

        List<BookingSeat> bookingSeats = bookingSeatRepository.findByBookingSecureId(secureId);
        List<BookingSeatDetailResponseDTO> seats = bookingSeats.stream()
                .map(bs -> new BookingSeatDetailResponseDTO(
                        bs.getSeat().getSecureId(),
                        bs.getSeat().getLabel(),
                        bs.getSeat().getColumn(),
                        bs.getSeat().getRow(),
                        bs.getPrice()
                ))
                .toList();

        return new BookingDetailResponseDTO(
                booking.getSecureId(),
                booking.getEmail(),
                booking.getName(),
                booking.getMovieTitle(),
                booking.getBookingStatus(),
                booking.getPaymentAt() != null ? DateTimeUtils.toEpochSecond(booking.getPaymentAt()) : null,
                DateTimeUtils.toEpochSecond(booking.getPaymentExpiredAt()),
                booking.getTotalPrice(),
                booking.getTotalSeats(),
                seats
        );
    }
} 