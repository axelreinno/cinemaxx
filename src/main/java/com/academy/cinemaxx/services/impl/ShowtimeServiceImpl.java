package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.HallShowtimeResponseDTO;
import com.academy.cinemaxx.dtos.MovieShowtimeResponseDTO;
import com.academy.cinemaxx.dtos.ShowtimeResponseDTO;
import com.academy.cinemaxx.entities.Showtime;
import com.academy.cinemaxx.repositories.ShowtimeRepository;
import com.academy.cinemaxx.services.ShowtimeService;
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

    public List<MovieShowtimeResponseDTO> getShowtimeByMovieAndDate(String secureId, LocalDate date) {
        List<Showtime> showtimes = showtimeRepository.findByMovieSecureIdAndDate(secureId, date);

        List<MovieShowtimeResponseDTO> result = showtimes.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getHall().getCinema().getName(), // Group by Cinema Name
                        LinkedHashMap::new,
                        Collectors.groupingBy(
                                s -> s.getHall().getType(),          // Group by Hall Name
                                Collectors.mapping(
                                        s -> new ShowtimeResponseDTO(s.getStartTime()),
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
                .collect(Collectors.toList());

        return result;
    }
}
