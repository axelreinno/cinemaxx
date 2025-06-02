package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.CityResponseDTO;
import com.academy.cinemaxx.dtos.PaginationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CityService {
    public PaginationResponseDTO<CityResponseDTO> getCities(String name, Pageable pageable);
}