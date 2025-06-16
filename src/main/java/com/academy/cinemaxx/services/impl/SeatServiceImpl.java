package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.response.SeatResponseDTO;
import com.academy.cinemaxx.dtos.response.SeatRowResponseDTO;
import com.academy.cinemaxx.entities.BookingSeat;
import com.academy.cinemaxx.enums.SeatStatus;
import com.academy.cinemaxx.projections.SeatProjection;
import com.academy.cinemaxx.repositories.BookingSeatRepository;
import com.academy.cinemaxx.repositories.SeatRepository;
import com.academy.cinemaxx.services.SeatService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final BookingSeatRepository bookingSeatRepository;

    public SeatServiceImpl(SeatRepository seatRepository, BookingSeatRepository bookingSeatRepository) {
        this.seatRepository = seatRepository;
        this.bookingSeatRepository = bookingSeatRepository;
    }

    public List<SeatRowResponseDTO> findSeatsByShowtimeId(String showtimeId) {
        List<SeatProjection> seats = seatRepository.findSeatsByShowtimeSecureId(showtimeId);
        List<BookingSeat> bookedSeats = bookingSeatRepository.findBookedSeatsByShowtimeSecureId(showtimeId);
        Map<Long, Boolean> bookedSeatMap = bookedSeats.stream()
                .map(bs -> bs.getSeat().getId())
                .collect(Collectors.toMap(id -> id, id -> true));

        return seats.stream()
                .collect(
                    Collectors.groupingBy(
                        SeatProjection::getRow,
                        LinkedHashMap::new,
                        Collectors.toList()
                    )
                )
                .entrySet().stream()
                .map(rowEntry -> {
                    Integer row = rowEntry.getKey();
                    List<SeatResponseDTO> seatList = rowEntry.getValue().stream()
                            .map(seatEntry -> new SeatResponseDTO(
                                seatEntry.getSecureId(),
                                seatEntry.getLabel(),
                                seatEntry.getColumn(),
                                seatEntry.getPrice(),
                                bookedSeatMap.containsKey(seatEntry.getId()) ? SeatStatus.TAKEN : seatEntry.getStatus(),
                                seatEntry.getElement()))
                            .collect(Collectors.toList());
                    return new SeatRowResponseDTO(row, seatList);
                })
                .toList();
    }
} 