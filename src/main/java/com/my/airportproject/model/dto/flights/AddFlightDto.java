package com.my.airportproject.model.dto.flights;

import com.my.airportproject.validation.addFlight.anotations.FlightValidator;
import com.my.airportproject.validation.addFlight.anotations.IsFromToEqualsValidator;
import com.my.airportproject.validation.addFlight.anotations.PlaneValidatorFlights;
import com.my.airportproject.validation.addFlight.anotations.TimeValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@FlightValidator
@IsFromToEqualsValidator

public class AddFlightDto {


    @NotNull
    @Size(min = 2)
    private String flightFrom;
    @NotNull
    @Size(min = 2)
    private String flightTo;
    @NotNull
    @Positive
    private Double price;
    @NotNull
    @Size(min = 1)
    @PlaneValidatorFlights
    private String planeNumber;
    @NotNull
    @TimeValidator
    private String time;


}
