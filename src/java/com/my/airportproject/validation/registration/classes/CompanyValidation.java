package com.my.airportproject.validation.registration.classes;

import com.my.airportproject.service.AuthService;
import com.my.airportproject.validation.registration.anotations.CompanyValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompanyValidation implements ConstraintValidator<CompanyValidator, String> {

    private final AuthService authService;

    public CompanyValidation(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.authService.checkCompany(value);
    }
}



