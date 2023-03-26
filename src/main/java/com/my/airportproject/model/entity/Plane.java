package com.my.airportproject.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "planes")
@NoArgsConstructor
@Getter
@Setter
public class Plane extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String planeNumber;

    @ManyToOne
    private User planeOwnerFirm;

    @OneToMany
    private List<Flight> flights;


    public Plane(String planeNumber) {
        this.planeNumber = planeNumber;
    }

    public Plane(String planeNumber, User username) {
        this.planeNumber = planeNumber;
        this.planeOwnerFirm = username;
    }
}
