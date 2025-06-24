package com.academy.cinemaxx.dtos.response;

import com.academy.cinemaxx.enums.BookingStatus;

import java.math.BigDecimal;
import java.util.List;

public record BookingDetailResponseDTO(
        String id,
        String email,
        String name,
        String movie,
        BookingStatus bookingStatus,
        Long paymentAt,
        Long paymentExpiredAt,
        BigDecimal totalPrice,
        Integer totalSeats,
        List<BookingSeatDetailResponseDTO> seats
) { } 