package com.my.airportproject.service;


import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.Plane;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.repository.FlightRepository;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class FlightService {

    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;
    private final PlaneRepository planeRepository;
    private final UserRepository userRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository, ModelMapper modelMapper, PlaneRepository planeRepository, UserRepository userRepository) {
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
        this.planeRepository = planeRepository;
        this.userRepository = userRepository;
    }

    public void addFlight(AddFlightDto addFlightDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = this.userRepository.findByUsername(name).get();

        if (this.planeRepository.findByPlaneNumber(addFlightDto.getPlaneNumber()).isEmpty()) {
            Plane plane = new Plane(addFlightDto.getPlaneNumber(), user);
            this.planeRepository.save(plane);
        }


        boolean equalsFromTo = addFlightDto.getFlightFrom().equals(addFlightDto.getFlightTo());
        boolean foundFlight = this.flightRepository.findByFlightFromAndFlightTo(addFlightDto.getFlightFrom()
                , addFlightDto.getFlightTo()).isEmpty();


        if (!foundFlight) {
            if (equalsFromTo) {
                throw new RuntimeException("Values for Start-End points can't be equals");
            }
            Flight flight = this.flightRepository.findByFlightFromAndFlightTo
                    (addFlightDto.getFlightFrom(), addFlightDto.getFlightTo()).get();

            String planeNumber = addFlightDto.getPlaneNumber();
            Flight byPlaneNumber = this.flightRepository.findByPlaneNumber(planeNumber).get();
            byPlaneNumber.getPlaneNumber();
        }


        Plane plane = this.planeRepository.findByPlaneNumber(addFlightDto.getPlaneNumber()).get();
        Flight flight = new Flight(
                addFlightDto.getFlightFrom(),
                addFlightDto.getFlightTo(),
                addFlightDto.getPrice(),
                addFlightDto.getTime(),
                addFlightDto.getPlaneNumber()
        );
        flight.setFirmOwner(user);
        this.flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return this.flightRepository.findAllBy();
    }
}
