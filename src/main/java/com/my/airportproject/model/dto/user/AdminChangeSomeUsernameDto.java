package com.my.airportproject.model.dto.user;

import com.my.airportproject.validation.changeUsername.anotation.ChangeUsernameValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminChangeSomeUsernameDto {
        @NotNull
        private String username;
        @NotNull
        @ChangeUsernameValidator
        private  String newUsername;
    }
