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

    @NotNull
    @Size(min = 1)
    private String companyName;

    @Size(min = 3, max = 10)
    @NotNull
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
