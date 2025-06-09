package com.academy.cinemaxx.projections;

import com.academy.cinemaxx.enums.SeatStatus;

import java.math.BigDecimal;

public interface SeatProjection {
    Long getId();
    String getSecureId();
    String getLabel();
    Integer getRow();
    Integer getColumn();
    SeatStatus getStatus();
    String getElement();
    BigDecimal getPrice();
}
