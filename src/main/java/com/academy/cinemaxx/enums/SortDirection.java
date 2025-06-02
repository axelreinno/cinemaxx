package com.academy.cinemaxx.enums;

import org.springframework.data.domain.Sort;

public enum SortDirection {
    ASC, DESC;

    public Sort.Direction toSpringSortDirection() {
        return Sort.Direction.valueOf(this.name());
    }

    public static SortDirection from(String value) {
        return value == null ? ASC : SortDirection.valueOf(value.toUpperCase());
    }

    public static boolean isValid(String value) {
        if (value == null) return false;
        try {
            SortDirection.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
