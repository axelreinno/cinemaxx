package com.academy.cinemaxx.dtos.response;

import com.academy.cinemaxx.enums.SeatStatus;

import java.math.BigDecimal;

public record BookingSeatDetailResponseDTO (
        String id,
        String label,
        Integer row,
        Integer column,
        BigDecimal price
) {}