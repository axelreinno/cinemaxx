package com.academy.cinemaxx.dtos;

import java.util.List;

public record MovieShowtimeResponseDTO(
        String cinema,
        List<HallShowtimeResponseDTO> halls
) { }
