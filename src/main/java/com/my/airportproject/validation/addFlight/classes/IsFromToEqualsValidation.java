package com.my.airportproject.validation.addFlight.classes;

import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.validation.addFlight.anotations.IsFromToEqualsValidator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsFromToEqualsValidation implements ConstraintValidator<IsFromToEqualsValidator, Object> {

    private String flightFrom;
    private String flightTo;

    private String message;
    private Object value;
    private ConstraintValidatorContext context;

    @Override
    public void initialize(IsFromToEqualsValidator constraintAnnotation) {
        this.flightFrom = constraintAnnotation.flightFrom();
        this.flightTo = constraintAnnotation.flightTo();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        Object flightFrom = beanWrapper.getPropertyValue(this.flightFrom);
        Object flightTo = beanWrapper.getPropertyValue(this.flightTo);


        if (flightFrom != null && !flightFrom.equals(flightTo)) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(this.flightTo)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
