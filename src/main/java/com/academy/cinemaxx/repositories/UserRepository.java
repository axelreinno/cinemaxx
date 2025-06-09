package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.Showtime;
import com.academy.cinemaxx.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySecureId(String secureId);
} 