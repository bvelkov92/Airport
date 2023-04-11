package com.my.airportproject.validation.addFlight.classes;

import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.validation.addFlight.anotations.IsFromToEqualsValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsFromToEqualsValidation implements ConstraintValidator<IsFromToEqualsValidator, AddFlightDto> {

    @Override
    public boolean isValid(AddFlightDto value, ConstraintValidatorContext context) {
        return !value.getFlightFrom().equals(value.getFlightTo());
    }
}
