package com.academy.cinemaxx.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academy.cinemaxx.dtos.BookingSeatsRequestDTO;
import com.academy.cinemaxx.services.BookingSeatService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/booking")
public class BookingController {
    private final BookingSeatService bookingSeatService;

    public BookingController(BookingSeatService bookingSeatService) {
        this.bookingSeatService = bookingSeatService;
    }

    @PostMapping
    public ResponseEntity<Boolean> createBooking(
            @Valid @RequestBody BookingSeatsRequestDTO request
    ) {
        bookingSeatService.createBooking(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(true);
    }
    
}
