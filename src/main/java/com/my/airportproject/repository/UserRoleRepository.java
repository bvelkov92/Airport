package com.my.airportproject.repository;


import com.my.airportproject.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, Long> {


    Optional<Role> findByName(String role);

}
