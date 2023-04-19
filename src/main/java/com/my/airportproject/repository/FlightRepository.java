package com.my.airportproject.repository;

import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightFromAndFlightToAndTimeOfFlightAndPlaneNumber_PlaneNumber(String from, String to, LocalDateTime time, String planeNumber);


    Optional<Flight> findFirstByTimeOfFlightAndPlaneNumber(LocalDateTime time, Plane planeNumber);
}
