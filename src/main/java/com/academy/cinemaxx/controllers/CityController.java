package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.response.*;
import com.academy.cinemaxx.enums.SortDirection;
import com.academy.cinemaxx.services.CinemaService;
import com.academy.cinemaxx.services.CityService;
import com.academy.cinemaxx.services.MovieService;
import com.academy.cinemaxx.services.ShowtimeService;
import com.academy.cinemaxx.validators.annotations.ValidEpochDayFromToday;
import com.academy.cinemaxx.validators.annotations.ValidSortDirection;
import com.academy.cinemaxx.validators.annotations.ValidSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/cities")
@Tag(name = "Cities", description = "Cities APIs")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('USER')")
public class CityController {
    private final CityService cityService;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    public CityController(CityService cityService, CinemaService cinemaService, MovieService movieService, ShowtimeService showtimeService) {
        this.cityService = cityService;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
        this.showtimeService = showtimeService;
    }

    @Operation(summary = "Get list of cities", description = "Returns a paginated list of cities with optional filtering and sorting")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved cities",
                    content = @Content(schema = @Schema(implementation = PaginationResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
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

    @Operation(summary = "Get list of playing movies", description = "Returns a paginated list of playing movies with optional filtering and sorting")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved playing movies",
                    content = @Content(schema = @Schema(implementation = PaginationResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
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

    @Operation(summary = "Get list of upcoming movies", description = "Returns a paginated list of upcoming movies with optional filtering and sorting")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved upcoming movies",
                    content = @Content(schema = @Schema(implementation = PaginationResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
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

    @Operation(summary = "Get list of cinemas", description = "Returns a paginated list of cinemas with optional filtering and sorting")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved cinemas",
                    content = @Content(schema = @Schema(implementation = PaginationResponseDTO.class))
            ),
    })
    @GetMapping("/{code}/cinemas")
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

    @Operation(summary = "Get movie showtimes", description = "Returns list of showtimes for a specific movie on a given date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved showtimes"),
    })
    @GetMapping("{code}/movies/{id}/showtime")
    public ResponseEntity<ResponseDTO<List<MovieShowtimeResponseDTO>>> getShowtimeByMovieIdAndCityCode(
            @PathVariable(name = "code") String cityCode,
            @PathVariable String id,
            @RequestParam
            @ValidEpochDayFromToday
            Long date
    ) {
        List<MovieShowtimeResponseDTO> showtime = showtimeService.getShowtimeByMovieIdAndCityCode(id, cityCode, date);
        return ResponseEntity.ok(ResponseDTO.success(showtime));
    }
}