package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.response.MovieShowtimeResponseDTO;

import java.util.List;

public interface ShowtimeService {
    public List<MovieShowtimeResponseDTO> getShowtime(String id, Long date);
}
