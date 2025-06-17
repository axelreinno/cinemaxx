package com.academy.cinemaxx.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO (
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email
) { }
