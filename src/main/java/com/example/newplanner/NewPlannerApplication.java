package com.example.newplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NewPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewPlannerApplication.class, args);
    }

}
