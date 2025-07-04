package com.academy.cinemaxx.dtos.response;

import com.academy.cinemaxx.enums.SeatStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record BookingSeatDetailResponseDTO (
        @Schema(example = "e9b13740-0371-42a5-85e1-68baa68218c7")
        String id,
        @Schema(example = "A!")
        String label,
        @Schema(example = "1")
        Integer row,
        @Schema(example = "1")
        Integer column,
        @Schema(example = "50000")
        BigDecimal price
) {}