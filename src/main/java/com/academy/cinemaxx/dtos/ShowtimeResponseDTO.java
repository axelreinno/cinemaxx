package com.academy.cinemaxx.dtos;

import java.time.LocalDateTime;

public record ShowtimeResponseDTO(
        LocalDateTime startTime
) {}