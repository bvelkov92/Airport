package com.my.airportproject.model.dto.tickedDto;

import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.Plane;
import com.my.airportproject.model.entity.User;
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
public class AddTickedDto {


    private Flight flight;
    private User user;


    public AddTickedDto(Flight flight, User user) {
        this.flight = flight;
        this.user = user;
    }
}
