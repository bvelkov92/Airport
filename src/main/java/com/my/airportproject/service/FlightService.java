package com.my.airportproject.service;


import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.Plane;
import com.my.airportproject.repository.FlightRepository;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Getter
@Setter
@Transactional
public class FlightService {

    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;
    private final PlaneRepository planeRepository;
    private final UserRepository userRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Autowired
    public FlightService(FlightRepository flightRepository, ModelMapper modelMapper, PlaneRepository planeRepository, UserRepository userRepository) {
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
        this.planeRepository = planeRepository;
        this.userRepository = userRepository;
    }

    public void addFlight(AddFlightDto addFlightDto) {

        boolean equalsFromTo = addFlightDto.getFlightFrom().equals(addFlightDto.getFlightTo());
        boolean foundFlight = this.flightRepository.findByFlightFromAndFlightTo(addFlightDto.getFlightFrom()
                , addFlightDto.getFlightTo()).isPresent();
        Optional<Flight> byFlightFromAndFlightTo = this.flightRepository.findByFlightFromAndFlightTo(addFlightDto.getFlightFrom()
                , addFlightDto.getFlightTo());

        if (equalsFromTo) {
            throw new RuntimeException("Values for Start-End points can't be equals");
        }
        if (byFlightFromAndFlightTo.isPresent()) {
            Set<Plane> planeNumber = byFlightFromAndFlightTo.get().getPlaneNumber();
            throw new RuntimeException("Flight exist");
        }
        Plane plane = this.planeRepository.findByPlaneNumber(addFlightDto.getPlaneNumber()).get();

        if (plane.getPlaneNumber().isEmpty()) {
            throw new RuntimeException("Plane is not exist!");
        }

        Flight flight = new Flight(
                addFlightDto.getFlightFrom(),
                addFlightDto.getFlightTo(),
                addFlightDto.getPrice(),
                addFlightDto.getTime()
        );
        flight.setFirmOwner(plane.getPlaneOwnerFirm());
        flight.getPlaneNumber().add(plane);
        Flight flightToSave = this.modelMapper.map(flight, Flight.class);

        this.flightRepository.save(flightToSave);
    }
}
