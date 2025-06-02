package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.CityResponseDTO;

import java.util.List;

public interface CityService {
    public List<CityResponseDTO> getAllCities();
}