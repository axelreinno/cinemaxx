package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.MovieShowtimeResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ShowtimeService {
    public List<MovieShowtimeResponseDTO> getShowtimeByMovieAndDate(String secureId, LocalDate date);
}
