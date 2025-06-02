package com.academy.cinemaxx.projections;

import java.time.LocalDateTime;

public interface ShowtimeProjection {
    String getCinemaName();
    String getHallName();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
}