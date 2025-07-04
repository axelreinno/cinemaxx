package com.academy.cinemaxx.projections;

import com.academy.cinemaxx.enums.AgeRating;
import com.academy.cinemaxx.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface BookingDetailProjection {
    String getSecureId();
    String getEmail();
    String getName();
    String getMovieTitle();
    String getMovieDescription();
    int getMovieDurationMin();
    AgeRating getMovieAgeRating();
    String getMovieThumbnailUrl();
    String getHallName();
    String getHallType();
    String getCinemaName();
    String getCinemaAddress();
    String getCityName();
    BookingStatus getBookingStatus();
    LocalDateTime getShowtimeAt();
    LocalDateTime getPaymentAt();
    LocalDateTime getPaymentExpiredAt();
    LocalDateTime getCreatedAt();
    BigDecimal getTotalPrice();
    int getTotalSeats();
}