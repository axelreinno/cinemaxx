package com.academy.cinemaxx.dtos;

import com.academy.cinemaxx.enums.AgeRating;

import java.time.LocalDate;
import java.util.List;

public record MovieDTO (
        String id,
        String title,
        String description,
        int durationMinutes,
        AgeRating rating,
        LocalDate releaseDate,
        List<String> genres
) {}