package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.CityResponseDTO;
import com.academy.cinemaxx.dtos.MovieResponseDTO;
import com.academy.cinemaxx.dtos.PaginationResponseDTO;
import com.academy.cinemaxx.entities.Genre;
import com.academy.cinemaxx.entities.Movie;
import com.academy.cinemaxx.repositories.MovieRepository;
import com.academy.cinemaxx.services.MovieService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public PaginationResponseDTO<MovieResponseDTO> getNowPlayingMovies(String cityCode, Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        Page<Movie> movies = movieRepository.findNowPlayingMovies(cityCode, now, pageable);
        return new PaginationResponseDTO<>(
                movies.getSize(),
                movies.getTotalPages(),
                movies.getTotalElements(),
                movies.map(movie -> new MovieResponseDTO(
                        movie.getSecureId(),
                        movie.getTitle(),
                        movie.getDescription(),
                        movie.getDurationMin(),
                        movie.getAgeRating(),
                        movie.getReleaseDate(),
                        movie.getGenres()
                                .stream()
                                .map(Genre::getGenre)
                                .collect(Collectors.toList())
                )).getContent()
        );
    }

    public PaginationResponseDTO<MovieResponseDTO> getUpcomingMovies(String cityCode, Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        Page<Movie> movies = movieRepository.findUpcomingMovies(cityCode, now, pageable);
        return new PaginationResponseDTO<>(
                movies.getSize(),
                movies.getTotalPages(),
                movies.getTotalElements(),
                movies.map(movie -> new MovieResponseDTO(
                        movie.getSecureId(),
                        movie.getTitle(),
                        movie.getDescription(),
                        movie.getDurationMin(),
                        movie.getAgeRating(),
                        movie.getReleaseDate(),
                        movie.getGenres()
                                .stream()
                                .map(Genre::getGenre)
                                .collect(Collectors.toList())
                )).getContent()
        );
    }

    public PaginationResponseDTO<MovieResponseDTO> getMovies(String title, Pageable pageable) {
        Page<Movie> movies;

        if(title != null && !title.isBlank()) {
            movies = movieRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else {
            movies = movieRepository.findAll(pageable);
        }

        return new PaginationResponseDTO<>(
                movies.getSize(),
                movies.getTotalPages(),
                movies.getTotalElements(),
                movies.map(movie -> new MovieResponseDTO(
                        movie.getSecureId(),
                        movie.getTitle(),
                        movie.getDescription(),
                        movie.getDurationMin(),
                        movie.getAgeRating(),
                        movie.getReleaseDate(),
                        movie.getGenres()
                                .stream()
                                .map(Genre::getGenre)
                                .collect(Collectors.toList())
                )).getContent()
        );
    }

    public MovieResponseDTO getMovieDetailById(String id) {
        Movie movie = movieRepository.findBySecureId(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        return new MovieResponseDTO(
                movie.getSecureId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getDurationMin(),
                movie.getAgeRating(),
                movie.getReleaseDate(),
                movie.getGenres().stream()
                        .map(Genre::getGenre)
                        .collect(Collectors.toList())
        );
    }
}
