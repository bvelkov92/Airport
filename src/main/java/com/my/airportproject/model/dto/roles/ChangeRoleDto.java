package com.my.airportproject.model.dto.roles;

import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.validation.ChangeRole.anotation.CRRoleValidator;
import com.my.airportproject.validation.ChangeRole.anotation.CRUserValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CRRoleValidator
public class ChangeRoleDto {

    @NotNull
    @CRUserValidator
    String username;

    @NotNull
    EnumRoles role;
}
