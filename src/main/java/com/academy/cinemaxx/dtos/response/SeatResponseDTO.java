package com.academy.cinemaxx.dtos.response;

import com.academy.cinemaxx.enums.SeatStatus;

import java.math.BigDecimal;

public record SeatResponseDTO(
    String id,
    String label,
    Integer column,
    BigDecimal price,
    SeatStatus status,
    String element
) {} 