package com.my.airportproject.service;


import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.model.dto.planes.PlaneDto;
import com.my.airportproject.model.entity.Plane;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@Transactional
public class PlaneService {

    private final PlaneRepository planeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PlaneService(PlaneRepository planeRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.planeRepository = planeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public void addPlane(PlaneDto planeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = this.userRepository.findByUsername(name).get();

        Optional<Plane> plane = this.planeRepository.findByPlaneNumber(planeDto.getPlaneNumber());

        if (plane.isEmpty()) {
            Plane newPlane = new Plane(planeDto.getPlaneNumber());
            newPlane.setPlaneOwnerFirm(user);

            this.planeRepository.save(newPlane);
        }
    }

    private boolean isPlaneExist(List<Plane> allPlanes, String currentPlane) {
        boolean isExist = false;
        for (Plane current : allPlanes) {
            if (current.getPlaneNumber().equals(currentPlane)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public boolean isNotFounded(String planeNumber) {
        Plane isFoundPlane = this.planeRepository.findByPlaneNumber(planeNumber).orElse(null);
        return isFoundPlane == null;
    }

    public boolean isFoundedPlane(String planeNumber) {
        Plane isFoundPlane = this.planeRepository.findByPlaneNumber(planeNumber).orElse(null);
        return isFoundPlane != null;
    }

    public List<Plane> getMyPlanes() {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        User user1 = this.userRepository.findByUsername(authenticatedUser.getName()).get();


        return  null;

    }
}
