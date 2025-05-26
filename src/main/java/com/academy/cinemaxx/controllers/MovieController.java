package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.CinemaDTO;
import com.academy.cinemaxx.dtos.MovieDTO;
import com.academy.cinemaxx.entities.Movie;
import com.academy.cinemaxx.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/now-playing")
    public ResponseEntity<List<MovieDTO>> getNowPlayingMovies(@RequestParam("cityCode") String cityCode) {
        List<MovieDTO> movies = movieService.getNowPlayingMovies(cityCode);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MovieDTO>> getUpcomingMovies(@RequestParam("cityCode") String cityCode) {
        List<MovieDTO> movies = movieService.getUpcomingMovies(cityCode);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDTO>> searchNowPlayingMoviesByTitleAndCityCode(@RequestParam(name = "name") String name, @RequestParam(name = "cityCode") String cityCode) {
        return ResponseEntity.ok(movieService.findNowPlayingMoviesByTitleAndCityCode(name, cityCode));
    }
}
