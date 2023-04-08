package com.my.airportproject.config;

import com.my.airportproject.service.FlightService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingTask {

    private final FlightService flightService;

    public SchedulingTask(FlightService flightService) {
        this.flightService = flightService;
    }


    @Scheduled(cron = "20 * * * * *")
        public void startCompetition() {
            flightService.start();
    }
}
