package com.academy.cinemaxx.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(
        @Schema(example = "John Doe")
        @NotBlank(message = "Full name is required")
        String fullName,
        @Schema(example = "john.doe@mail.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
        @Schema(example = "081234567890")
        @NotBlank(message = "Phone number is required")
        String phoneNumber
) { }