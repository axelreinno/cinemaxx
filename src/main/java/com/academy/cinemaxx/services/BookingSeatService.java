package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.BookingSeatsRequestDTO;
import com.academy.cinemaxx.entities.Booking;

public interface BookingSeatService {
    void bookSeats(String id, BookingSeatsRequestDTO bookingSeatsRequestDTO);
} 