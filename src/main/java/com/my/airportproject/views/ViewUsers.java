package com.my.airportproject.views;

import com.my.airportproject.model.enums.EnumRoles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ViewUsers {

    @NotNull
    Long id;
    @NotNull
    String username;

    @NotNull
    String email;

    EnumRoles role;


    public ViewUsers(Long id, String username, String email, EnumRoles role) {
        this.id=id;
        this.username=username;
        this.email = email;
        this.role= role;
    }


}
