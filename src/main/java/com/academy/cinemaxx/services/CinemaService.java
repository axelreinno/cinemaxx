package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.CinemaResponseDTO;

import java.util.List;

public interface CinemaService {
    public List<CinemaResponseDTO> getCinemasByCityCode(String cityCode);
    public List<CinemaResponseDTO> searchCinemasByNameAndCityCode(String name, String cityCode);
}
