package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.HallShowtimeDTO;
import com.academy.cinemaxx.dtos.MovieShowtimeDTO;
import com.academy.cinemaxx.dtos.ShowtimeDTO;
import com.academy.cinemaxx.entities.Showtime;
import com.academy.cinemaxx.repositories.MovieRepository;
import com.academy.cinemaxx.repositories.ShowtimeRepository;
import com.academy.cinemaxx.services.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {
    private final ShowtimeRepository showtimeRepository;

    @Autowired
    public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    public List<MovieShowtimeDTO> getShowtimeByMovieAndDate(String secureId, LocalDate date) {
        List<Showtime> showtimes = showtimeRepository.findByMovieSecureIdAndDate(secureId, date);

        List<MovieShowtimeDTO> result = showtimes.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getHall().getCinema().getName(), // Group by Cinema Name
                        LinkedHashMap::new,
                        Collectors.groupingBy(
                                s -> s.getHall().getType(),          // Group by Hall Name
                                Collectors.mapping(
                                        s -> new ShowtimeDTO(s.getStartTime()),
                                        Collectors.toList()
                                )
                        )
                ))
                .entrySet().stream()
                .map(cinemaEntry -> {
                    String cinemaName = cinemaEntry.getKey();

                    List<HallShowtimeDTO> halls = cinemaEntry.getValue().entrySet().stream()
                            .map(hallEntry -> new HallShowtimeDTO(hallEntry.getKey(), hallEntry.getValue()))
                            .collect(Collectors.toList());

                    return new MovieShowtimeDTO(cinemaName, halls);
                })
                .collect(Collectors.toList());

        return result;
    }
}
