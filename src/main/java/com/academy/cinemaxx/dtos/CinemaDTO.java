package com.academy.cinemaxx.dtos;

public record CinemaDTO (
        String id,
        String name,
        String address,
        CityDTO city
) {}