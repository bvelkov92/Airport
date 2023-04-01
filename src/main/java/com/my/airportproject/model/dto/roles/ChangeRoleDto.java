package com.my.airportproject.model.dto.roles;
import com.my.airportproject.model.enums.EnumRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRoleDto {
    String username;
    EnumRoles role;
}
