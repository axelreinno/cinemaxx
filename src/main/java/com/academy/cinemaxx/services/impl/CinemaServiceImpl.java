package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.CinemaResponseDTO;
import com.academy.cinemaxx.dtos.CityResponseDTO;
import com.academy.cinemaxx.dtos.PaginationResponseDTO;
import com.academy.cinemaxx.entities.Cinema;
import com.academy.cinemaxx.repositories.CinemaRepository;
import com.academy.cinemaxx.services.CinemaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;

    public CinemaServiceImpl(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public PaginationResponseDTO<CinemaResponseDTO> getCinemas(String name, String cityCode, Pageable pageable) {
        Page<Cinema> cinemas;

        if(name != null && !name.isBlank()) {
            cinemas = cinemaRepository.findByNameContainingIgnoreCaseAndCity_CodeIgnoreCase(name, cityCode, pageable);
        } else {
            cinemas = cinemaRepository.findByCity_CodeIgnoreCase(cityCode, pageable);
        }

        return new PaginationResponseDTO<>(
                cinemas.getSize(),
                cinemas.getTotalPages(),
                cinemas.getTotalElements(),
                cinemas.map(
                        cinema -> new CinemaResponseDTO(
                            cinema.getSecureId(),
                            cinema.getName(),
                            cinema.getAddress(),
                            new CityResponseDTO(
                                    cinema.getCity().getCode(),
                                    cinema.getCity().getName()
                            )
                        )
                ).getContent()
        );
    }
}
