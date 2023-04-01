package com.my.airportproject.service;

import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.model.dto.tickedDto.AddTickedDto;
import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.Ticket;
import com.my.airportproject.repository.FlightRepository;
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

    @Autowired
    public TicketService(TicketRepository ticketRepository, ModelMapper modelMapper, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.flightRepository = flightRepository;
    }


    public void addTicket(AddFlightDto flight) {

        Flight findedFlight = this.flightRepository.findByFlightFromAndFlightToAndTimeOfFlight
                (flight.getFlightFrom(), flight.getFlightTo(), flight.getTime()).get();


        Optional<Ticket> ticketForSave = ticketRepository
                .findFirstByFlight_Id(findedFlight.getId());

        AddTickedDto tickedDto =
                new AddTickedDto(findedFlight, findedFlight.getTicketPrice(), findedFlight.getFirmOwner());

        if (ticketForSave.isEmpty()) {
            Ticket ticket = this.modelMapper.map(tickedDto, Ticket.class);


            this.ticketRepository.save(ticket);
        }
    }
}
