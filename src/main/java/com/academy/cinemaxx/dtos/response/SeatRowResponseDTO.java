package com.academy.cinemaxx.dtos.response;

import java.util.List;

public record SeatRowResponseDTO(
    Integer row,
    List<SeatResponseDTO> seats
) { }
