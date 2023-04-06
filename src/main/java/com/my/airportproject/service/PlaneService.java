package com.my.airportproject.service;

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

    public boolean isNotFounded(String planeNumber) {
        Plane isFoundPlane = this.planeRepository.findByPlaneNumber(planeNumber).orElse(null);
        return isFoundPlane == null;
    }

    public boolean isFoundedPlane(String planeNumber) {
        Plane isFoundPlane = this.planeRepository.findByPlaneNumber(planeNumber).orElse(null);
        return isFoundPlane != null;
    }

    public List<Plane> getMyPlanes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = this.userRepository.findByUsername(authentication.getName()).get();

        return this.planeRepository.findAllByPlaneOwnerFirm(loggedUser).stream().toList();
    }
}
