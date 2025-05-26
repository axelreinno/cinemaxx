package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.Cinema;
import com.academy.cinemaxx.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findBySecureId(String secureId);

    @Query("""
        SELECT DISTINCT s.movie FROM Showtime s
        WHERE LOWER(s.hall.cinema.city.code) = LOWER(:cityCode)
        AND :currentTime BETWEEN s.startTime AND s.endTime
    """)
    List<Movie> findNowPlayingMoviesByCityCode(@Param("cityCode") String cityCode, @Param("currentTime") LocalDateTime currentTime);

    @Query("""
        SELECT DISTINCT s.movie FROM Showtime s
        WHERE LOWER(s.hall.cinema.city.code) = LOWER(:cityCode)
        AND s.startTime > :currentTime
        AND s.movie.id NOT IN (
            SELECT s2.movie.id FROM Showtime s2
            WHERE LOWER(s2.hall.cinema.city.code) = LOWER(:cityCode)
            AND :currentTime BETWEEN s2.startTime AND s2.endTime
       )
    """)
    List<Movie> findUpcomingMoviesByCityCode(@Param("cityCode") String cityCode, @Param("currentTime") LocalDateTime currentTime);

    @Query("""
        SELECT DISTINCT s.movie
        FROM Showtime s
        WHERE LOWER(s.movie.title) LIKE LOWER(CONCAT('%', :title, '%'))
        AND LOWER(s.hall.cinema.city.code) = LOWER(:cityCode)
        AND :currentTime BETWEEN s.startTime AND s.endTime
    """)
    List<Movie> findNowPlayingMoviesByTitleAndCityCode(
            @Param("title") String title,
            @Param("cityCode") String cityCode,
            @Param("currentTime") LocalDateTime currentTime
    );
}