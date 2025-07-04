package com.academy.cinemaxx.dtos.response;

import com.academy.cinemaxx.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record BookingListResponseDTO(
        @Schema(example = "e9b13740-0371-42a5-85e1-68baa68218c7")
        String id,
        @Schema(example = "john.doe@mail.com")
        String email,
        @Schema(example = "John Doe")
        String name,
        @Schema(example = "Mission: Impossible - The Final Reckoning")
        String movie,
        @Schema(example = "https://thumbail.com")
        String thumbnailUrl,
        @Schema(example = "CGV Satin Hall 1")
        String hallName,
        @Schema(example = "PENDING")
        BookingStatus bookingStatus,
        @Schema(example = "1751557046")
        Long showtimeAt,
        @Schema(example = "1751557046")
        Long paymentAt,
        @Schema(example = "1751557046")
        Long paymentExpiredAt,
        @Schema(example = "50000")
        BigDecimal totalPrice,
        @Schema(example = "1")
        Integer totalSeats
) { }
