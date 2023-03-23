package com.my.airportproject.model.dto.flights;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
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
