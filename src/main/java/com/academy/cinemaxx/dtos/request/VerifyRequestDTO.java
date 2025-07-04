package com.academy.cinemaxx.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyRequestDTO(
        @Schema(example = "john.doe@mail.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
        @Schema(example = "123456")
        @NotBlank(message = "OTP is required")
        String otp
) { }
