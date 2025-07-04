package com.academy.cinemaxx.dtos.response;

import com.academy.cinemaxx.enums.AgeRating;
import com.academy.cinemaxx.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookingDetailResponseDTO(
        @Schema(example = "e9b13740-0371-42a5-85e1-68baa68218c7")
        String id,
        @Schema(example = "john.doe@mail.com")
        String email,
        @Schema(example = "John Doe")
        String name,
        @Schema(example = "Mission: Impossible - The Final Reckoning")
        String movie,
        @Schema(example = "Mission: Impossible – The Final Reckoning adalah sebuah film aksi laga mata-mata Amerika Serikat tahun 2025 yang disutradarai oleh Christopher McQuarrie dari sebuah skenario yang ditulisnya bersama Erik Jendresen.")
        String movieDescription,
        @Schema(example = "120")
        int durationMinutes,
        @Schema(example = "SU")
        AgeRating ageRating,
        @Schema(example = "https://thumbail.com")
        String thumbnailUrl,
        @Schema(example = "CGV Satin Hall 1")
        String hall,
        @Schema(example = "REGULAR")
        String hallType,
        @Schema(example = "CGV Cinemas FX Sudirman")
        String cinema,
        @Schema(example = "Jl. Jenderal Sudirman No.25, RT.1/RW.3, Gelora, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10270")
        String cinemaAddress,
        @Schema(example = "Jakarta")
        String city,
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
        Integer totalSeats,
        List<BookingSeatDetailResponseDTO> seats
) { } 