package com.my.airportproject.service;


import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.Plane;
import com.my.airportproject.model.entity.Ticket;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.repository.FlightRepository;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.TicketRepository;
import com.my.airportproject.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
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
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AuthService authService;

    private final TicketRepository ticketRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Autowired
    public FlightService(FlightRepository flightRepository, ModelMapper modelMapper, PlaneRepository planeRepository, UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher, AuthService authService, TicketRepository ticketRepository) {
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
        this.planeRepository = planeRepository;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.authService = authService;
        this.ticketRepository = ticketRepository;
    }


    public void addFlight(AddFlightDto addFlightDto) {

        Optional<Plane> plane = this.planeRepository.findByPlaneNumber(addFlightDto.getPlaneNumber());
        LocalDateTime dateTime = LocalDateTime.parse(addFlightDto.getTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

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

    public Flight findFlight(AddFlightDto flight) {
        LocalDateTime dateTime = LocalDateTime.parse(flight.getTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return this.flightRepository
                .findByFlightFromAndFlightToAndTimeOfFlightAndPlaneNumber_PlaneNumber(
                        flight.getFlightFrom(),
                        flight.getFlightTo(),
                        dateTime,
                        flight.getPlaneNumber()
                ).orElse(null);

    }

    public boolean checkTimeDifference(String value) {
        LocalDateTime dateTime = LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime validDate = LocalDateTime.now().plusMinutes(58);
        return dateTime.isAfter(validDate);
    }


    public List<Ticket> getMyTickets() {
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticator.getName();
        User companyOrUser = this.userRepository.findByUsername(username).get();
        boolean isAdmin = this.userRepository.findByUsername(username).get().getRoles().get(0).getName().equals(EnumRoles.ADMIN);
       boolean isFirm = this.userRepository.findByUsername(username).get().getRoles().get(0).getName().equals(EnumRoles.FIRM);

       if (isAdmin){
            return this.ticketRepository.findAll();
        } else if (isFirm) {
            return this.ticketRepository.findAllByFlight_FirmOwner(companyOrUser.getCompanyName());
        }

        return this.ticketRepository.findAllByUser(companyOrUser);
    }

    public void removeFlight(Long id) {
        List<Ticket> ticketsForFlight = this.ticketRepository.findAllByFlight_Id(id);
        this.ticketRepository.deleteAll(ticketsForFlight);
        this.flightRepository.deleteById(id);
    }

    @Transactional
    public void start() {
        List<Flight> flightList = this.flightRepository.findAll();
        LocalDateTime timeNow = LocalDateTime.now();
        for (Flight currentFligh : flightList) {
            LocalDateTime timeOfFlight = currentFligh.getTimeOfFlight();
            if (timeNow.isEqual(timeOfFlight) || timeNow.isAfter(timeOfFlight)) {
                List<Ticket> byFlightId = this.ticketRepository.findAllByFlight_Id(currentFligh.getId());
                if (byFlightId.size() > 0) {
                    this.ticketRepository.deleteAll(byFlightId);
                }
                this.flightRepository.delete(currentFligh);
            }
        }
    }
}
