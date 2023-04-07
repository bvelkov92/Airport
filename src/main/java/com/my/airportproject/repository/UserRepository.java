package com.my.airportproject.repository;


import com.my.airportproject.model.entity.User;
import com.my.airportproject.model.enums.EnumRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndRoles(String username, EnumRoles role);

}
