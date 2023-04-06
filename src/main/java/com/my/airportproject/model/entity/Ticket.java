package com.my.airportproject.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Table(name = "tickets")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ticket extends BaseEntity {

    @ManyToOne
    private User user;
    @ManyToOne
    private Flight flight;

}
