package com.my.airportproject.validation.addFlight.classes;

import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.service.FlightService;
import com.my.airportproject.validation.addFlight.anotations.FlightValidator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class FlightValidation implements ConstraintValidator<FlightValidator, AddFlightDto> {

    private final FlightService flightService;

    @Autowired
    public FlightValidation(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public boolean isValid(AddFlightDto flight, ConstraintValidatorContext context) {
        return this.flightService.findFlight(flight)==null;
    }
}
