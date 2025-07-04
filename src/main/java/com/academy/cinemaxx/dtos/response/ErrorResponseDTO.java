package com.academy.cinemaxx.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
        @Schema(example = "400")
        int code,
        @Schema(example = "Bad Request")
        String error,
        @Schema(example = "/v1/path")
        String path,
        @Schema(example = "2025-07-03T22:42:16.132884")
        LocalDateTime timestamp,
        List<String> messages
) {
    public static ErrorResponseDTO error(int status, String error, String path) {
        return new ErrorResponseDTO(status, error, path, LocalDateTime.now(), List.of());
    }

    public static ErrorResponseDTO error(int status, String error, String path, List<String> messages) {
        return new ErrorResponseDTO(status, error, path, LocalDateTime.now(), messages);
    }
}