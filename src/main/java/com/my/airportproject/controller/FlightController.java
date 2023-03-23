package com.my.airportproject.controller;

import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.UserRepository;
import com.my.airportproject.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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

        return "redirect:/home";
    }


    @ModelAttribute("addNewFlight")
    public AddFlightDto addFlight() {
        return new AddFlightDto();
    }
}
