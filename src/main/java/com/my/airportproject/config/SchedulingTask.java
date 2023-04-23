package com.my.airportproject.config;

import com.my.airportproject.service.AuthService;
import com.my.airportproject.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class SchedulingTask {

    private final FlightService flightService;
    private final AuthService authService;

    @Autowired
    public SchedulingTask(FlightService flightService, AuthService authService) {
        this.flightService = flightService;
        this.authService = authService;
    }

    @Scheduled(cron = "20 * * * * *")
        public void startCompetition() {
            flightService.start();
    }

}
