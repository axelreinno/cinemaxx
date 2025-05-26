package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.MovieDTO;
import com.academy.cinemaxx.entities.Movie;

import java.util.List;

public interface MovieService {
    public List<MovieDTO> getNowPlayingMovies(String cityCode);
    public List<MovieDTO> getUpcomingMovies(String cityCode);
    public List<MovieDTO> findNowPlayingMoviesByTitleAndCityCode(String name, String cityCode);
}
