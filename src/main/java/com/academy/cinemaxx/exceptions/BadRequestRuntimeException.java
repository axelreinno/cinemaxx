package com.academy.cinemaxx.exceptions;

public class BadRequestRuntimeException extends RuntimeException {
    public BadRequestRuntimeException(String message) {
        super(message);
    }
}
