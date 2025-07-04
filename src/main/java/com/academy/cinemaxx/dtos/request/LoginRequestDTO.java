package com.academy.cinemaxx.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email
) { }
