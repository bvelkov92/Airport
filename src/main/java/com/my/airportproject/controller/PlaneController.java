package com.my.airportproject.controller;

import com.my.airportproject.model.dto.planes.PlaneDto;
import com.my.airportproject.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.nio.file.FileAlreadyExistsException;
import java.security.Principal;


@Controller

public class PlaneController {

    private final PlaneService planeService;

    @Autowired
    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }


    // =============== PLANES (ADD, GET LIST)  ======================

    @GetMapping("/planes/add-plane")
    public String getPlane() {
        return "add-plane";
    }

    @PostMapping("/planes/add-plane")
    public String postPlane(Principal loggedUser, @Valid PlaneDto planeDto,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("planeDto", planeDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.planeDto");
            return "redirect:/planes/add-plane";
        }

        boolean isAdded = this.planeService.addPlane(loggedUser.getName(), planeDto);
        if (isAdded) {
            System.out.println("Plane Added Successful!");
        }
        return "redirect:/";
    }


    //============== M O D E L  A T R I B U T E S ====================

    @ModelAttribute("addPlane")
    public PlaneDto addPlane() {
        return new PlaneDto();
    }
}
