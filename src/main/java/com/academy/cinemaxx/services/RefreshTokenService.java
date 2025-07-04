package com.academy.cinemaxx.services;

import com.academy.cinemaxx.entities.RefreshToken;
import com.academy.cinemaxx.entities.User;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    RefreshToken verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
} 