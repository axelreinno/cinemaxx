package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.response.SeatRowResponseDTO;

import java.util.List;

public interface SeatService {
    public List<SeatRowResponseDTO> findSeatsByShowtimeId(String id);
}
