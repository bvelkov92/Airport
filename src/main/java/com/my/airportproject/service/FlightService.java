package com.my.airportproject.service;


import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.Plane;
import com.my.airportproject.repository.FlightRepository;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.TicketRepository;
import com.my.airportproject.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@Transactional
public class FlightService {

    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;
    private final PlaneRepository planeRepository;
    private final UserRepository userRepository;

    private final TicketRepository ticketRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Autowired
    public FlightService(FlightRepository flightRepository, ModelMapper modelMapper, PlaneRepository planeRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
        this.planeRepository = planeRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    public void addFlight(AddFlightDto addFlightDto) {

        boolean equalsFromTo = addFlightDto.getFlightFrom().equals(addFlightDto.getFlightTo());

        LocalDateTime dateTime = LocalDateTime.parse(addFlightDto.getTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime validDate = LocalDateTime.now().plusHours(1).plusMinutes(2);

        if (dateTime.isBefore(validDate)) {
            throw new RuntimeException("Invalid date! The flight must be at least 1 hour!");
        }

        Optional<Flight> isExistFlight = this.flightRepository.
                findByFlightFromAndFlightToAndTimeOfFlight(addFlightDto.getFlightFrom()
                        , addFlightDto.getFlightTo(), dateTime);

        if (equalsFromTo) {
            throw new RuntimeException("Values for Start-End points can't be equals");
        }
        if (isExistFlight.isPresent()) {
            throw new RuntimeException("Flight exist");
        }
        Optional<Plane> plane = this.planeRepository.findByPlaneNumber(addFlightDto.getPlaneNumber());

        if (plane.get().getPlaneNumber().isEmpty()) {
            throw new RuntimeException("Plane is not exist!");
        }



        Flight flight = new Flight(
                addFlightDto.getFlightFrom(),
                addFlightDto.getFlightTo(),
                addFlightDto.getPrice(),
                dateTime,
                plane.get()

        );
        flight.setFirmOwner(plane.get().getPlaneOwnerFirm());
        Flight flightToSave = this.modelMapper.map(flight, Flight.class);

        this.flightRepository.save(flightToSave);
    }

}
