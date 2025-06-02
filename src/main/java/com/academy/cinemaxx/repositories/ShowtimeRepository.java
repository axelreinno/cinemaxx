package com.academy.cinemaxx.repositories;

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
        SELECT s FROM Showtime s
        JOIN FETCH s.hall h
        JOIN FETCH h.cinema c
        JOIN FETCH c.city
        WHERE s.movie.secureId = :secureId
        AND DATE(s.startTime) = :date
        ORDER BY c.name, h.name, s.startTime
    """)
    List<Showtime> findByMovieSecureIdAndDate(
            @Param("secureId") String secureId,
            @Param("date") LocalDate date
    );
}