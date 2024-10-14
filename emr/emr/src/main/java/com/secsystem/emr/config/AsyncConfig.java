package com.secsystem.emr.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);        // Minimum number of threads
        executor.setMaxPoolSize(20);        // Maximum number of threads
        executor.setQueueCapacity(500);     // Queue capacity for tasks before new threads are created
        executor.setThreadNamePrefix("AsyncExecutor-");  // Thread name prefix for easier identification
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            private final Logger logger = LoggerFactory.getLogger(this.getClass());

            @Override
            public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
                logger.error("Exception occurred in async method - " + method.getName(), throwable);
            }
        };
    }

}