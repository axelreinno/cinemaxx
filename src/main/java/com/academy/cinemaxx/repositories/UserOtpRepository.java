package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.User;
import com.academy.cinemaxx.entities.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {
    Optional<UserOtp> findFirstByUserAndIsUsedFalseAndExpiresAtAfterOrderByCreatedAtDesc(User user, LocalDateTime now);
}