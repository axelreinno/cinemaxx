package com.academy.cinemaxx.dtos;

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