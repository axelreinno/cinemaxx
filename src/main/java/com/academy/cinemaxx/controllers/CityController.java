package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.*;
import com.academy.cinemaxx.enums.SortDirection;
import com.academy.cinemaxx.services.CinemaService;
import com.academy.cinemaxx.services.CityService;
import com.academy.cinemaxx.services.MovieService;
import com.academy.cinemaxx.validators.annotations.ValidSortDirection;
import com.academy.cinemaxx.validators.annotations.ValidSortField;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/cities")
public class CityController {
    private final CityService cityService;
    private final CinemaService cinemaService;
    private final MovieService movieService;

    public CityController(CityService cityService, CinemaService cinemaService, MovieService movieService) {
        this.cityService = cityService;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<PaginationResponseDTO<CityResponseDTO>> getCities(
            @RequestParam(required = false) String names,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit,
            @ValidSortField(allowed = {"name", "code"}) @RequestParam(defaultValue = "name") String sort,
            @ValidSortDirection @RequestParam(defaultValue = "asc") String direction
    ) {
        SortDirection sortDirection = SortDirection.from(direction);
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection.toSpringSortDirection(), sort));
        PaginationResponseDTO<CityResponseDTO> pagination = cityService.getCities(names, pageable);
        return ResponseEntity.ok(pagination);
    }

    @GetMapping("/{code}/playing-movie")
    public ResponseEntity<PaginationResponseDTO<MovieResponseDTO>> getNowPlayingMovies(
            @PathVariable("code") String cityCode,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit,
            @ValidSortField(allowed = {"movie.title","movie.ageRating","movie.releaseDate"}) @RequestParam(defaultValue = "movie.title") String sort,
            @ValidSortDirection @RequestParam(defaultValue = "asc") String direction
    ) {
        SortDirection sortDirection = SortDirection.from(direction);
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection.toSpringSortDirection(), sort));
        PaginationResponseDTO<MovieResponseDTO> pagination = movieService.getNowPlayingMovies(cityCode, pageable);
        return ResponseEntity.ok(pagination);
    }

    @GetMapping("/{code}/upcoming-movie")
    public ResponseEntity<PaginationResponseDTO<MovieResponseDTO>> getUpcomingMovies(
            @PathVariable("code") String cityCode,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit,
            @ValidSortField(allowed = {"movie.title","movie.ageRating","movie.releaseDate"}) @RequestParam(defaultValue = "movie.title") String sort,
            @ValidSortDirection @RequestParam(defaultValue = "asc") String direction
    ) {
        SortDirection sortDirection = SortDirection.from(direction);
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection.toSpringSortDirection(), sort));
        PaginationResponseDTO<MovieResponseDTO> pagination = movieService.getUpcomingMovies(cityCode, pageable);
        return ResponseEntity.ok(pagination);
    }

    @GetMapping("/{code}/cinema")
    public ResponseEntity<PaginationResponseDTO<CinemaResponseDTO>> getCinemas(
            @PathVariable(name = "code") String cityCode,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit,
            @ValidSortField(allowed = {"name"}) @RequestParam(defaultValue = "name") String sort,
            @ValidSortDirection @RequestParam(defaultValue = "asc") String direction
    ) {
        SortDirection sortDirection = SortDirection.from(direction);
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection.toSpringSortDirection(), sort));
        PaginationResponseDTO<CinemaResponseDTO> pagination = cinemaService.getCinemas(name, cityCode, pageable);
        return ResponseEntity.ok(pagination);
    }
}