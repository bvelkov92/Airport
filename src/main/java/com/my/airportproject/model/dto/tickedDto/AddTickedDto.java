package com.my.airportproject.model.dto.tickedDto;

import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class AddTickedDto {

    @NotNull
    @Positive
    private Double price;

    @NotNull
    private Flight flightId;

    @NotNull User user;

    public AddTickedDto(Flight flight, Double ticketPrice, User user) {
        this.flightId=flight;
        this.price = ticketPrice;
        this.user = user;
    }
}
