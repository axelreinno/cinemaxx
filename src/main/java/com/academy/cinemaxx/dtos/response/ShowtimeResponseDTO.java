package com.academy.cinemaxx.dtos.response;

public record ShowtimeResponseDTO(
        String id,
        Long startTime,
        Long endTime
) {}