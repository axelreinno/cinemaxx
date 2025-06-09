package com.academy.cinemaxx.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;


public record BookingSeatsRequestDTO(
    @NotNull(message = "User ID is required")
    String userId,

    @NotNull(message = "Seat IDs are required")
    @Size(min = 1, message = "At least one seat must be selected")
    List<String> seatIds
) {

}