package com.my.airportproject.model.dto.user;

import com.my.airportproject.validation.registration.anotations.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUsernameDto {

   private String username;
   @NotNull
  private  String newUsername;

}
