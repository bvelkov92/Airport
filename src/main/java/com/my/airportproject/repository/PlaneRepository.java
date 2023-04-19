package com.my.airportproject.repository;

import com.my.airportproject.model.entity.Plane;
import com.my.airportproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Long> {

    Optional<Plane> findByPlaneNumber(String planeNumber);

    List<Plane> findAllByPlaneOwnerFirm(User owner);


}
