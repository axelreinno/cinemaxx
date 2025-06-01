package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.CinemaDTO;
import com.academy.cinemaxx.dtos.MovieDTO;
import com.academy.cinemaxx.dtos.MovieShowtimeDTO;
import com.academy.cinemaxx.dtos.ResponseDTO;
import com.academy.cinemaxx.entities.Movie;
import com.academy.cinemaxx.services.MovieService;
import com.academy.cinemaxx.services.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class MovieController {
    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    @Autowired
    public MovieController(MovieService movieService, ShowtimeService showtimeService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
    }

    @GetMapping("/now-playing-movie")
    public ResponseEntity<ResponseDTO<List<MovieDTO>>> getNowPlayingMovies(@RequestParam("cityCode") String cityCode) {
        List<MovieDTO> movies = movieService.getNowPlayingMovies(cityCode);
        return ResponseEntity.ok(ResponseDTO.success(movies));
    }

    @GetMapping("/upcoming-movie")
    public ResponseEntity<ResponseDTO<List<MovieDTO>>> getUpcomingMovies(@RequestParam("cityCode") String cityCode) {
        List<MovieDTO> movies = movieService.getUpcomingMovies(cityCode);
        return ResponseEntity.ok(ResponseDTO.success(movies));
    }

    @GetMapping("/movie")
    public ResponseEntity<ResponseDTO<List<MovieDTO>>> searchNowPlayingMoviesByTitleAndCityCode(@RequestParam(name = "name") String name, @RequestParam(name = "cityCode") String cityCode) {
        List<MovieDTO> movies = movieService.findNowPlayingMoviesByTitleAndCityCode(name, cityCode);
        return ResponseEntity.ok(ResponseDTO.success(movies));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<ResponseDTO<MovieDTO>> getMovieDetail(@PathVariable String id) {
        MovieDTO movie = movieService.getMovieDetailBySecureId(id);
        return ResponseEntity.ok(ResponseDTO.success(movie));
    }

    @GetMapping("/movie/{id}/showtime")
    public ResponseEntity<ResponseDTO<List<MovieShowtimeDTO>>> getShowtimeByMovieAndDate(
            @PathVariable String secureId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<MovieShowtimeDTO> showtimes = showtimeService.getShowtimeByMovieAndDate(secureId, date);
        return ResponseEntity.ok(ResponseDTO.success(showtimes));
    }
}
