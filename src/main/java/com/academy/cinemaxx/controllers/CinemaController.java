package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.CinemaDTO;
import com.academy.cinemaxx.entities.Cinema;
import com.academy.cinemaxx.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinema")
public class CinemaController {
    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/by-city-code/{code}")
    public ResponseEntity<List<CinemaDTO>> getCinemasByCityCode(@PathVariable(name = "code") String code) {
        return ResponseEntity.ok(cinemaService.getCinemasByCityCode(code));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CinemaDTO>> searchCinema(@RequestParam(name = "name") String name, @RequestParam(name = "cityCode") String cityCode) {
        return ResponseEntity.ok(cinemaService.searchCinemasByNameAndCityCode(name, cityCode));
    }
}
