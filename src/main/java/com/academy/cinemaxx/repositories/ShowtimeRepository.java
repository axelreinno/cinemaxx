package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.projections.ShowtimeProjection;
import com.academy.cinemaxx.entities.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    @Query("""
        SELECT 
            c.name AS cinemaName,
            h.name AS hallName,
            s.secureId AS secureId,
            s.startTime AS startTime,
            s.endTime AS endTime
        FROM Showtime s
        JOIN s.hall h
        JOIN h.cinema c
        JOIN c.city ci
        JOIN s.movie m
        WHERE m.secureId = :id
        AND DATE(s.startTime) = :date
        ORDER BY c.name, h.name, s.startTime
    """)
    List<ShowtimeProjection> findShowtimeByMovieId(
            @Param("id") String id,
            @Param("date") LocalDate date
    );
}