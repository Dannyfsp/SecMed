package com.secsystem.emr.filters;

import com.secsystem.emr.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class    FiltersConfig {

    private final LoggingFilter loggingFilter;
    private final UserService userService;


    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilterBean() {

        final FilterRegistrationBean<LoggingFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(loggingFilter);
        filterBean.addUrlPatterns("/*");
        // Lower values have higher priority
        filterBean.setOrder(Integer.MAX_VALUE-2);

        return filterBean;
    }


    @Bean
    public FilterRegistrationBean<UserVerificationFilter> userVerificationFilter() {
        FilterRegistrationBean<UserVerificationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new UserVerificationFilter(userService));
        registrationBean.addUrlPatterns("/api/protected/*");

        return registrationBean;
    }

}
