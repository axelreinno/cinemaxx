package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.Showtime;
import com.academy.cinemaxx.projections.ShowtimeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    Optional<Showtime> findBySecureId(String secureId);

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
        WHERE m.secureId = :secureId
        AND ci.code = :cityCode
        AND DATE(s.startTime) = :date
        AND s.deleted = false
        ORDER BY c.name, h.name, s.startTime
    """)
    List<ShowtimeProjection> findShowtimeByMovieSecureIdAndCityCode(
            @Param("secureId") String secureId,
            @Param("cityCode") String cityCode,
            @Param("date") LocalDate date
    );

}