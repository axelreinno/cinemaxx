package com.academy.cinemaxx.exceptions;

import com.academy.cinemaxx.dtos.ErrorResponseDTO;
import com.academy.cinemaxx.dtos.ResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO<Object>> handleEntityNotFound(EntityNotFoundException ex) {
        log.warn("LOG: Entity not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(ErrorResponseDTO.error("Request Error", errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO<Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String path = cv.getPropertyPath().toString();
            String field = path.contains(".") ? path.substring(path.lastIndexOf(".") + 1) : path;
            errors.put(field, cv.getMessage());
        });
        log.warn("LOG: Validation Error: {}", errors.toString());
        return ResponseEntity.badRequest().body(ErrorResponseDTO.error("Validation Error", errors));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("LOG: Illegal Argument: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponseDTO.error(ex.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO<Object>> handleMissingParams(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().body(ErrorResponseDTO.error(ex.getParameterName() + " is required"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO<Object>> handleGenericException(Exception ex) {
        log.error("LOG: Unhandled exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDTO.error("An unexpected error occurred"));
    }
}