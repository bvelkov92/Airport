package com.my.airportproject.validation.registration.classes;

import com.my.airportproject.model.dto.user.UserRegisterDto;
import com.my.airportproject.validation.registration.anotations.EqualsPasswords;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualsPasswordValidator
        implements ConstraintValidator<EqualsPasswords, UserRegisterDto> {

    @Override
    public boolean isValid(UserRegisterDto userRegisterDto, ConstraintValidatorContext context) {
        return userRegisterDto.getPassword() != null
                && userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword());
    }
}
