package com.my.airportproject.views;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ViewFlights {

   private Long id;
   private String firmOwner;
   private String from;
   private String to;
   private Double price;
   private String planeNumber;
   private String flightTime;
   private Double tickedPrice;


    public ViewFlights(Long id, String firmOwner, String from,
                       String to, Double price, String planeNumber,
                       String flightTime , Double tickedPrice) {
        this.id=id;
        this.firmOwner = firmOwner;
        this.from = from;
        this.to = to;
        this.price = price;
        this.flightTime = flightTime;
        this.planeNumber =planeNumber;
        this.tickedPrice = tickedPrice;
    }

    public void setFlightTime(String flightTime) {
        LocalDateTime dateTime = LocalDateTime.parse(flightTime);
        String etest = "";
        this.flightTime = flightTime;
    }
}
