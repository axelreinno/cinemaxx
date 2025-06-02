package com.academy.cinemaxx.validators.annotations;

import com.academy.cinemaxx.validators.SortDirectionValidator;
import com.academy.cinemaxx.validators.SortFieldValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SortFieldValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSortField {
    String message() default "Invalid sort field";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String[] allowed() default {};
}