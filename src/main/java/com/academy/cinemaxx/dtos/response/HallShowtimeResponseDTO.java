package com.academy.cinemaxx.dtos.response;

import java.util.List;

public record HallShowtimeResponseDTO(
        String name,
        List<ShowtimeResponseDTO> showtimes
) {}
