package com.my.airportproject.validation.registration.classes;

import com.my.airportproject.model.entity.User;
import com.my.airportproject.service.AuthService;
import com.my.airportproject.validation.registration.anotations.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final AuthService userService;

    @Autowired
    public UniqueUsernameValidator(AuthService userService) {
        this.userService = userService;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userService.findByUsername(value)==null;
    }
}
