package com.example.demo.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            Product AirJordan = new Product(
                    "Air Jordan 1",
                    "Shoe",
                    "Nike Basketball Shoe",
                    100.25,
                    50.75,
                    null,
                    null
            );

            Product WhiteShirt = new Product(
                    "White Shirt 1",
                    "Shirt",
                    "White Colored Shirt",
                    20.0,
                    5.5,
                    null,
                    null
            );
            productRepository.saveAll(
                    List.of(AirJordan, WhiteShirt)
            );
        };
    }

}