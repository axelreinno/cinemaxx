package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.MovieDTO;
import com.academy.cinemaxx.dtos.MovieShowtimeDTO;
import com.academy.cinemaxx.dtos.ResponseDTO;
import com.academy.cinemaxx.entities.Showtime;
import com.academy.cinemaxx.services.MovieService;
import com.academy.cinemaxx.services.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/showtime")
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @Autowired
    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @GetMapping("/movie/{secureId}")
    public ResponseEntity<ResponseDTO<List<MovieShowtimeDTO>>> getShowtimeByMovieAndDate(
            @PathVariable String secureId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<MovieShowtimeDTO> showtimes = showtimeService.getShowtimeByMovieAndDate(secureId, date);
        return ResponseEntity.ok(ResponseDTO.success(showtimes));
    }

}