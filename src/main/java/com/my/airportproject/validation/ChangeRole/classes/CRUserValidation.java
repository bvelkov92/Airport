package com.my.airportproject.validation.ChangeRole.classes;

import com.my.airportproject.service.AuthService;
import com.my.airportproject.validation.ChangeRole.anotation.CRUserValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CRUserValidation implements ConstraintValidator<CRUserValidator, String> {

    private final AuthService authService;

    public CRUserValidation(AuthService authService) {
        this.authService = authService;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.authService.findByUsername(value)!=null;
    }
}
