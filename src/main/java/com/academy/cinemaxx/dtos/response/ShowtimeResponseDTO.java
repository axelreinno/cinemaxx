package com.academy.cinemaxx.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ShowtimeResponseDTO(
        @Schema(example = "e9b13740-0371-42a5-85e1-68baa68218c7")
        String id,
        @Schema(example = "1751557046")
        Long startTime,
        @Schema(example = "1751557046")
        Long endTime
) {}