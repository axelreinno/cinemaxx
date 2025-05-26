package com.academy.cinemaxx.dtos;

import java.util.List;

public record MovieShowtimeDTO (
        String cinema,
        List<HallShowtimeDTO> halls
) { }
