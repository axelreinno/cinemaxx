package com.academy.cinemaxx.dtos;

public record CinemaResponseDTO (
        String id,
        String name,
        String address,
        CityResponseDTO city
) {}