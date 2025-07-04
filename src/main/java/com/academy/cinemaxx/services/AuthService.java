package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.request.LoginRequestDTO;
import com.academy.cinemaxx.dtos.request.VerifyRequestDTO;
import com.academy.cinemaxx.dtos.response.AuthResponseDTO;

public interface AuthService {
    void login(LoginRequestDTO request);
    AuthResponseDTO verify(VerifyRequestDTO request);
}
