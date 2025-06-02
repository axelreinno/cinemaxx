package com.academy.cinemaxx.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDTO<TData>(
        String status,
        String message,
        TData data
) {
    public static <TData> ResponseDTO<TData> success(TData data) {
        return new ResponseDTO<>("success", "OK", data);
    }

    public static <TData> ResponseDTO<TData> success(String message, TData data) {
        return new ResponseDTO<>("success", message, data);
    }
}