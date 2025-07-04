package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.response.MovieResponseDTO;
import com.academy.cinemaxx.dtos.response.PaginationResponseDTO;
import com.academy.cinemaxx.dtos.response.ResponseDTO;
import com.academy.cinemaxx.enums.SortDirection;
import com.academy.cinemaxx.services.MovieService;
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

@Validated
@RestController
@RequestMapping("/v1/movies")
@Tag(name = "Movies", description = "Movie APIs")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('USER')")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Get list of movies", description = "Returns a paginated list of movies with optional filtering and sorting")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved movies",
                    content = @Content(schema = @Schema(implementation = PaginationResponseDTO.class))
            ),
    })
    @GetMapping
    public ResponseEntity<PaginationResponseDTO<MovieResponseDTO>> getMovies(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit,
            @ValidSortField(allowed = {"title","ageRating","releaseDate"})
            @RequestParam(defaultValue = "title") String sort,
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
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<MovieResponseDTO>> getMovieDetail(
            @PathVariable String id) {
        MovieResponseDTO movie = movieService.getMovieDetailById(id);
        return ResponseEntity.ok(ResponseDTO.success(movie));
    }
}
