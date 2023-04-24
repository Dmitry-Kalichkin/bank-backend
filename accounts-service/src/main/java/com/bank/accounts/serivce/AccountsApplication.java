package com.bank.accounts.serivce;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "Accounts Service",
        description = "This service provides endpoints related to accounts in bank system",
        version = "2.0"))
public class AccountsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class);
    }
}