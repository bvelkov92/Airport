package com.my.airportproject.model.dto.user;

import com.my.airportproject.validation.registration.anotations.EqualsPasswords;
import com.my.airportproject.validation.registration.anotations.UniqueEmail;
import com.my.airportproject.validation.registration.anotations.UniqueUsername;
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
@EqualsPasswords
public class UserRegisterDto {

    @NotNull
    @Size(min = 1)
    private String companyName;

    @Size(min = 3, max = 10)
    @NotNull
    @UniqueUsername
    private String username;

    @Size(min = 5)
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;

    @NotNull
    @Email
    @UniqueEmail
    private String email;
}
