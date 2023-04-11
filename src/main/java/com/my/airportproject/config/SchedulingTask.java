package com.my.airportproject.config;

import com.my.airportproject.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingTask {

    private final FlightService flightService;

    @Autowired
    public SchedulingTask(FlightService flightService) {
        this.flightService = flightService;
    }


    @Scheduled(cron = "20 * * * * *")
        public void startCompetition() {
            flightService.start();
    }
}
