package com.my.airportproject.repository;

import com.my.airportproject.model.entity.Role;
import com.my.airportproject.model.enums.EnumRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Override
    Optional<Role> findById(Long aLong);


  Optional <Role> findRoleByName(EnumRoles name);


}
