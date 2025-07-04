package com.academy.cinemaxx.dtos.request;

import com.academy.cinemaxx.validators.annotations.ValidSeats;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@ValidSeats
public record BookingSeatsRequestDTO(
        @Schema(example = "e9b13740-0371-42a5-85e1-68baa68218c7")
        @NotNull(message = "Showtime ID is required")
        String showtimeId,

        @NotNull(message = "Seat IDs are required")
        @Size(min = 1, message = "At least one seat must be selected")
        List<String> seatIds
) {

}