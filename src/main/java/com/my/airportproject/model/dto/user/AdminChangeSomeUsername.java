package com.my.airportproject.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminChangeSomeUsername {

        private String email;
        @NotNull
        private  String newUsername;

    }
