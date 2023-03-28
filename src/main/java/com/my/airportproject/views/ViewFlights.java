package com.my.airportproject.views;

import com.my.airportproject.model.entity.Plane;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ViewFlights {
    String firmOwner;
    String from;
    String to;

    Double price;
    String planeNumber;

    String flightTime;


    public ViewFlights(String firmOwner, String from,
                       String to, Double price, String planeNumber, String flightTime) {
        this.firmOwner = firmOwner;
        this.from = from;
        this.to = to;
        this.price = price;
        this.flightTime = flightTime;
        this.planeNumber =planeNumber;
    }

    public ViewFlights(String username, String flightFrom,
                       String flightTo, Double ticketPrice, String timeOfFlight) {
    }
}
