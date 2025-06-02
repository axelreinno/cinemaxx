package com.academy.cinemaxx.validators;

import com.academy.cinemaxx.validators.annotations.ValidEpochDayFromToday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class EpochDayFromTodayValidator implements ConstraintValidator<ValidEpochDayFromToday, Long> {
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) return true;
        long todayEpochDay = LocalDate.now().toEpochDay();
        return value >= todayEpochDay;
    }
}
