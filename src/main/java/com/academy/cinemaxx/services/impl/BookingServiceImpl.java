package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.request.BookingSeatsRequestDTO;
import com.academy.cinemaxx.dtos.response.*;
import com.academy.cinemaxx.entities.*;
import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.enums.UserRole;
import com.academy.cinemaxx.exceptions.BadRequestRuntimeException;
import com.academy.cinemaxx.projections.BookingDetailProjection;
import com.academy.cinemaxx.projections.BookingListProjection;
import com.academy.cinemaxx.repositories.*;
import com.academy.cinemaxx.services.BookingService;
import com.academy.cinemaxx.services.UserService;
import com.academy.cinemaxx.utils.DateTimeUtils;
import com.academy.cinemaxx.utils.HelperUtils;
import jakarta.persistence.EntityNotFoundException;
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

    @Transactional
    public void createBookingUser(BookingSeatsRequestDTO request) {
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
    public void payBooking(String id) {
        Booking booking = bookingRepository.findBySecureId(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(booking.getPaymentExpiredAt())) {
            throw new BadRequestRuntimeException("Booking payment has expired");
        }

        booking.setPaymentAt(now);
        booking.setBookingStatus(BookingStatus.PAID);

        bookingRepository.save(booking);
    }

    @Override
    public void cancelBooking(String id) {
        User currentUser = userService.getCurrentUser();
        Booking booking;

        if(currentUser.getRole() == UserRole.ROLE_ADMIN) {
            booking = bookingRepository.findBySecureId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        } else {
            booking = bookingRepository.findByUserAndSecureId(currentUser, id)
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);


        bookingRepository.save(booking);
    }

    @Override
    public PaginationResponseDTO<BookingListResponseDTO> getBookings(String movie, String name, String email, BookingStatus status, Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        Page<BookingListProjection> bookings;

        movie = HelperUtils.normalize(movie);
        name = HelperUtils.normalize(name);
        email = HelperUtils.normalize(email);

        if(currentUser.getRole() == UserRole.ROLE_ADMIN) {
            bookings = bookingRepository.findAllBookingList(movie, name, email, status, pageable);
        } else {
            bookings = bookingRepository.findAllBookingListByUser(currentUser.getSecureId(), movie, name, email, status, pageable);
        }

        return new PaginationResponseDTO<>(
                bookings.getSize(),
                bookings.getTotalPages(),
                bookings.getTotalElements(),
                bookings.map(booking -> new BookingListResponseDTO(
                        booking.getSecureId(),
                        booking.getEmail(),
                        booking.getName(),
                        booking.getMovieTitle(),
                        booking.getMovieThumbnailUrl(),
                        booking.getHallName(),
                        booking.getBookingStatus(),
                        DateTimeUtils.toEpochSecond(booking.getShowtimeAt()),
                        booking.getPaymentAt() != null ? DateTimeUtils.toEpochSecond(booking.getPaymentAt()) : null,
                        DateTimeUtils.toEpochSecond(booking.getPaymentExpiredAt()),
                        booking.getTotalPrice(),
                        booking.getTotalSeats())
                ).getContent()
        );
    }

    @Override
    public BookingDetailResponseDTO getBookingBySecureId(String secureId) {
        User currentUser = userService.getCurrentUser();
        BookingDetailProjection booking;

        if(currentUser.getRole() == UserRole.ROLE_ADMIN) {
            booking = bookingRepository.findBookingBySecureId(secureId)
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        } else {
            booking = bookingRepository.findBookingBySecureIdAndUserSecureId(secureId, currentUser.getSecureId())
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        }

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
                booking.getMovieDescription(),
                booking.getMovieDurationMin(),
                booking.getMovieAgeRating(),
                booking.getMovieThumbnailUrl(),
                booking.getHallName(),
                booking.getHallType(),
                booking.getCinemaName(),
                booking.getCinemaAddress(),
                booking.getCityName(),
                booking.getBookingStatus(),
                DateTimeUtils.toEpochSecond(booking.getShowtimeAt()),
                booking.getPaymentAt() != null ? DateTimeUtils.toEpochSecond(booking.getPaymentAt()) : null,
                DateTimeUtils.toEpochSecond(booking.getPaymentExpiredAt()),
                booking.getTotalPrice(),
                booking.getTotalSeats(),
                seats
        );
    }
} 