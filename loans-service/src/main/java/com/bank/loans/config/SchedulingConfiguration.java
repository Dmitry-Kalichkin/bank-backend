package com.bank.loans.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Slf4j
@Configuration
@EnableScheduling
public class SchedulingConfiguration {
    @Bean
    public TaskScheduler taskScheduler(@Value("${loans.scheduling.pool.size}") Integer poolSize,
                                       @Value("${loans.scheduling.pool.prefix}") String prefix) {
        log.info("Creating TaskScheduler with pool size - {} and prefix - {}", poolSize, prefix);

        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

        taskScheduler.setPoolSize(poolSize);
        taskScheduler.setThreadNamePrefix(prefix);

        log.info("TaskScheduler created");
        return taskScheduler;
    }
}
