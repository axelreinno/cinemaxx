package com.academy.cinemaxx.dtos.response;

import com.academy.cinemaxx.enums.AgeRating;

import java.time.LocalDate;
import java.util.List;

public record MovieResponseDTO(
        String id,
        String title,
        String description,
        int durationMinutes,
        AgeRating rating,
        LocalDate releaseDate,
        String thumbnailUrl,
        String trailerUrl,
        List<String> genres
) {}