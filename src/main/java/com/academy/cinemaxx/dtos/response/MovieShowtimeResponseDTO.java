package com.academy.cinemaxx.dtos.response;

import java.util.List;

public record MovieShowtimeResponseDTO(
        String cinema,
        List<HallShowtimeResponseDTO> halls
) { }
