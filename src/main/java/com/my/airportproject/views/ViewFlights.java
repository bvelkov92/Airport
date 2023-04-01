package com.my.airportproject.views;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewFlights {


    Long id;
    String firmOwner;
    String from;
    String to;
    Double price;
    String planeNumber;

    String flightTime;

    Double tickedPrice;


    public ViewFlights(Long id, String firmOwner, String from,
                       String to, Double price, String planeNumber,
                       String flightTime, Double tickedPrice) {
        this.id=id;
        this.firmOwner = firmOwner;
        this.from = from;
        this.to = to;
        this.price = price;
        this.flightTime = flightTime;
        this.planeNumber =planeNumber;
        this.tickedPrice = tickedPrice;
    }

}
