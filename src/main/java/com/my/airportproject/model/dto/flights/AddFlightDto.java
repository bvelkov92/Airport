package com.my.airportproject.model.dto.flights;

import com.my.airportproject.validation.addFlight.anotations.FlightValidator;
import com.my.airportproject.validation.addFlight.anotations.IsFromToEqualsValidator;
import com.my.airportproject.validation.addFlight.anotations.PlaneValidatorFlights;
import com.my.airportproject.validation.addFlight.anotations.TimeValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@FlightValidator
@IsFromToEqualsValidator(flightFrom="flightFrom", flightTo="flightTo")
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

    @TimeValidator
    private String time;

    public void setTime(String time) {
        if (time.isBlank() || time.isEmpty()){
            setTime("1969-02-01T00:01");
        }else {
            this.time = time;
        }
    }
}
