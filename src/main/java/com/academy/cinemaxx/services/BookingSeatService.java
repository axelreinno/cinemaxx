package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.BookingSeatsRequestDTO;

public interface BookingSeatService {
    void createBooking(BookingSeatsRequestDTO bookingSeatsRequestDTO);
} 