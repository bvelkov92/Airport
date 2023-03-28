package com.my.airportproject.repository;

import com.my.airportproject.model.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Long> {


    Optional<Plane> findByPlaneNumberAndPlaneOwnerFirm(String planeNumber, String owner);

    Optional<Plane> findByPlaneNumber(String planeNumber);

    Optional<Plane> findByPlaneOwnerFirm(String owner);
}
