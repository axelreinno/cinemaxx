package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.response.CinemaResponseDTO;
import com.academy.cinemaxx.dtos.response.PaginationResponseDTO;
import org.springframework.data.domain.Pageable;

public interface CinemaService {
    public PaginationResponseDTO<CinemaResponseDTO> getCinemas(String name, String cityCode, Pageable pageable);
}