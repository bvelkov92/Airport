package com.my.airportproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "flights")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight extends BaseEntity {

    @Column(nullable = false)
    private String flightFrom;
    @Column(nullable = false)
    private String flightTo;

    @Column
    private String timeOfFlight;

    @Column(nullable = false)
    private Double ticketPrice;

    @OneToMany
    private List<Plane> planeNumber = new ArrayList<>();
    @ManyToOne
    public User firmOwner;


    public Flight(String flightFrom, String flightTo,
                  Double price, String time) {
        this.flightFrom = flightFrom;
        this.flightTo = flightTo;
        this.ticketPrice = price;
        this.planeNumber = new ArrayList<>();
        this.timeOfFlight = time;
    }

}
