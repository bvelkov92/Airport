package com.my.airportproject.service;

import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.repository.FlightRepository;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.TicketRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Getter
@Setter
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;
    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, ModelMapper modelMapper, FlightRepository flightRepository, PlaneRepository planeRepository) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.flightRepository = flightRepository;
        this.planeRepository = planeRepository;
    }


    public void buyTicket(Long id) {

        Optional<Flight> byId = this.flightRepository.findById(id);


    }
}
