package com.academy.cinemaxx.exceptions;

import com.academy.cinemaxx.dtos.response.ErrorResponseDTO;
import com.academy.cinemaxx.dtos.response.ResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Stream;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        log.error("LOG: Entity not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.error(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        request.getRequestURI(),
                        List.of(ex.getMessage())));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ResponseDTO<String>> handleLoginException(HttpServletRequest request) {
        log.error("LOG: Login exception: {}", request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.success("OTP berhasil dikirim silahkan cek email anda!"));
    }

    @ExceptionHandler(BadRequestRuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(BadRequestRuntimeException ex, HttpServletRequest request) {
        log.error("LOG: Bad request: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.error(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        request.getRequestURI(),
                        List.of(ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("LOG: Validation exceptions: {}", ex.getMessage());
        List<String> fieldMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        List<String> globalMessages = ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .toList();

        List<String> messages = Stream.concat(fieldMessages.stream(), globalMessages.stream())
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.error(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        request.getRequestURI(),
                        messages));
    }
    

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        log.error("LOG: Constraint violation exception: {}", ex.getMessage());
        List<String> messages = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    String path = violation.getPropertyPath().toString();
                    String field = path.contains(".") ? path.substring(path.lastIndexOf(".") + 1) : path;
                    return field + ": " + violation.getMessage();
                })
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.error(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        request.getRequestURI(),
                        messages));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        log.error("LOG: Illegal argument exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.error(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        request.getRequestURI(),
                        List.of(ex.getMessage())));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingParams(MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.error("LOG: Missing servlet request parameter exception: {}", ex.getMessage());
        String message = "Missing required parameter: " + ex.getParameterName();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.error(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        request.getRequestURI(),
                        List.of(message)));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.error("LOG: Access denied exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseDTO.error(
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(),
                        request.getRequestURI(),
                        List.of("Access denied. You don't have permission to access this resource.")));
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationServiceException(AuthenticationServiceException ex, HttpServletRequest request) {
        log.error("LOG: Authentication service exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDTO.error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        request.getRequestURI(),
                        List.of(ex.getMessage())));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
        log.error("LOG: No resource found exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.error(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        request.getRequestURI(),
                        List.of(ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        log.error("LOG: Unexpected exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDTO.error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        request.getRequestURI(),
                        List.of("Unexpected error occurred, please contact support.")));
    }
}