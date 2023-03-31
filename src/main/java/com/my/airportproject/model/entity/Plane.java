package com.my.airportproject.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "planes")
@NoArgsConstructor
@Getter
@Setter
public class Plane extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String planeNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User planeOwnerFirm;


    public Plane(String planeNumber) {
        this.planeNumber = planeNumber;
    }

    public Plane(String planeNumber, User username) {
        this.planeNumber = planeNumber;
        this.planeOwnerFirm = username;
    }
}
