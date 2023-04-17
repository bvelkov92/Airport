package com.my.airportproject.model.dto.user;

import com.my.airportproject.validation.ChangeRole.anotation.CRUserValidator;
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
        private  String newUsername;
    }
