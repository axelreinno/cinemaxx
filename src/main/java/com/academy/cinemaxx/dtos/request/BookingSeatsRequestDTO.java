package com.academy.cinemaxx.dtos.request;

import com.academy.cinemaxx.validators.annotations.ValidSeats;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@ValidSeats
public record BookingSeatsRequestDTO(
    @NotNull(message = "Showtime ID is required")
    String showtimeId,

    @NotNull(message = "Seat IDs are required")
    @Size(min = 1, message = "At least one seat must be selected")
    List<String> seatIds
) {

}