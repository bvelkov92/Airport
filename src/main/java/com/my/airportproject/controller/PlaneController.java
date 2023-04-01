package com.my.airportproject.controller;

import com.my.airportproject.model.dto.planes.PlaneDto;
import com.my.airportproject.service.PlaneService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/planes")
public class PlaneController {

    private final PlaneService planeService;

    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }


    @GetMapping("/add-plane")
    public String getPlane() {
        return "add-plane";
    }

    @PostMapping("/add-plane")
    public String postPlane(@Valid PlaneDto planeDto,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("planeDto", planeDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.planeDto");
            return "redirect:/planes/add-plane";
        }
        this.planeService.addPlane(planeDto);
        return "add-plane";
    }

    @ModelAttribute("addPlane")
    public PlaneDto addPlane() {
        return new PlaneDto();
    }
}
