package com.my.airportproject.model.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "planes")
@NoArgsConstructor
public class Plane extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String planeNumber;

    @ManyToOne
    private User planeOwnerFirm;


    public Plane(String planeNumber) {
        this.planeNumber = planeNumber;
    }

    public Plane(String planeNumber, User username) {
        this.planeNumber = planeNumber;
        this.planeOwnerFirm = username;
    }
}
