package com.my.airportproject.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    @Size(min = 3, max = 10)
    private String username;

    @Size(min = 5)
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;

    @NotNull
    @Email
    private String email;

}
