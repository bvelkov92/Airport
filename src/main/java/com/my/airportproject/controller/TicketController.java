package com.my.airportproject.controller;

import com.my.airportproject.model.dto.tickedDto.AddTickedDto;
import com.my.airportproject.model.entity.Ticket;
import com.my.airportproject.service.FlightService;
import com.my.airportproject.service.TicketService;
import com.my.airportproject.views.ViewBoughtTickets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class TicketController {

    private final TicketService ticketService;
    private final FlightService flightService;

    @Autowired
    public TicketController(TicketService ticketService, FlightService flightService) {
        this.ticketService = ticketService;
        this.flightService = flightService;
    }


    @GetMapping("/tickets/ticket-list")
    public String viewTickets(Model model) {
        List<Ticket> myTickets = this.flightService.getMyTickets();

        List<ViewBoughtTickets> ticketsList = myTickets.stream()
                .map(ticket ->
                        new ViewBoughtTickets(
                                ticket.getId(),
                                ticket.getFlight().getFlightFrom(),
                                ticket.getFlight().getFlightTo(),
                                ticket.getFlight().getTicketPrice(),
                                ticket.getFlight().getTimeOfFlight(),
                                ticket.getFlight().getPlaneNumber(),
                                ticket.getFlight().getFirmOwner().getCompanyName())
                ).toList();
        model.addAttribute("tickets", ticketsList);

        return "ticket-list";
    }

    @Transactional
    @GetMapping("/tickets/removeticket/{id}")
    public String removeFlight(@PathVariable ("id") Long id){
        this.ticketService.deleteRow(id);
        return "redirect:/tickets/ticket-list";
    }

    @ModelAttribute("addTicketDto")
    public AddTickedDto initForm() {
        return new AddTickedDto();
    }

}
