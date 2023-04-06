package com.my.airportproject.controller;

import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.service.FlightService;
import com.my.airportproject.views.ViewFlights;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@Getter
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
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


        return "redirect:/welcome";
    }

    @GetMapping("/flights/flight-list")
    public String getAllFlight(Model model) {
        var flight = this.flightService.getAllFlights();

        List<ViewFlights> flightsList = flight.stream()
                .map(f ->
                        new ViewFlights(f.getId(), f.getFirmOwner().getCompanyName(), f.getFlightFrom(), f.getFlightTo(), f.getTicketPrice(),
                                f.getPlaneNumber().getPlaneNumber()
                                , f.getTimeOfFlight(),
                                f.getTicketPrice())
                ).toList();
        model.addAttribute("flightsList", flightsList);
        return "flight-list";
    }

    //============== M O D E L  A T R I B U T E S ====================
    @ModelAttribute("addNewFlight")
    public AddFlightDto addFlight() {
        return new AddFlightDto();
    }
}
