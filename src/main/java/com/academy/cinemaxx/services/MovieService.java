package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.MovieResponseDTO;

import java.util.List;

public interface MovieService {
    public List<MovieResponseDTO> getNowPlayingMovies(String cityCode);
    public List<MovieResponseDTO> getUpcomingMovies(String cityCode);
    public List<MovieResponseDTO> findNowPlayingMoviesByTitleAndCityCode(String name, String cityCode);
    public MovieResponseDTO getMovieDetailBySecureId(String secureId);
}
