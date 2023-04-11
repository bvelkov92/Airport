package com.my.airportproject.controller;

import com.my.airportproject.service.PlaneService;
import com.my.airportproject.views.ViewMyPlane;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestPlains {

    private final PlaneService planeService;

    public RestPlains(PlaneService planeService) {
        this.planeService = planeService;
    }


    @GetMapping("/planes/plane-list")
    public ResponseEntity<List<ViewMyPlane>> getMyPlaneList(Model model) {
        var planes = this.planeService.getMyPlanes();
        List<ViewMyPlane> planeList = planes.stream().map(el -> new ViewMyPlane(el.getPlaneNumber()))
                .toList();
        return ResponseEntity.ok(planeList);
    }
}
