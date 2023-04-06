package com.my.airportproject.validation.registration.classes;


import com.my.airportproject.service.AuthService;
import com.my.airportproject.validation.registration.anotations.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final AuthService userService;

    @Autowired
    public UniqueEmailValidator(AuthService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userService.findByEmail(value) == null;
    }
}
