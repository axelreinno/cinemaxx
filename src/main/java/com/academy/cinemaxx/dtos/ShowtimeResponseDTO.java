package com.academy.cinemaxx.dtos;

public record ShowtimeResponseDTO(
        String id,
        Long startTime,
        Long endTime
) {}