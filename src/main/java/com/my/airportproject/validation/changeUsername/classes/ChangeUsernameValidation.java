package com.my.airportproject.validation.changeUsername.classes;

import com.my.airportproject.service.AuthService;
import com.my.airportproject.validation.changeUsername.anotation.ChangeUsernameValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChangeUsernameValidation implements ConstraintValidator <ChangeUsernameValidator, String>{

    private final AuthService authService;

    public ChangeUsernameValidation(AuthService authService) {
        this.authService = authService;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.authService.getUserByUsernameForChangeUsername(value);
    }
}
