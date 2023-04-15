package com.my.airportproject.validation.ChangeRole.classes;

import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.service.AuthService;
import com.my.airportproject.validation.ChangeRole.anotation.CRRoleValidator;

import javax.persistence.EnumType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CRRoleValidation implements ConstraintValidator<CRRoleValidator, ChangeRoleDto> {
    private final AuthService authService;

    public CRRoleValidation(AuthService authService) {
        this.authService = authService;
    }


    @Override
    public boolean isValid(ChangeRoleDto value, ConstraintValidatorContext context) {
        return this.authService.findByRole(value);
    }
}
