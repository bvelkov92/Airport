package com.my.airportproject.views;


import com.my.airportproject.model.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewUsers {

    Long id;
    String username;

    String email;

    String role;


    public ViewUsers(Long id, String username, String email, Role role) {
        this.id=id;
        this.username=username;
        this.email = email;
        this.role=role.getRoles().name();
    }

}
