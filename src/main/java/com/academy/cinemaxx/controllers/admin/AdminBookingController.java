package com.academy.cinemaxx.controllers.admin;

import com.academy.cinemaxx.dtos.response.BookingDetailResponseDTO;
import com.academy.cinemaxx.dtos.response.BookingListResponseDTO;
import com.academy.cinemaxx.dtos.response.PaginationResponseDTO;
import com.academy.cinemaxx.dtos.response.ResponseDTO;
import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.enums.SortDirection;
import com.academy.cinemaxx.services.BookingService;
import com.academy.cinemaxx.validators.annotations.ValidSortDirection;
import com.academy.cinemaxx.validators.annotations.ValidSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Validated
@RestController
@RequestMapping("/v1/admin/bookings")
@Tag(name = "Admin Booking", description = "Booking APIs For Admin Role")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBookingController {
    private final BookingService bookingService;

    public AdminBookingController(
            BookingService bookingService
    ) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get list of booking", description = "Returns a paginated list of booking with optional filtering and sorting")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved booking",
                    content = @Content(schema = @Schema(implementation = PaginationResponseDTO.class))
            ),
    })
    @GetMapping
    public ResponseEntity<PaginationResponseDTO<BookingListResponseDTO>> getBookings(
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

    @Operation(summary = "Get booking details", description = "Returns detailed information about a specific booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved booking details"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<BookingDetailResponseDTO>> getBookingBySecureId(
            @PathVariable String id
    ) {
        BookingDetailResponseDTO booking = bookingService.getBookingBySecureId(id);
        return ResponseEntity.ok(ResponseDTO.success(booking));
    }

    @Operation(summary = "Update a booking payments", description = "Update booking status to paid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking status successfully updated"),
    })
    @PatchMapping("/{id}/payments")
    public ResponseEntity<Boolean> payBooking(
            @PathVariable String id
    ) {
        bookingService.payBooking(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }

    @Operation(summary = "Update a booking cancellations", description = "Update booking status to cancelled")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking status successfully updated"),
    })
    @PatchMapping("/{id}/cancellations")
    public ResponseEntity<Boolean> cancelBooking(
            @PathVariable String id
    ) {
        bookingService.cancelBooking(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }
}
