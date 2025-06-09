package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.Seat;
import com.academy.cinemaxx.projections.SeatProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("""
        SELECT
            s.id AS id,
            s.secureId AS secureId,
            s.label AS label,
            s.row AS row,
            s.column AS column,
            s.status AS status,
            se.code AS element,
            st.price AS price
        FROM Seat s
        JOIN s.seatElement se
        JOIN s.hall h
        JOIN h.showtimes st
        WHERE st.secureId = :showtimeSecureId
        ORDER BY s.row, s.column
    """)
    List<SeatProjection> findSeatsByShowtimeSecureId(@Param("showtimeSecureId") String showtimeSecureId);
} 