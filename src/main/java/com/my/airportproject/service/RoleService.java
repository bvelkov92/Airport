package com.my.airportproject.service;

import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.entity.Role;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.repository.RoleRepository;
import com.my.airportproject.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Getter
@Setter
public class RoleService {
    private final RoleRepository roleRepository;
    private  final UserRepository userRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void setRole(String email) {
        User user = this.userRepository.findByEmail(email).orElse(null);
        Role role= new Role();
        if (this.userRepository.count() <= 1) {
            role = new Role(EnumRoles.ADMIN);
            user.getRoles().add(role);
            Role role2 = new Role(EnumRoles.FIRM);
            Role role1 = new Role(EnumRoles.USER);
            this.roleRepository.save(role2);
            this.roleRepository.save(role1);
            this.roleRepository.save(role);
        } else {
            role = this.roleRepository.findRoleByName(EnumRoles.USER).get();
            user.getRoles().add(role);
        }
        this.userRepository.save(user);
    }

    public boolean setNewRoleOnUser(ChangeRoleDto changeRoleDto) {
        User user = this.userRepository.findByEmail(changeRoleDto.getEmail()).orElse(null);
        EnumRoles curentRole=null;
        if (user!=null) {
             curentRole = user.getRoles().get(0).getName();
        }
        Role role = this.roleRepository.findRoleByName(changeRoleDto.getRole()).orElse(null);
        if (user != null && role!=null && !curentRole.equals(changeRoleDto.getRole())) {
            user.setRoles(new ArrayList<>());
            user.getRoles().add(role);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }
}
