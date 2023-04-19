package com.my.airportproject.views;

import com.my.airportproject.model.enums.EnumRoles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewUsers {


  private  Long id;

  private  String username;

   private String email;

  private  EnumRoles role;


    public ViewUsers(Long id, String username, String email, EnumRoles role) {
        this.id=id;
        this.username=username;
        this.email = email;
        this.role= role;
    }


}
