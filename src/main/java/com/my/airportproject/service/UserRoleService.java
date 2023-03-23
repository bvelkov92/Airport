package com.my.airportproject.service;

import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.entity.Role;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.repository.UserRepository;
import com.my.airportproject.repository.UserRoleRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class UserRoleService {


    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRoleService(UserRepository userRepository, UserRoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public void addRole() {
        String roles;
        if (this.roleRepository.count() == 0) {
            roles = "ADMIN";
        } else if (this.roleRepository.findByName("USER").isEmpty()) {
            roles = "USER";
        } else {
            return;
        }
        Role role = new Role(roles);
        this.roleRepository.save(role);
    }

    public void setRole(String username) {
        User user = this.userRepository.findByUsername(username).get();
        Role role;
        if (this.userRepository.count() <= 1) {
            role = this.roleRepository.findByName("ADMIN").get();
            Role nextRole = new Role("USER");
            this.roleRepository.save(nextRole);
        } else {
            role = this.roleRepository.findByName("USER").get();
        }
        user.getRoles().clear();
        user.getRoles().add(role);
        this.userRepository.saveAndFlush(user);
    }

    public void setNewRoleOnUser(ChangeRoleDto changeRoleDto) {

        if (this.userRepository.findByUsername(changeRoleDto.getUsername()).isEmpty()) {
            throw new RuntimeException("Username not found!");
        }

        if (this.roleRepository.findByName(changeRoleDto.getRole()).isEmpty()) {
            Role newRole = new Role(changeRoleDto.getRole());
            this.roleRepository.save(newRole);
        }
        User user = this.userRepository.findByUsername(changeRoleDto.getUsername()).get();
        Role newRole = this.roleRepository.findByName(changeRoleDto.getRole()).get();
        user.getRoles().add(newRole);
        this.userRepository.save(user);
    }
}
