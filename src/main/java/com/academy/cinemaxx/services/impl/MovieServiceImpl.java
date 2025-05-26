package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.MovieDTO;
import com.academy.cinemaxx.entities.Genre;
import com.academy.cinemaxx.entities.Movie;
import com.academy.cinemaxx.repositories.CityRepository;
import com.academy.cinemaxx.repositories.MovieRepository;
import com.academy.cinemaxx.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDTO> getNowPlayingMovies(String cityCode) {
        LocalDateTime now = LocalDateTime.now();
        return movieRepository
                .findNowPlayingMoviesByCityCode(cityCode, now)
                .stream().map(movie -> new MovieDTO(
                        movie.getSecureId(),
                        movie.getTitle(),
                        movie.getDescription(),
                        movie.getDurationMin(),
                        movie.getAgeRating(),
                        movie.getReleaseDate(),
                        movie.getGenres().stream()
                                .map(Genre::getGenre)
                                .collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    public List<MovieDTO> getUpcomingMovies(String cityCode) {
        LocalDateTime now = LocalDateTime.now();
        return movieRepository
                .findUpcomingMoviesByCityCode(cityCode, now)
                .stream().map(movie -> new MovieDTO(
                        movie.getSecureId(),
                        movie.getTitle(),
                        movie.getDescription(),
                        movie.getDurationMin(),
                        movie.getAgeRating(),
                        movie.getReleaseDate(),
                        movie.getGenres().stream()
                                .map(Genre::getGenre)
                                .collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    public List<MovieDTO> findNowPlayingMoviesByTitleAndCityCode(String name, String cityCode) {
        LocalDateTime now = LocalDateTime.now();
        return movieRepository
                .findNowPlayingMoviesByTitleAndCityCode(name, cityCode, now)
                .stream().map(movie -> new MovieDTO(
                        movie.getSecureId(),
                        movie.getTitle(),
                        movie.getDescription(),
                        movie.getDurationMin(),
                        movie.getAgeRating(),
                        movie.getReleaseDate(),
                        movie.getGenres().stream()
                                .map(Genre::getGenre)
                                .collect(Collectors.toList())
                )).collect(Collectors.toList());
    }
}
