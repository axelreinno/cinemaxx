package com.academy.cinemaxx.validators;


import com.academy.cinemaxx.validators.annotations.ValidSortField;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class SortFieldValidator implements ConstraintValidator<ValidSortField, String> {
    private String[] allowedFields;

    @Override
    public void initialize(ValidSortField constraintAnnotation) {
        this.allowedFields = constraintAnnotation.allowed();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(allowedFields).contains(value);
    }
}