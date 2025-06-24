package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.request.BookingSeatsRequestDTO;
import com.academy.cinemaxx.dtos.response.BookingListResponseDTO;
import com.academy.cinemaxx.dtos.response.BookingDetailResponseDTO;
import com.academy.cinemaxx.dtos.response.PaginationResponseDTO;
import com.academy.cinemaxx.dtos.response.ResponseDTO;
import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.enums.SortDirection;
import com.academy.cinemaxx.services.BookingService;
import com.academy.cinemaxx.validators.annotations.ValidSortDirection;
import com.academy.cinemaxx.validators.annotations.ValidSortField;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/v1/booking")
@Tag(name = "Booking", description = "Booking APIs")
@SecurityRequirement(name = "bearerAuth")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(
            BookingService bookingService
    ) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Boolean> createBooking(
            @Valid @RequestBody BookingSeatsRequestDTO request
    ) {
        bookingService.createBooking(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(true);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PaginationResponseDTO<BookingListResponseDTO>> getBooking(
            @RequestParam(required = false) String movie,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit,
            @ValidSortField(allowed = {"createdAt","paymentAt","paymentExpiredAt"}) @RequestParam(defaultValue = "createdAt") String sort,
            @ValidSortDirection @RequestParam(defaultValue = "desc") String direction
    ) {
        SortDirection sortDirection = SortDirection.from(direction);
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection.toSpringSortDirection(), sort));
        PaginationResponseDTO<BookingListResponseDTO> pagination = bookingService.getBookings(movie, name, email, status, pageable);
        return ResponseEntity.ok(pagination);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO<BookingDetailResponseDTO>> getBookingDetail(
            @PathVariable String id
    ) {
        BookingDetailResponseDTO booking = bookingService.getBookingBySecureId(id);
        return ResponseEntity.ok(ResponseDTO.success(booking));
    }

    @PutMapping("/{id}/pay")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Boolean> payBooking(
            @PathVariable String id
    ) {
        bookingService.payBooking(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Boolean> cancelBooking(
            @PathVariable String id
    ) {
        bookingService.cancelBooking(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }
}
