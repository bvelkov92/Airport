package com.my.airportproject.service;

import com.my.airportproject.model.dto.tickedDto.AddTickedDto;
import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.Ticket;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.repository.FlightRepository;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.TicketRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Getter
@Setter
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;
    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;

    private final AuthService authService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, ModelMapper modelMapper, FlightRepository flightRepository, PlaneRepository planeRepository, AuthService authService) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.flightRepository = flightRepository;
        this.planeRepository = planeRepository;
        this.authService = authService;
    }


    public void buyTicket(Long id) {
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticator.getName();
        User user = this.authService.getUserByUsername(username);
        Flight flight = this.flightRepository.findById(id).get();
        AddTickedDto newTicket = new AddTickedDto(
               flight,
                user
        );

        Ticket ticket = modelMapper.map(newTicket, Ticket.class);
        this.ticketRepository.save(ticket);
    }

    public void deleteRow(Long id) {
        this.ticketRepository.deleteById(id);
    }
}
