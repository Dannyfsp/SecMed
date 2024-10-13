package com.secsystem.emr.utils.constants.validators;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class JsonFieldValidator implements ConstraintValidator<JsonField, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean isValid(String field, ConstraintValidatorContext context) {
        if (field == null || field.isEmpty()) {
            return true;
        }
        try {
            objectMapper.readTree(field);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}