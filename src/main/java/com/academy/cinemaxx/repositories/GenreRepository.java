package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByCodeIn(List<String> codes);
} 