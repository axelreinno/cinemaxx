package com.academy.cinemaxx.dtos.request;

import com.academy.cinemaxx.enums.AgeRating;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.List;

public record MovieRequestDTO(
        @Schema(example = "Mission: Impossible - The Final Reckoning")
        @NotBlank(message = "Title is required")
        @Size(min = 1, max = 150, message = "Title must be between 1 and 150 characters")
        String title,

        @Schema(example = "Mission: Impossible – The Final Reckoning adalah sebuah film aksi laga mata-mata Amerika Serikat tahun 2025 yang disutradarai oleh Christopher McQuarrie dari sebuah skenario yang ditulisnya bersama Erik Jendresen.")
        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @Schema(example = "120")
        @NotNull(message = "Duration is required")
        @Positive(message = "Duration must be positive")
        Integer durationMin,

        @Schema(example = "2025-05-21")
        @NotNull(message = "Release date is required")
        Long releaseDate,

        @Schema(example = "Brian De Palma")
        @NotBlank(message = "Director is required")
        @Size(min = 1, max = 150, message = "Director name must be between 1 and 150 characters")
        String director,

        @Schema(example = "e9b13740-0371-42a5-85e1-68baa68218c7.jpg")
        @NotBlank(message = "Thumbnail is required")
        String thumbnail,

        @Schema(example = "https://trailer.com")
        String trailerUrl,

        @Schema(example = "SU")
        @NotNull(message = "Age rating is required")
        AgeRating ageRating,

        @NotNull(message = "Genres are required")
        @Size(min = 1, message = "At least one genre must be selected")
        List<String> genres
) {} 