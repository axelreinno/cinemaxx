package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.response.ResponseDTO;
import com.academy.cinemaxx.dtos.response.SeatRowResponseDTO;
import com.academy.cinemaxx.services.impl.SeatServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/showtimes")
public class ShowtimeController {
    private final SeatServiceImpl seatService;

    public ShowtimeController(SeatServiceImpl seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<ResponseDTO<List<SeatRowResponseDTO>>> getSeatsByShowtime(
            @PathVariable String id
    ) {
        List<SeatRowResponseDTO> seats = seatService.findSeatsByShowtimeId(id);
        return ResponseEntity.ok(ResponseDTO.success(seats));
    }
} 