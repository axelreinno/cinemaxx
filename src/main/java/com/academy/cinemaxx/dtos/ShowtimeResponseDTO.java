package com.academy.cinemaxx.dtos;

import java.time.LocalDateTime;

public record ShowtimeResponseDTO(
        Long startTime,
        Long endTime
) {}