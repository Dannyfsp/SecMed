package com.secsystem.emr.utils.constants.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.secsystem.emr.user.UserRepository;

public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, String> {

    private final UserRepository userRepository;

    public UniquePhoneValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniquePhone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return true; // consider null/empty valid or handle as per your requirement
        }
        return !userRepository.existsByPhoneNumber(phoneNumber); // Implement this method in your repository
    }
}
