package com.academy.cinemaxx.repositories;

import com.academy.cinemaxx.entities.User;
import com.academy.cinemaxx.entities.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {

    @Query("""
        SELECT 
            uo 
        FROM UserOtp uo 
        WHERE uo.user = :user 
        AND uo.otp = :otp 
        AND uo.expiresAt > :now 
        AND uo.isUsed = false
    """)
    Optional<UserOtp> findValidOtp(@Param("user") User user, @Param("otp") String otp, @Param("now") LocalDateTime now);

    @Query("""
        SELECT DISTINCT
            uo 
        FROM UserOtp uo 
        WHERE uo.user.id = :userId 
        AND uo.expiresAt > :now 
        AND uo.isUsed = false 
        ORDER BY uo.createdAt DESC
    """)
    Optional<UserOtp> findLatestActiveOtp(@Param("userId") Long userId, @Param("now") LocalDateTime now);

    Optional<UserOtp> findFirstByUserAndIsUsedFalseAndExpiresAtAfterOrderByCreatedAtDesc(User user, LocalDateTime now);
}