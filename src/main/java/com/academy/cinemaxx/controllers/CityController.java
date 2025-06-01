package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.CinemaDTO;
import com.academy.cinemaxx.dtos.CityDTO;
import com.academy.cinemaxx.dtos.ResponseDTO;
import com.academy.cinemaxx.entities.City;
import com.academy.cinemaxx.repositories.CityRepository;
import com.academy.cinemaxx.services.CinemaService;
import com.academy.cinemaxx.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CityController {
    private final CityService cityService;
    private final CinemaService cinemaService;

    public CityController(CityService cityService, CinemaService cinemaService) {
        this.cityService = cityService;
        this.cinemaService = cinemaService;
    }

    @GetMapping("/cities")
    public ResponseEntity<ResponseDTO<List<CityDTO>>> getAllCities() {
        return ResponseEntity.ok(ResponseDTO.success(cityService.getAllCities()));
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