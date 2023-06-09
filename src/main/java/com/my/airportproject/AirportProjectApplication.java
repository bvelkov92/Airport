package com.my.airportproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AirportProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirportProjectApplication.class, args);
    }

}
