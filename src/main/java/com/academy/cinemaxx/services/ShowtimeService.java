package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.MovieShowtimeResponseDTO;
import com.academy.cinemaxx.dtos.PaginationResponseDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ShowtimeService {
    public List<MovieShowtimeResponseDTO> getShowtime(String id, Long date);
}
