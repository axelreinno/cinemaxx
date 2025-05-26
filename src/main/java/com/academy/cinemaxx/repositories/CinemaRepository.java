package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    List<Cinema> findByCity_CodeIgnoreCase(String code);
}