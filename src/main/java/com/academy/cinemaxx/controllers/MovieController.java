package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.request.MovieRequestDTO;
import com.academy.cinemaxx.dtos.response.MovieResponseDTO;
import com.academy.cinemaxx.dtos.response.MovieShowtimeResponseDTO;
import com.academy.cinemaxx.dtos.response.PaginationResponseDTO;
import com.academy.cinemaxx.dtos.response.ResponseDTO;
import com.academy.cinemaxx.enums.SortDirection;
import com.academy.cinemaxx.services.MovieService;
import com.academy.cinemaxx.services.ShowtimeService;
import com.academy.cinemaxx.validators.annotations.ValidEpochDayFromToday;
import com.academy.cinemaxx.validators.annotations.ValidSortDirection;
import com.academy.cinemaxx.validators.annotations.ValidSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@RestController
@RequestMapping("/v1/movies")
@Tag(name = "Movies", description = "Movie APIs")
@SecurityRequirement(name = "bearerAuth")
public class MovieController {
    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    public MovieController(MovieService movieService, ShowtimeService showtimeService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
    }

    @Operation(summary = "Get list of movies", description = "Returns a paginated list of movies with optional filtering and sorting")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved movies",
                    content = @Content(schema = @Schema(implementation = PaginationResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input parameters")
    })
    @GetMapping
    public ResponseEntity<PaginationResponseDTO<MovieResponseDTO>> getMovies(
            @Parameter(description = "Filter movies by title (optional)")
            @RequestParam(required = false) String title,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0", required = false) int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10", required = false) int limit,
            @Parameter(description = "Sort field (title, ageRating, or releaseDate)", example = "title")
            @ValidSortField(allowed = {"title","ageRating","releaseDate"})
            @RequestParam(defaultValue = "title") String sort,
            @Parameter(description = "Sort direction (asc or desc)", example = "asc")
            @ValidSortDirection @RequestParam(defaultValue = "asc") String direction
    ) {
        SortDirection sortDirection = SortDirection.from(direction);
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection.toSpringSortDirection(), sort));
        PaginationResponseDTO<MovieResponseDTO> pagination = movieService.getMovies(title, pageable);
        return ResponseEntity.ok(pagination);
    }

    @Operation(summary = "Get movie details", description = "Returns detailed information about a specific movie")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved movie details"),
        @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<MovieResponseDTO>> getMovieDetail(
            @Parameter(description = "Movie ID", required = true)
            @PathVariable String id) {
        MovieResponseDTO movie = movieService.getMovieDetailById(id);
        return ResponseEntity.ok(ResponseDTO.success(movie));
    }

    @Operation(summary = "Get movie showtimes", description = "Returns list of showtimes for a specific movie on a given date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved showtimes"),
        @ApiResponse(responseCode = "404", description = "Movie not found"),
        @ApiResponse(responseCode = "400", description = "Invalid date parameter")
    })
    @GetMapping("/{id}/showtime")
    public ResponseEntity<ResponseDTO<List<MovieShowtimeResponseDTO>>> getShowtime(
            @Parameter(description = "Movie ID", required = true)
            @PathVariable String id,
            @Parameter(description = "Date in epoch days from Unix epoch", required = true)
            @RequestParam
            @ValidEpochDayFromToday
            Long date
    ) {
        List<MovieShowtimeResponseDTO> showtime = showtimeService.getShowtime(id, date);
        return ResponseEntity.ok(ResponseDTO.success(showtime));
    }

    @Operation(summary = "Create a new movie", description = "Creates a new movie in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Movie successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Boolean> createMovie(
            @Parameter(description = "Movie details", required = true)
            @Valid @RequestBody MovieRequestDTO movieRequestDTO
    ) {
        movieService.createMovie(movieRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }
}
