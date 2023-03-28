package com.my.airportproject.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Table(name = "tickets")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ticket extends BaseEntity {

    @ManyToMany
    private Set<User> user = new HashSet<>();

    @ManyToOne
    private Flight flight;


}
