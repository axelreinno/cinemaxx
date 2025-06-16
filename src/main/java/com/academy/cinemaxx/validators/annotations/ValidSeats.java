package com.academy.cinemaxx.validators.annotations;

import com.academy.cinemaxx.validators.SeatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SeatValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSeats {
    String message() default "Invalid seat selection";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 