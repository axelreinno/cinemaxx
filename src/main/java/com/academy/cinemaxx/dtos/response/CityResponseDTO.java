package com.academy.cinemaxx.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CityResponseDTO(
        @Schema(example = "JKT")
        String code,
        @Schema(example = "Jakarta")
        String name
) {}