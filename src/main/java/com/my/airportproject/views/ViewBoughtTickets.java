package com.my.airportproject.views;

import com.my.airportproject.model.entity.Plane;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class ViewBoughtTickets {
    private Long id;
    private String firmOwner;
    private String from;
    private String to;
    private Double price;
    private String planeNumber;
    private LocalDateTime flightTime;

    private String firm;

    public ViewBoughtTickets(Long id, String flightFrom, String flightTo, Double ticketPrice, LocalDateTime timeOfFlight, Plane planeNumber, String companyName) {
        this.id = id;
        this.firmOwner = companyName;
        this.from = flightFrom;
        this.to = flightTo;
        this.price = ticketPrice;
        this.planeNumber = planeNumber.getPlaneNumber();
        this.flightTime = timeOfFlight;
    }

}
