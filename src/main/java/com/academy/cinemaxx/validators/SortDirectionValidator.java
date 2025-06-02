package com.academy.cinemaxx.validators;

import com.academy.cinemaxx.enums.SortDirection;
import com.academy.cinemaxx.validators.annotations.ValidSortDirection;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SortDirectionValidator implements ConstraintValidator<ValidSortDirection, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return SortDirection.isValid(value);
    }
}