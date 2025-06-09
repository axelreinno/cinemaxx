package com.academy.cinemaxx.dtos;

import java.util.List;

public record SeatRowResponseDTO(
    Integer row,
    List<SeatResponseDTO> seats
) { }
