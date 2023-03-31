package com.my.airportproject.views;

import com.my.airportproject.model.entity.Plane;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


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

    Double tickedPrice;


    public ViewFlights(String firmOwner, String from,
                       String to, Double price, String planeNumber, String flightTime, Double tickecPrice) {
        this.firmOwner = firmOwner;
        this.from = from;
        this.to = to;
        this.price = price;
        this.flightTime = flightTime;
        this.planeNumber =planeNumber;
        this.tickedPrice = tickecPrice;
    }

}
