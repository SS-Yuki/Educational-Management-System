package com.example.lab3_behind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Lab3BehindApplication {

    public static void main(String[] args) {

        SpringApplication.run(Lab3BehindApplication.class, args);
    }

}
