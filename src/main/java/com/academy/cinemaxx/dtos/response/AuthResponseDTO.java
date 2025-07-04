package com.academy.cinemaxx.dtos.response;

import com.academy.cinemaxx.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponseDTO(
        @Schema(example = "e9b13740-0371-42a5-85e1-68baa68218c7")
        String id,
        @Schema(example = "jwt-token")
        String token,
        @Schema(example = "refresh-token")
        String refreshToken,
        @Schema(example = "John Doe")
        String name,
        @Schema(example = "john.doe@mail.com")
        String email,
        @Schema(example = "ROLE_USER")
        UserRole role
) { }