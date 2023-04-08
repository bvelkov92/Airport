package com.my.airportproject.controller;

import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.service.FlightService;
import com.my.airportproject.service.TicketService;
import com.my.airportproject.views.ViewFlights;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Getter
public class FlightController {

    private final FlightService flightService;
    private final TicketService ticketService;

    @Autowired
    public FlightController(FlightService flightService, TicketService ticketService) {
        this.flightService = flightService;
        this.ticketService = ticketService;
    }


    // =============== FLIGHTS (ADD, GET LIST,DELETE)  ======================
    @GetMapping("/flights/flight-add")
    public String getAddFlight() {

        return "flight-add";
    }

    @PostMapping("/flights/flight-add")
    public String postAddFlight(@Valid AddFlightDto addFlightDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addFlightDto", addFlightDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addFlightDto");
            return "redirect:/flights/flight-add";
        }
        this.flightService.addFlight(addFlightDto);


        return "redirect:/";
    }

    @Transactional
    @GetMapping("/flights/{id}")
    public String addTicket(@PathVariable("id") Long id){
        this.ticketService.buyTicket(id);
        return "redirect:/tickets/ticket-list";
    }
    @Transactional
 @GetMapping("/flights/remove/{id}")
 public String removeFlight(@PathVariable ("id") Long id){
        this.flightService.removeFlight(id);

        return "redirect:/flights/flight-list";
 }


    //============== M O D E L  A T R I B U T E S ====================
    @ModelAttribute("addNewFlight")
    public AddFlightDto addFlight() {
        return new AddFlightDto();
    }
}