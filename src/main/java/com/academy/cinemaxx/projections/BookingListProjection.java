package com.academy.cinemaxx.projections;

import com.academy.cinemaxx.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface BookingListProjection {
    String getSecureId();
    String getEmail();
    String getName();
    String getMovieTitle();
    BookingStatus getBookingStatus();
    LocalDateTime getPaymentAt();
    LocalDateTime getPaymentExpiredAt();
    LocalDateTime getCreatedAt();
    BigDecimal getTotalPrice();
    Integer getTotalSeats();
} 