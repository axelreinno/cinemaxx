package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.MovieResponseDTO;
import com.academy.cinemaxx.dtos.MovieShowtimeResponseDTO;
import com.academy.cinemaxx.dtos.ResponseDTO;
import com.academy.cinemaxx.services.MovieService;
import com.academy.cinemaxx.services.ShowtimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class MovieController {
    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    public MovieController(MovieService movieService, ShowtimeService showtimeService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<ResponseDTO<MovieResponseDTO>> getMovieDetail(@PathVariable String id) {
        MovieResponseDTO movie = movieService.getMovieDetailBySecureId(id);
        return ResponseEntity.ok(ResponseDTO.success(movie));
    }

    @GetMapping("/movie/{id}/showtime")
    public ResponseEntity<ResponseDTO<List<MovieShowtimeResponseDTO>>> getShowtimeByMovieAndDate(
            @PathVariable String secureId,
            @RequestParam("date") LocalDate date
    ) {
        List<MovieShowtimeResponseDTO> showtimes = showtimeService.getShowtimeByMovieAndDate(secureId, date);
        return ResponseEntity.ok(ResponseDTO.success(showtimes));
    }
}
