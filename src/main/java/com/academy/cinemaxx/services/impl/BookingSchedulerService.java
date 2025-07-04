package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.entities.Booking;
import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.repositories.BookingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BookingSchedulerService {

    private final BookingRepository bookingRepository;

    public BookingSchedulerService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateExpiredBookings() {
        LocalDateTime now = LocalDateTime.now();
        
        bookingRepository.findByBookingStatusAndPaymentExpiredAtLessThan(BookingStatus.PENDING, now)
            .stream()
            .peek(booking -> booking.setBookingStatus(BookingStatus.EXPIRED))
            .forEach(bookingRepository::save);
    }
} 