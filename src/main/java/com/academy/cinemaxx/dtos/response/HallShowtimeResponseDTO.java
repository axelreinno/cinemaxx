package com.academy.cinemaxx.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record HallShowtimeResponseDTO(
        @Schema(example = "CGV Satin Hall 1")
        String name,
        List<ShowtimeResponseDTO> showtimes
) {}
