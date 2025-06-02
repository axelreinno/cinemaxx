package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.CinemaDTO;
import com.academy.cinemaxx.dtos.CityResponseDTO;
import com.academy.cinemaxx.repositories.CinemaRepository;
import com.academy.cinemaxx.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaServiceImpl(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public List<CinemaDTO> getCinemasByCityCode(String cityCode) {
        return cinemaRepository
                .findByCity_CodeIgnoreCase(cityCode)
                .stream()
                .map(cinema -> new CinemaDTO(cinema.getSecureId(), cinema.getName(), cinema.getAddress(), new CityResponseDTO(cinema.getCity().getCode(), cinema.getCity().getName())))
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> searchCinemasByNameAndCityCode(String name, String cityCode) {
        return cinemaRepository
                .findByNameContainingIgnoreCaseAndCity_CodeIgnoreCase(name, cityCode)
                .stream()
                .map(cinema -> new CinemaDTO(cinema.getSecureId(), cinema.getName(), cinema.getAddress(), new CityResponseDTO(cinema.getCity().getCode(), cinema.getCity().getName())))
                .collect(Collectors.toList());
    }
}
