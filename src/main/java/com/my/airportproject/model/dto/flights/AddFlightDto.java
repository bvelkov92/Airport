package com.my.airportproject.model.dto.flights;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddFlightDto {

    @NotNull
    private String flightFrom;
    @NotNull
    private String flightTo;
    @NotNull
    private Double price;
    @NotNull
    private String planeNumber;
    @NotNull
    private String time;


}
