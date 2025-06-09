package com.academy.cinemaxx.projections;

import java.time.LocalDateTime;

public interface ShowtimeProjection {
    String getCinemaName();
    String getHallName();
    String getSecureId();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
}