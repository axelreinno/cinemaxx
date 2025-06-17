package com.academy.cinemaxx.services;

import com.academy.cinemaxx.entities.User;

public interface JwtService {
    String generateToken(User user);
    String extractUsername(String token);
    boolean isTokenValid(String token);
} 