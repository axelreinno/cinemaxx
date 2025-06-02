package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Page<Cinema> findByCity_CodeIgnoreCase(String code, Pageable pageable);
    Page<Cinema> findByNameContainingIgnoreCaseAndCity_CodeIgnoreCase(String name, String code, Pageable pageable);
}