package com.secsystem.emr.filters;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class LoggingFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String clientIP = this.getClientIP(httpServletRequest);

        if ( this.shouldLogRequest(httpServletRequest) ) {
            log.info("Client IP: {}, Request URL: {}, Method: {}", clientIP, httpServletRequest.getRequestURL(), httpServletRequest.getMethod());
        }

        chain.doFilter(request, response);
        if ( this.shouldLogRequest(httpServletRequest) ) {
            log.info("Response status: {}", httpServletResponse.getStatus());
        }

    }

    private boolean shouldLogRequest(HttpServletRequest request) {

        // (?i) enables case-insensitive matching, \b matched as whole words
        // reference: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Regular_expressions
        return !request.getServletPath().matches("(?i).*\\b(actuator|swagger|api-docs|favicon|ui)\\b.*");
    }

    private String getClientIP(HttpServletRequest request) {

        String clientIP = request.getHeader("Client-IP");

        if (clientIP == null || clientIP.isEmpty() || "unknown".equalsIgnoreCase(clientIP)) {
            clientIP = request.getHeader("X-Forwarded-For");
        }

        if (clientIP == null || clientIP.isEmpty() || "unknown".equalsIgnoreCase(clientIP)) {
            clientIP = request.getHeader("X-Real-IP");
        }

        if (clientIP == null || clientIP.isEmpty() || "unknown".equalsIgnoreCase(clientIP)) {
            clientIP = request.getRemoteAddr();
        }

        return clientIP != null ? clientIP : "Unknown";
    }

}
