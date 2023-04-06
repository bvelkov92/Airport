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

    private Long id;
    private Double price;

    private Flight flight;

    private User user;

    public AddTickedDto(Long id, Flight flight, Double ticketPrice, User user) {
        this.id = id;
        this.flight =flight;
        this.price = ticketPrice;
        this.user = user;
    }
}
