package com.my.airportproject.controller;

import com.my.airportproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {
  private  final AuthService authService;

  @Autowired
    public HomeController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getHome() {

        return "index";
    }
}

