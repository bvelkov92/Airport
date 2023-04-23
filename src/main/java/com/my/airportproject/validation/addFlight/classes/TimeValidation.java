package com.my.airportproject.validation.addFlight.classes;

import com.my.airportproject.service.FlightService;
import com.my.airportproject.validation.addFlight.anotations.TimeValidator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class TimeValidation implements  ConstraintValidator<TimeValidator, String> {

    private final FlightService flightService;

    @Autowired
    public TimeValidation(FlightService flightService) {
        this.flightService = flightService;

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            return this.flightService.checkTimeDifference(LocalDateTime.parse(value));
        }
        return false;
    }
}
