package com.my.airportproject.model.dto.flights;


import com.my.airportproject.validators.ValidateFlightTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String planeNumber;
    @NotNull
    private String time;

}
