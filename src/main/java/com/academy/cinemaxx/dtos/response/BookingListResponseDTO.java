package com.academy.cinemaxx.dtos.response;

import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.projections.BookingListProjection;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingListResponseDTO(
        String id,
        String email,
        String name,
        String movie,
        BookingStatus bookingStatus,
        Long paymentAt,
        Long paymentExpiredAt,
        BigDecimal totalPrice,
        Integer totalSeats
) { } 