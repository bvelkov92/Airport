package com.my.airportproject.validation.addPlane.classes;

import com.my.airportproject.service.PlaneService;
import com.my.airportproject.validation.addPlane.anotations.PlaneValidatorAdd;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PlaneValidationAdd implements ConstraintValidator<PlaneValidatorAdd, String> {

    private final PlaneService planeService;

    @Autowired
    public PlaneValidationAdd(PlaneService planeService) {
        this.planeService = planeService;
    }

    @Override
    public boolean isValid(String planeNumber, ConstraintValidatorContext context) {
        return this.planeService.isNotFounded(planeNumber);
    }
}
