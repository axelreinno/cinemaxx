package com.academy.cinemaxx.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CinemaResponseDTO (
        @Schema(example = "e9b13740-0371-42a5-85e1-68baa68218c7")
        String id,
        @Schema(example = "CGV Cinemas FX Sudirman")
        String name,
        @Schema(example = "Jl. Jenderal Sudirman No.25, RT.1/RW.3, Gelora, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10270")
        String address,
        CityResponseDTO city
) {}