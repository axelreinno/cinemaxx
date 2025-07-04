package com.academy.cinemaxx.dtos.response;

import com.academy.cinemaxx.enums.AgeRating;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record MovieResponseDTO(
        @Schema(example = "e9b13740-0371-42a5-85e1-68baa68218c7")
        String id,
        @Schema(example = "Mission: Impossible - The Final Reckoning")
        String title,
        @Schema(example = "Mission: Impossible – The Final Reckoning adalah sebuah film aksi laga mata-mata Amerika Serikat tahun 2025 yang disutradarai oleh Christopher McQuarrie dari sebuah skenario yang ditulisnya bersama Erik Jendresen.")
        String description,
        @Schema(example = "120")
        int durationMinutes,
        @Schema(example = "SU")
        AgeRating rating,
        @Schema(example = "2025-05-21")
        LocalDate releaseDate,
        @Schema(example = "https://thumbail.com")
        String thumbnailUrl,
        @Schema(example = "https://trailer.com")
        String trailerUrl,
        List<String> genres
) {}