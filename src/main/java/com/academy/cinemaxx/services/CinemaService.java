package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.CinemaDTO;
import com.academy.cinemaxx.entities.Cinema;

import java.util.List;

public interface CinemaService {
    public List<CinemaDTO> getCinemasByCityCode(String cityCode);
    public List<CinemaDTO> searchCinemasByNameAndCityCode(String name, String cityCode);
}
