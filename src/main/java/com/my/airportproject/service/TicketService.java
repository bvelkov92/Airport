package com.my.airportproject.service;

import com.my.airportproject.model.dto.tickedDto.AddTickedDto;
import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.Ticket;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.repository.FlightRepository;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.TicketRepository;
import com.my.airportproject.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private  final UserRepository userRepository;


    @Autowired
    public TicketService(TicketRepository ticketRepository,
                         ModelMapper modelMapper,
                         FlightRepository flightRepository,
                         PlaneRepository planeRepository,
                         UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.flightRepository = flightRepository;
        this.planeRepository = planeRepository;
        this.userRepository = userRepository;
    }
    public void buyTicket(Long id, String email) {
        User user = this.userRepository.findByEmail(email).orElse(null);
        Flight flight = this.flightRepository.findById(id).get();
        AddTickedDto newTicket = new AddTickedDto(
               flight,
                user
        );

        Ticket ticket = modelMapper.map(newTicket, Ticket.class);
        this.ticketRepository.save(ticket);
    }
    public boolean deleteRow(Long id) {
        Ticket ticket = this.ticketRepository.findById(id).orElse(null);
        if (ticket!=null) {
            this.ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
