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

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();

            // Ensure the email is not null before proceeding
            if (email != null) {
                User user = userService.findByEmail(email);

                // Check if the user exists and if they are verified
                if (user == null || user.getIsVerified() == null || !user.getIsVerified()) {
                    throw new UnAuthorizedException("User is not verified");
                }
            } else {
                throw new UnAuthorizedException("Authentication name (email) is null");
            }
        } else {
            throw new UnAuthorizedException("User is not authenticated");
        }
    }
}
