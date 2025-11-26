package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.example.entity")
public class MutantDetectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MutantDetectorApplication.class, args);
    }
}