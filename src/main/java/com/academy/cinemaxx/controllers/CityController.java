package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.CityDTO;
import com.academy.cinemaxx.dtos.ResponseDTO;
import com.academy.cinemaxx.entities.City;
import com.academy.cinemaxx.repositories.CityRepository;
import com.academy.cinemaxx.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<CityDTO>>> getAllCities() {
        return ResponseEntity.ok(ResponseDTO.success(cityService.getAllCities()));
    }
}