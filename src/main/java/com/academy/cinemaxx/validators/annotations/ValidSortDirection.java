package com.academy.cinemaxx.validators.annotations;

import com.academy.cinemaxx.validators.SortDirectionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SortDirectionValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSortDirection {
    String message() default "Invalid sort direction. Allowed values: ASC, DESC";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}