package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.BookingSeatsRequestDTO;
import com.academy.cinemaxx.dtos.ResponseDTO;
import com.academy.cinemaxx.dtos.SeatResponseDTO;
import com.academy.cinemaxx.dtos.SeatRowResponseDTO;
import com.academy.cinemaxx.entities.Booking;
import com.academy.cinemaxx.services.BookingSeatService;
import com.academy.cinemaxx.services.impl.SeatServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/showtimes")
public class ShowtimeController {
    private final SeatServiceImpl seatService;
    private final BookingSeatService bookingSeatService;

    public ShowtimeController(SeatServiceImpl seatService, BookingSeatService bookingSeatService) {
        this.seatService = seatService;
        this.bookingSeatService = bookingSeatService;
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<ResponseDTO<List<SeatRowResponseDTO>>> getSeatsByShowtime(
            @PathVariable String id
    ) {
        List<SeatRowResponseDTO> seats = seatService.findSeatsByShowtimeId(id);
        return ResponseEntity.ok(ResponseDTO.success(seats));
    }

    @PostMapping("/{id}/seats")
    public ResponseEntity<Boolean> bookSeats(
            @PathVariable("id") String showtimeId,
            @Valid @RequestBody BookingSeatsRequestDTO request
    ) {
        bookingSeatService.bookSeats(showtimeId, request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(true);
    }
} 