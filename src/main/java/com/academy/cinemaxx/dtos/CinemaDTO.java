package com.academy.cinemaxx.dtos;

public record CinemaDTO (
        String secureId,
        String name,
        String address,
        CityDTO city
) {}