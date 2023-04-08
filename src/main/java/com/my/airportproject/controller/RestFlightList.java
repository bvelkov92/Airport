package com.my.airportproject.controller;

import com.my.airportproject.service.FlightService;
import com.my.airportproject.views.ViewFlights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class RestFlightList {

    private final FlightService flightService;

    @Autowired
    public RestFlightList(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights/flight-list")
    public ResponseEntity<List<ViewFlights>> getAllFlight(Model model) {
        var flight = this.flightService.getAllFlights();


        List<ViewFlights> flightsList = flight.stream()
                .map(f ->
                        new ViewFlights(f.getId(), f.getFirmOwner().getCompanyName(), f.getFlightFrom(), f.getFlightTo(), f.getTicketPrice(),
                                f.getPlaneNumber().getPlaneNumber(),
                                f.getTimeOfFlight().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")),
                                f.getTicketPrice())
                ).toList();
        model.addAttribute("flightsList", flightsList);


        return ResponseEntity.ok(flightsList);
    }
}
