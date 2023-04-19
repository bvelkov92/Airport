package com.my.airportproject.validation.addFlight.classes;

import com.my.airportproject.service.PlaneService;

import com.my.airportproject.validation.addFlight.anotations.PlaneValidatorFlights;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PlaneValidationFlights implements ConstraintValidator<PlaneValidatorFlights, String> {
    private final PlaneService planeService;

    @Autowired
    public PlaneValidationFlights(PlaneService planeService) {
        this.planeService = planeService;
    }

    @Override
    public boolean isValid(String planeNumber, ConstraintValidatorContext context) {
        return this.planeService.isFoundedPlane(planeNumber);
    }
}
