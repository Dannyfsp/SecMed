package com.secsystem.emr.utils.constants.validators;

import com.secsystem.emr.user.User;
import com.secsystem.emr.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, User> {

    private final UserRepository repository;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user == null) return true;
        return repository.findByPhoneNumber(user.getPhoneNumber()).isEmpty();
    }
}