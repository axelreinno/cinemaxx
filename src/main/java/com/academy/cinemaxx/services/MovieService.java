package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.request.MovieRequestDTO;
import com.academy.cinemaxx.dtos.response.MovieResponseDTO;
import com.academy.cinemaxx.dtos.response.PaginationResponseDTO;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    public PaginationResponseDTO<MovieResponseDTO> getNowPlayingMovies(String cityCode, Pageable pageable);
    public PaginationResponseDTO<MovieResponseDTO> getUpcomingMovies(String cityCode, Pageable pageable);
    public PaginationResponseDTO<MovieResponseDTO> getMovies(String name, Pageable pageable);
    public MovieResponseDTO getMovieDetailById(String id);
    public void createMovie(MovieRequestDTO movieRequestDTO);
}
