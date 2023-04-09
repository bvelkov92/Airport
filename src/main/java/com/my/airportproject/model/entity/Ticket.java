package com.my.airportproject.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Table(name = "tickets")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ticket extends BaseEntity {

    @OneToOne
    private User user;
    @OneToOne
    private Flight flight;

}
