package com.academy.cinemaxx.dtos.response;

public record CinemaResponseDTO (
        String id,
        String name,
        String address,
        CityResponseDTO city
) {}