package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.response.MovieShowtimeResponseDTO;

import java.util.List;

public interface ShowtimeService {
    List<MovieShowtimeResponseDTO> getShowtimeByMovieIdAndCityCode(String id, String cityCode, Long date);
}
