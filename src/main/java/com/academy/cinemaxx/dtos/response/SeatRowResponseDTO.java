package com.academy.cinemaxx.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SeatRowResponseDTO(
        @Schema(example = "1")
        Integer row,
        List<SeatResponseDTO> seats
) { }
