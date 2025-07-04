package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.response.ResponseDTO;
import com.academy.cinemaxx.dtos.response.SeatRowResponseDTO;
import com.academy.cinemaxx.services.impl.SeatServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/showtimes")
@Tag(name = "Showtimes", description = "Showtimes APIs")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('USER')")
public class ShowtimeController {
    private final SeatServiceImpl seatService;

    public ShowtimeController(SeatServiceImpl seatService) {
        this.seatService = seatService;
    }

    @Operation(summary = "Get seats showtimes", description = "Returns list of seats for a specific showtime")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved showtimes"),
    })
    @GetMapping("/{id}/seats")
    public ResponseEntity<ResponseDTO<List<SeatRowResponseDTO>>> getSeatsByShowtime(
            @PathVariable String id
    ) {
        List<SeatRowResponseDTO> seats = seatService.findSeatsByShowtimeId(id);
        return ResponseEntity.ok(ResponseDTO.success(seats));
    }
} 