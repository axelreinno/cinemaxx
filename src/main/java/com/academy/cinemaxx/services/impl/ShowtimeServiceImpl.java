package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.response.HallShowtimeResponseDTO;
import com.academy.cinemaxx.dtos.response.MovieShowtimeResponseDTO;
import com.academy.cinemaxx.dtos.response.ShowtimeResponseDTO;
import com.academy.cinemaxx.projections.ShowtimeProjection;
import com.academy.cinemaxx.repositories.ShowtimeRepository;
import com.academy.cinemaxx.services.ShowtimeService;
import com.academy.cinemaxx.utils.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {
    private final ShowtimeRepository showtimeRepository;

    public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    public List<MovieShowtimeResponseDTO> getShowtime(String id, Long date) {
        LocalDate currentDate = DateTimeUtils.fromEpochDay(date);
        List<ShowtimeProjection> projections = showtimeRepository.findShowtimeByMovieId(id, currentDate);

        return projections
                .stream()
                .collect(Collectors.groupingBy(
                        ShowtimeProjection::getCinemaName,
                        LinkedHashMap::new,
                        Collectors.groupingBy(
                                ShowtimeProjection::getHallName,
                                Collectors.mapping(
                                        showtime -> {
                                            long startTime = DateTimeUtils.toEpochSecond(showtime.getStartTime());
                                            long endTime = DateTimeUtils.toEpochSecond(showtime.getEndTime());
                                            return new ShowtimeResponseDTO(showtime.getSecureId(), startTime, endTime);
                                        },
                                        Collectors.toList()
                                )
                        )
                ))
                .entrySet().stream()
                .map(cinemaEntry -> {
                    String cinemaName = cinemaEntry.getKey();
                    List<HallShowtimeResponseDTO> halls = cinemaEntry.getValue().entrySet().stream()
                            .map(hallEntry -> new HallShowtimeResponseDTO(hallEntry.getKey(), hallEntry.getValue()))
                            .collect(Collectors.toList());
                    return new MovieShowtimeResponseDTO(cinemaName, halls);
                })
                .toList();
    }
}
