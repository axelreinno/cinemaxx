package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findBySecureId(String secureId);
    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("""
        SELECT DISTINCT s.movie FROM Showtime s
        WHERE LOWER(s.hall.cinema.city.code) = LOWER(:cityCode)
        AND DATE(s.startTime) > DATE(:currentTime)
        AND s.movie.id NOT IN (
            SELECT s2.movie.id FROM Showtime s2
            WHERE LOWER(s2.hall.cinema.city.code) = LOWER(:cityCode)
           AND DATE(s2.startTime) = DATE(:currentTime)
       )
    """)
    Page<Movie> findUpcomingMovies(
            @Param("cityCode") String cityCode,
            @Param("currentTime") LocalDateTime currentTime,
            Pageable pageable
    );

    @Query("""
        SELECT DISTINCT s.movie
        FROM Showtime s
        WHERE LOWER(s.hall.cinema.city.code) = LOWER(:cityCode)
        AND DATE(s.startTime) = DATE(:currentTime)
    """)
    Page<Movie> findNowPlayingMovies(
            @Param("cityCode") String cityCode,
            @Param("currentTime") LocalDateTime currentTime,
            Pageable pageable
    );
}