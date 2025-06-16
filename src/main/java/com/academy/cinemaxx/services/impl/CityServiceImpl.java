package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.response.CityResponseDTO;
import com.academy.cinemaxx.dtos.response.PaginationResponseDTO;
import com.academy.cinemaxx.entities.City;
import com.academy.cinemaxx.repositories.CityRepository;
import com.academy.cinemaxx.services.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    public PaginationResponseDTO<CityResponseDTO> getCities(String name, Pageable pageable) {
        Page<City> cities;

        if(name != null && !name.isBlank()) {
            cities = cityRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            cities = cityRepository.findAll(pageable);
        }

        return new PaginationResponseDTO<>(
                cities.getSize(),
                cities.getTotalPages(),
                cities.getTotalElements(),
                cities.map(city -> new CityResponseDTO(city.getCode(), city.getName())).getContent()
        );
    }
}