package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.request.BookingSeatsRequestDTO;
import com.academy.cinemaxx.dtos.response.PaginationResponseDTO;
import com.academy.cinemaxx.dtos.response.BookingListResponseDTO;
import com.academy.cinemaxx.dtos.response.BookingDetailResponseDTO;
import com.academy.cinemaxx.enums.BookingStatus;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    void createBooking(BookingSeatsRequestDTO bookingSeatsRequestDTO);
    PaginationResponseDTO<BookingListResponseDTO> getBookings(String movie, String name, String email, BookingStatus status, Pageable pageable);
    BookingDetailResponseDTO getBookingBySecureId(String secureId);
} 