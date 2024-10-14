package com.secsystem.emr.filters;

import com.secsystem.emr.exceptions.UnAuthorizedException;
import com.secsystem.emr.user.User;
import com.secsystem.emr.user.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserVerificationAspect {

    private final UserService userService;

    public UserVerificationAspect(UserService userService) {
        this.userService = userService;
    }

    @Before("@annotation(com.secsystem.emr.filters.VerifiedUserOnly)")
    public void checkIfUserVerified() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userService.findByEmail(email);

            if (user == null || !user.getIsVerified()) {
                throw new UnAuthorizedException("User is not verified");
            }
        }
    }
}