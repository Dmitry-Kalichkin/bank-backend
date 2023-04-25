package com.bank.loans.config;


import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.bank.commons"})
public class ExternalModulesComponentsConfiguration {
    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
