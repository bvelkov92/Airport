package com.my.airportproject.validation.ChangeRole.classes;

import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.service.AuthService;
import com.my.airportproject.service.RoleService;
import com.my.airportproject.validation.ChangeRole.anotation.CRRoleValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CRRoleValidation implements ConstraintValidator<CRRoleValidator, ChangeRoleDto> {
    private final AuthService authService;
    private  final RoleService roleService;

    public CRRoleValidation(AuthService authService, RoleService roleService) {
        this.authService = authService;
        this.roleService = roleService;
    }


    @Override
    public boolean isValid(ChangeRoleDto value, ConstraintValidatorContext context) {
       return this.authService.changeRole(value);
    }
}
