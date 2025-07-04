package com.academy.cinemaxx.projections;

import com.academy.cinemaxx.enums.AgeRating;
import com.academy.cinemaxx.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface BookingListProjection {
    String getSecureId();
    String getEmail();
    String getName();
    String getMovieTitle();
    String getMovieThumbnailUrl();
    String getHallName();
    BookingStatus getBookingStatus();
    LocalDateTime getShowtimeAt();
    LocalDateTime getPaymentAt();
    LocalDateTime getPaymentExpiredAt();
    LocalDateTime getCreatedAt();
    BigDecimal getTotalPrice();
    Integer getTotalSeats();
} 