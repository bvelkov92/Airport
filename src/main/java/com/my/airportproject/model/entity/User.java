package com.my.airportproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @Size(min = 6)
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    private String email;



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Role> roles = new ArrayList<>();


    public User(String username, String password, String email, String companyName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.companyName = companyName;
    }

    public User(String companyName) {
        this.companyName = companyName;
    }


}
