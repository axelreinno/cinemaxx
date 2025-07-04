package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.request.MovieRequestDTO;
import com.academy.cinemaxx.dtos.response.MovieResponseDTO;
import com.academy.cinemaxx.dtos.response.PaginationResponseDTO;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    PaginationResponseDTO<MovieResponseDTO> getNowPlayingMovies(String cityCode, Pageable pageable);
    PaginationResponseDTO<MovieResponseDTO> getUpcomingMovies(String cityCode, Pageable pageable);
    PaginationResponseDTO<MovieResponseDTO> getMovies(String name, Pageable pageable);
    MovieResponseDTO getMovieDetailById(String id);
    void createMovie(MovieRequestDTO movieRequestDTO);
}
