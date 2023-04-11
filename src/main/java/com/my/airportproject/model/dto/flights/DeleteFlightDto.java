package com.my.airportproject.model.dto.flights;

import com.my.airportproject.model.entity.Plane;
import com.my.airportproject.validation.addFlight.anotations.PlaneValidatorFlights;
import com.my.airportproject.validation.addFlight.anotations.TimeValidator;
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
public class DeleteFlightDto {

    private Plane plane;

    private LocalDateTime time;


}
