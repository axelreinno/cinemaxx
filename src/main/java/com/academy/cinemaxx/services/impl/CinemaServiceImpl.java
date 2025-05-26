package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.CinemaDTO;
import com.academy.cinemaxx.dtos.CityDTO;
import com.academy.cinemaxx.entities.Cinema;
import com.academy.cinemaxx.repositories.CinemaRepository;
import com.academy.cinemaxx.repositories.CityRepository;
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

    public List<CinemaDTO> getCinemasByCityCode(String code) {
        return cinemaRepository
                .findByCity_CodeIgnoreCase(code)
                .stream()
                .map(cinema -> new CinemaDTO(cinema.getSecureId(), cinema.getName(), cinema.getAddress(), new CityDTO(cinema.getCity().getCode(), cinema.getCity().getName())))
                .collect(Collectors.toList());
    }
}
