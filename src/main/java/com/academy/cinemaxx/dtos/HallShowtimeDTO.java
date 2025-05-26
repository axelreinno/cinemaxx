package com.academy.cinemaxx.dtos;

import java.util.List;

public record HallShowtimeDTO(
        String name,
        List<ShowtimeDTO> showtimes
) {}
