package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.CinemaResponseDTO;
import com.academy.cinemaxx.dtos.PaginationResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CinemaService {
    public PaginationResponseDTO<CinemaResponseDTO> getCinemas(String name, String cityCode, Pageable pageable);
}