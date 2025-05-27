package com.academy.cinemaxx.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDTO<T>(
        String status,
        String message,
        T data
) {
    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>("success", "OK", data);
    }

    public static <T> ResponseDTO<T> success(String message, T data) {
        return new ResponseDTO<>("success", message, data);
    }

    public static <T> ResponseDTO<T> error(String message) {
        return new ResponseDTO<>("error", message, null);
    }
}