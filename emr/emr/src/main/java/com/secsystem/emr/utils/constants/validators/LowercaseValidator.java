package com.secsystem.emr.utils.constants.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LowercaseValidator implements ConstraintValidator<Lowercase, String> {

    @Override
    public boolean isValid(String field, ConstraintValidatorContext context) {
        if (field == null || field.isEmpty()) {
            return true;
        }
        return field.equals(field.toLowerCase());
    }
}