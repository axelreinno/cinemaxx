package com.academy.cinemaxx.dtos;

import java.util.List;

public record HallShowtimeResponseDTO(
        String name,
        List<ShowtimeResponseDTO> showtimes
) {}
