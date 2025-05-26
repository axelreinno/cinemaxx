package com.academy.cinemaxx.dtos;

import com.academy.cinemaxx.enums.AgeRating;

import java.time.LocalDateTime;

public record ShowtimeDTO (
        LocalDateTime startTime
) {}