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

    public void setRole(String username) {
        User user = this.userRepository.findByUsername(username).get();
        Role role;
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

    public void setNewRoleOnUser(ChangeRoleDto changeRoleDto, Long id) {
        changeRoleDto.setUsername(this.userRepository.findById(id).get().getUsername());
        User user = this.userRepository.findByUsername(changeRoleDto.getUsername()).get();
        Role role = this.roleRepository.findRoleByName(changeRoleDto.getRole()).get();
        user.getRoles().clear();
        user.getRoles().add(role);
        this.userRepository.save(user);
    }

    public User getUserByName(String username) {
      return  this.userRepository.findByUsername(username).orElse(null);
    }
}
