package com.bank.loans;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.bank.accounts.api")
@OpenAPIDefinition(info = @Info(title = "Loans Service",
        description = "This service provides endpoints related to loans in bank system",
        version = "2.0"))
public class LoansApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class);
    }
}