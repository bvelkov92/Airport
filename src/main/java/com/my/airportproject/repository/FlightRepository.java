package com.my.airportproject.repository;

import com.my.airportproject.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightFromAndFlightTo(String from, String to);


    Optional<Flight> findByPlaneNumber(String planeNumber);

    List<Flight> findAllBy();
}
