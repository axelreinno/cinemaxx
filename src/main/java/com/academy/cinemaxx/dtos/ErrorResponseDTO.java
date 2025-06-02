package com.academy.cinemaxx.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO<TError>(
        String status,
        String message,
        TError error
) {
    public static <TError> ErrorResponseDTO<TError> error(String message) {
        return new ErrorResponseDTO<>("error", message, null);
    }

    public static <TError> ErrorResponseDTO<TError> error(String message, TError error) {
        return new ErrorResponseDTO<>("error", message, error);
    }
}