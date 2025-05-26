package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.MovieShowtimeDTO;
import com.academy.cinemaxx.entities.Showtime;

import java.time.LocalDate;
import java.util.List;

public interface ShowtimeService {
    public List<MovieShowtimeDTO> getShowtimeByMovieAndDate(String secureId, LocalDate date);
}
