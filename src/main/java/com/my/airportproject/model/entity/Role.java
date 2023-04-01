package com.my.airportproject.model.entity;

import com.my.airportproject.model.enums.EnumRoles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "roles")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private EnumRoles name;

    public Role(EnumRoles role) {
        this.name = role;
    }
}
