package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.request.BookingSeatsRequestDTO;

public interface BookingSeatService {
    void createBooking(BookingSeatsRequestDTO bookingSeatsRequestDTO);
} 