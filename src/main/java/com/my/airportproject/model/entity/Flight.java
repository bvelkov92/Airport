package com.my.airportproject.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "flights")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Flight extends BaseEntity {

    @Column(nullable = false)
    private String flightFrom;
    @Column(nullable = false)
    private String flightTo;

    @Column
    private String timeOfFlight;

    @Column(nullable = false)
    private Double ticketPrice;

    @OneToOne
    private Plane planeNumber;
    @ManyToOne
    public User firmOwner;


    public Flight(String flightFrom, String flightTo, Double price, String time, String planeNumber) {
        this.flightFrom = flightFrom;
        this.flightTo = flightTo;
        this.ticketPrice = price;
        this.timeOfFlight = time;
    }

}
