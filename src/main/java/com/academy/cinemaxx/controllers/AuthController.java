package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.request.LoginRequestDTO;
import com.academy.cinemaxx.dtos.request.RegisterRequestDTO;
import com.academy.cinemaxx.dtos.request.VerifyRequestDTO;
import com.academy.cinemaxx.dtos.response.*;
import com.academy.cinemaxx.services.AuthService;
import com.academy.cinemaxx.services.RefreshTokenService;
import com.academy.cinemaxx.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Auth", description = "Auth APIs")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, UserService userService) {
        this.authService = authService;
    }

    @Operation(summary = "Login using email", description = "Login Authentication using email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successfully"),
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {
        authService.login(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.success("OTP berhasil dikirim silahkan cek email anda!"));
    }

    @Operation(summary = "Verify OTP", description = "OTP verification based on otp that send to email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successfully"),
    })
    @PostMapping("/verify")
    public ResponseEntity<ResponseDTO<AuthResponseDTO>> verify(
            @Valid @RequestBody VerifyRequestDTO request
    ) {
        AuthResponseDTO response = authService.verify(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.success(response));
    }

    @Operation(summary = "User Registration", description = "Register user into system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Register successfully"),
    })
    @PostMapping("/register")
    public ResponseEntity<Boolean> register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {
        authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(true);
    }
}