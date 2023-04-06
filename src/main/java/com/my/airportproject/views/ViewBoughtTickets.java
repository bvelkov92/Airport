package com.my.airportproject.views;

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


    public ViewBoughtTickets(String firmOwner, String from,
                             String to, String planeNumber,
                             LocalDateTime flightTime) {
        this.firmOwner = firmOwner;
        this.from = from;
        this.to = to;
        this.flightTime = flightTime;
        this.planeNumber =planeNumber;
    }

}
