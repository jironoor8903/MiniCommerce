package com.example.demo.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration

public class UserConfig {
    @Bean(name = "userCommandLineRunner")
    CommandLineRunner userCommandLineRunner(UserRepository userRepository) {
        return args -> {
            User avrum = new User(
                    "avrum",
                    "$5a$10$lT1pZL34VabcdEWA4pcgCOLmlIjdDPAMxl5eCilCyhuNhzEuhQGYC",
                    "avrum@gmail.com",
                    5000.00,
                    10000.00
            );
            User santos = new User(
                    "santos",
                    "$2a$10$lT1pZL33VvwqkEWA4pcgCOLmlIjdDPAMxl5eCilCyhuNhzEuhQGYC",
                    "santos@yahoo.com",
                    6000.00,
                    80000.25
            );
            userRepository.saveAll(
                    List.of(avrum, santos)
            );
        };
    }

}
