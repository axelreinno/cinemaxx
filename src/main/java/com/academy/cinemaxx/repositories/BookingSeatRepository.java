package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
    @Query("""
        SELECT bs FROM BookingSeat bs
        JOIN bs.booking b
        JOIN b.showtime st
        WHERE st.secureId = :id
        AND b.bookingStatus IN ('PENDING', 'BOOKED')
    """)
    List<BookingSeat> findBookedSeatsByShowtimeSecureId(@Param("id") String showtimeId);

    @Query("""
        SELECT bs FROM BookingSeat bs
        JOIN bs.booking b
        JOIN b.showtime st
        WHERE st.secureId = :id
        AND bs.seat.id IN :seatIds
        AND b.bookingStatus IN ('PENDING', 'BOOKED')
    """)
    List<BookingSeat> findExistingBookingsForSeats(
        @Param("id") String showtimeId,
        @Param("seatIds") List<Long> seatIds
    );
} 