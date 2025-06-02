package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.CityResponseDTO;
import com.academy.cinemaxx.dtos.PaginationResponseDTO;
import org.springframework.data.domain.Pageable;


public interface CityService {
    public PaginationResponseDTO<CityResponseDTO> getCities(String name, Pageable pageable);
}