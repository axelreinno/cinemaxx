package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.CityDTO;
import com.academy.cinemaxx.entities.City;
import com.academy.cinemaxx.repositories.CityRepository;
import com.academy.cinemaxx.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityDTO> getAllCities() {
        return cityRepository
                .findAll()
                .stream()
                .map(city -> new CityDTO(city.getCode(), city.getName()))
                .collect(Collectors.toList());
    }
}