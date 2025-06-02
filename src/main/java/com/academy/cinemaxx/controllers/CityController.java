package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.CinemaDTO;
import com.academy.cinemaxx.dtos.CityResponseDTO;
import com.academy.cinemaxx.dtos.MovieResponseDTO;
import com.academy.cinemaxx.dtos.ResponseDTO;
import com.academy.cinemaxx.services.CinemaService;
import com.academy.cinemaxx.services.CityService;
import com.academy.cinemaxx.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CityController {
    private final CityService cityService;
    private final CinemaService cinemaService;
    private final MovieService movieService;

    public CityController(CityService cityService, CinemaService cinemaService, MovieService movieService) {
        this.cityService = cityService;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
    }

    @GetMapping("/cities")
    public ResponseEntity<ResponseDTO<List<CityResponseDTO>>> getAllCities() {
        return ResponseEntity.ok(ResponseDTO.success(cityService.getAllCities()));
    }

    @GetMapping("/city/{code}/playing-movie")
    public ResponseEntity<ResponseDTO<List<MovieResponseDTO>>> getNowPlayingMovies(@PathVariable("code") String cityCode) {
        List<MovieResponseDTO> movies = movieService.getNowPlayingMovies(cityCode);
        return ResponseEntity.ok(ResponseDTO.success(movies));
    }

    @GetMapping("/city/{code}/upcoming-movie")
    public ResponseEntity<ResponseDTO<List<MovieResponseDTO>>> getUpcomingMovies(@PathVariable("code") String cityCode) {
        List<MovieResponseDTO> movies = movieService.getUpcomingMovies(cityCode);
        return ResponseEntity.ok(ResponseDTO.success(movies));
    }

    @GetMapping("/city/{code}/movie")
    public ResponseEntity<ResponseDTO<List<MovieResponseDTO>>> searchNowPlayingMoviesByTitleAndCityCode(
            @PathVariable("code") String cityCode,
            @RequestParam(name = "name", required = false) String name
    ) {
        List<MovieResponseDTO> movies = movieService.findNowPlayingMoviesByTitleAndCityCode(name, cityCode);
        return ResponseEntity.ok(ResponseDTO.success(movies));
    }

    @GetMapping("/city/{code}/movie")
    public ResponseEntity<ResponseDTO<List<CinemaDTO>>> getCinemasByCityCode(@PathVariable(name = "code") String code) {
        return ResponseEntity.ok(ResponseDTO.success(cinemaService.getCinemasByCityCode(code)));
    }

    @GetMapping("/city/{code}/cinema")
    public ResponseEntity<ResponseDTO<List<CinemaDTO>>> searchCinema(
            @PathVariable(name = "code") String cityCode,
            @RequestParam(name = "name", required = false) String name
    ) {
        return ResponseEntity.ok(ResponseDTO.success(cinemaService.searchCinemasByNameAndCityCode(name, cityCode)));
    }
}