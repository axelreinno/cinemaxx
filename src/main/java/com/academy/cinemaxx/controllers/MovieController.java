package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.CinemaDTO;
import com.academy.cinemaxx.dtos.MovieDTO;
import com.academy.cinemaxx.dtos.ResponseDTO;
import com.academy.cinemaxx.entities.Movie;
import com.academy.cinemaxx.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDTO<List<MovieDTO>>> getNowPlayingMovies(@RequestParam("cityCode") String cityCode) {
        List<MovieDTO> movies = movieService.getNowPlayingMovies(cityCode);
        return ResponseEntity.ok(ResponseDTO.success(movies));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<ResponseDTO<List<MovieDTO>>> getUpcomingMovies(@RequestParam("cityCode") String cityCode) {
        List<MovieDTO> movies = movieService.getUpcomingMovies(cityCode);
        return ResponseEntity.ok(ResponseDTO.success(movies));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<List<MovieDTO>>> searchNowPlayingMoviesByTitleAndCityCode(@RequestParam(name = "name") String name, @RequestParam(name = "cityCode") String cityCode) {
        List<MovieDTO> movies = movieService.findNowPlayingMoviesByTitleAndCityCode(name, cityCode);
        return ResponseEntity.ok(ResponseDTO.success(movies));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseDTO<MovieDTO>> getMovieDetail(@PathVariable String id) {
        MovieDTO movie = movieService.getMovieDetailBySecureId(id);
        return ResponseEntity.ok(ResponseDTO.success(movie));
    }
}
