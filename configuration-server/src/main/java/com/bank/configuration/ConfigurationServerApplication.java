package com.bank.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigurationServerApplcation {
    public static void main(String[] args) {
        SpringApplication.run(ConfigurationServerApplcation.class, args);
    }
}