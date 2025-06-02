package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.MovieResponseDTO;
import com.academy.cinemaxx.dtos.MovieShowtimeResponseDTO;
import com.academy.cinemaxx.dtos.PaginationResponseDTO;
import com.academy.cinemaxx.dtos.ResponseDTO;
import com.academy.cinemaxx.enums.SortDirection;
import com.academy.cinemaxx.services.MovieService;
import com.academy.cinemaxx.services.ShowtimeService;
import com.academy.cinemaxx.validators.annotations.ValidSortDirection;
import com.academy.cinemaxx.validators.annotations.ValidSortField;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1")
public class MovieController {
    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    public MovieController(MovieService movieService, ShowtimeService showtimeService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
    }

    @GetMapping("/movie")
    public ResponseEntity<PaginationResponseDTO<MovieResponseDTO>> getMovies(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit,
            @ValidSortField(allowed = {"title","ageRating","releaseDate"}) @RequestParam(defaultValue = "title") String sort,
            @ValidSortDirection @RequestParam(defaultValue = "asc") String direction
    ) {
        SortDirection sortDirection = SortDirection.from(direction);
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection.toSpringSortDirection(), sort));
        PaginationResponseDTO<MovieResponseDTO> pagination = movieService.getMovies(title, pageable);
        return ResponseEntity.ok(pagination);
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<ResponseDTO<MovieResponseDTO>> getMovieDetail(@PathVariable String id) {
        MovieResponseDTO movie = movieService.getMovieDetailById(id);
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
