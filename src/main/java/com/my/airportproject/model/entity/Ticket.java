package com.my.airportproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Table(name = "tickets")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket extends BaseEntity {

    @OneToOne
    private User user;
    @OneToOne
    private Flight flight;

}
