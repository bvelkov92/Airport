package com.my.airportproject.controller;

import com.my.airportproject.service.FlightService;
import com.my.airportproject.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class TicketController {



    private final TicketService ticketService;
    private final FlightService flightService;

    @Autowired
    public TicketController(TicketService ticketService, FlightService flightService) {
        this.ticketService = ticketService;
        this.flightService = flightService;
    }


    @GetMapping("/tickets/{id}")
    public ModelAndView buyTicket(@PathVariable("id") Long id) {
        this.ticketService.buyTicket(id);

        return null;
    }


}
