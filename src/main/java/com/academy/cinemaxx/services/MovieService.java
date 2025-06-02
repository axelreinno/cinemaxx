package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.MovieResponseDTO;
import com.academy.cinemaxx.dtos.PaginationResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    public PaginationResponseDTO<MovieResponseDTO> getNowPlayingMovies(String cityCode, Pageable pageable);
    public PaginationResponseDTO<MovieResponseDTO> getUpcomingMovies(String cityCode, Pageable pageable);
    public PaginationResponseDTO<MovieResponseDTO> getMovies(String name, Pageable pageable);
    public MovieResponseDTO getMovieDetailById(String id);
}
