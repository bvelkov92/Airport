package com.my.airportproject.controller;

import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.UserRepository;
import com.my.airportproject.service.FlightService;
import com.my.airportproject.views.ViewFlights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final UserRepository userRepository;
    private final PlaneRepository planeRepository;

    @Autowired
    public FlightController(FlightService flightService, UserRepository userRepository, PlaneRepository planeRepository) {
        this.flightService = flightService;
        this.userRepository = userRepository;
        this.planeRepository = planeRepository;
    }

    @GetMapping("/flight-add")
    public String getAddFlight() {
        return "flight-add";
    }

    @PostMapping("flight-add")
    public String postAddFlight(@Valid AddFlightDto addFlightDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addFlightDto", addFlightDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addFlightDto");
            return "redirect:/flights/flight-add";
        }


        this.flightService.addFlight(addFlightDto);

        return "flight-list";
    }

    @GetMapping("/flight-list")
    public String getAllFlight(Model model) {
        var flights = this.flightService.getAllFlights()
                .stream()
                .map(f ->
                        new ViewFlights(f.getFirmOwner().getUsername(), f.getFlightFrom(), f.getFlightTo(), f.getTicketPrice(),
                                f.getPlaneNumber().getPlaneNumber(), f.getTimeOfFlight())
                ).collect(Collectors.toList());
        model.addAttribute("flights", flights);
        return "flight-list";
    }


    @ModelAttribute("addNewFlight")
    public AddFlightDto addFlight() {
        return new AddFlightDto();
    }
}
