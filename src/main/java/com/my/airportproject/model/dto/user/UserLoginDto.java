package com.my.airportproject.model.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserLoginDto {

    @Size(min = 3, max = 10)
    @NotNull
    @Email
    private String email;

    @Size(min = 5)
    @NotNull
    private String password;

}
