package com.my.airportproject.repository;

import com.my.airportproject.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightFromAndFlightToAndTimeOfFlight(String from, String to, @NotNull String time);



}
