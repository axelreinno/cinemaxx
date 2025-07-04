package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.request.LoginRequestDTO;
import com.academy.cinemaxx.dtos.request.VerifyRequestDTO;
import com.academy.cinemaxx.dtos.response.*;
import com.academy.cinemaxx.services.AuthService;
import com.academy.cinemaxx.services.RefreshTokenService;
import com.academy.cinemaxx.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    // @PostMapping("/refresh")
    // public ResponseEntity<ResponseDTO<AuthResponseDTO>> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO request) {
    //     String requestRefreshToken = request.refreshToken();

    //     return refreshTokenService.findByToken(requestRefreshToken)
    //             .map(refreshTokenService::verifyExpiration)
    //             .map(RefreshToken::getUser)
    //             .map(user -> {
    //                 AuthResponseDTO authResponse = authService.generateAuthResponse(user);
    //                 return ResponseEntity.ok(
    //                         ResponseDTO.<AuthResponseDTO>builder()
    //                                 .status(HttpStatus.OK.value())
    //                                 .message("Token refreshed successfully")
    //                                 .data(authResponse)
    //                                 .build()
    //                 );
    //             })
    //             .orElseThrow(() -> new BadRequestRuntimeException("Refresh token not found!"));
    // }

    // @PostMapping("/logout")
    // public ResponseEntity<ResponseDTO<Void>> logout() {
    //     User currentUser = userService.getCurrentUser();
    //     refreshTokenService.deleteByUser(currentUser);
    //     return ResponseEntity.ok(
    //             ResponseDTO.<Void>builder()
    //                     .status(HttpStatus.OK.value())
    //                     .message("Logged out successfully")
    //                     .build()
    //     );
    // }
}