package com.academy.cinemaxx.validators.annotations;

import com.academy.cinemaxx.validators.EpochDayFromTodayValidator;
import com.academy.cinemaxx.validators.SortDirectionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EpochDayFromTodayValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEpochDayFromToday {
    String message() default "Epoch day must be greater than or equal to today";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
