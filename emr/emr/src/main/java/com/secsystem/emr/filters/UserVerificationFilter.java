package com.secsystem.emr.filters;

import com.secsystem.emr.user.User;
import com.secsystem.emr.user.UserController;
import com.secsystem.emr.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UserVerificationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserVerificationFilter.class);
    private final UserService userService;

    public UserVerificationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();

            User user = userService.findByEmail(email);

            if (user != null && !user.getIsVerified()) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("User not verified");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}