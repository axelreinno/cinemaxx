package com.academy.cinemaxx.dtos.request;

import com.academy.cinemaxx.enums.AgeRating;
import jakarta.validation.constraints.*;

import java.util.List;

public record MovieRequestDTO(
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 150, message = "Title must be between 1 and 150 characters")
    String title,

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    String description,

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    Integer durationMin,

    @NotNull(message = "Release date is required")
    Long releaseDate,

    @NotBlank(message = "Director is required")
    @Size(min = 1, max = 150, message = "Director name must be between 1 and 150 characters")
    String director,

    @NotBlank(message = "Thumbnail is required")
    String thumbnail,

    String trailerUrl,

    @NotNull(message = "Age rating is required")
    AgeRating ageRating,

    @NotNull(message = "Genres are required")
    @Size(min = 1, message = "At least one genre must be selected")
    List<String> genres
) {} 