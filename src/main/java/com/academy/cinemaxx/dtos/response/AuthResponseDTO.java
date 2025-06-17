package com.academy.cinemaxx.dtos.response;

public record AuthResponseDTO(
        String id,
        String token,
        String name,
        String email,
        String role
) { }