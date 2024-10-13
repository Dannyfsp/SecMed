package com.secsystem.emr.utils.constants.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class IpAddressValidator implements ConstraintValidator<IpAddress, String> {
    private static final String IP_REGEX = "^(\\d{1,3}\\.){3}\\d{1,3}$"; // For IPv4 (you can also add IPv6 support)

    @Override
    public boolean isValid(String ipAddress, ConstraintValidatorContext context) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            return true;
        }
        return Pattern.matches(IP_REGEX, ipAddress);
    }
}