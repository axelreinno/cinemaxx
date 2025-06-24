package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.Booking;
import com.academy.cinemaxx.enums.BookingStatus;
import com.academy.cinemaxx.projections.BookingListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
            SELECT b.secureId as secureId,
                   u.email as email,
                   u.name as name,
                   m.title as movieTitle,
                   b.bookingStatus as bookingStatus,
                   b.paymentAt as paymentAt,
                   b.paymentExpiredAt as paymentExpiredAt,
                   b.createdAt as createdAt,
                   (SELECT SUM(bs.price) FROM BookingSeat bs WHERE bs.booking = b) as totalPrice,
                   (SELECT COUNT(bs) FROM BookingSeat bs WHERE bs.booking = b) as totalSeats
            FROM Booking b
            JOIN User u ON u.id = b.user.id
            JOIN Showtime s ON s.id = b.showtime.id
            JOIN movie m ON m.id = s.movie.id
            WHERE b.deleted = false
            AND (:movie IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :movie, '%')))
            AND (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')))
            AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
            AND (:status IS NULL OR b.bookingStatus = :status)
            ORDER BY b.createdAt DESC
            """)
    Page<BookingListProjection> findAllBookingList(
            @Param("movie") String movie,
            @Param("name") String name,
            @Param("email") String email,
            @Param("status") BookingStatus status,
            Pageable pageable
    );

    @Query("""
            SELECT b.secureId as secureId,
                   u.email as email,
                   u.name as name,
                   m.title as movieTitle,
                   b.bookingStatus as bookingStatus,
                   b.paymentAt as paymentAt,
                   b.paymentExpiredAt as paymentExpiredAt,
                   b.createdAt as createdAt,
                   (SELECT SUM(bs.price) FROM BookingSeat bs WHERE bs.booking = b) as totalPrice,
                   (SELECT COUNT(bs) FROM BookingSeat bs WHERE bs.booking = b) as totalSeats
            FROM Booking b
            JOIN User u ON u.id = b.user.id
            JOIN Showtime s ON s.id = b.showtime.id
            JOIN movie m ON m.id = s.movie.id
            WHERE b.deleted = false
            AND b.secureId = :secureId
            """)
    Optional<BookingListProjection> findBookingBySecureId(@Param("secureId") String secureId);

    Optional<Booking> findBySecureId(String secureId);
} 