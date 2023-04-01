package com.my.airportproject.model.dto.roles;

import com.my.airportproject.model.enums.EnumRoles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleDto {

    String username;
    EnumRoles role;
}
