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
import org.springframework.validation.BindingResult;

import javax.annotation.PostConstruct;
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


    @PostConstruct
    private  void createRoles(){
        Role roleAdmin = new Role(EnumRoles.ADMIN);
        Role roleUser = new Role(EnumRoles.USER);
        Role roleFirm = new Role(EnumRoles.FIRM);

        Role admin = this.roleRepository.findRoleByName(EnumRoles.ADMIN).orElse(null);
        Role user = this.roleRepository.findRoleByName(EnumRoles.USER).orElse(null);
        Role firm = this.roleRepository.findRoleByName(EnumRoles.FIRM).orElse(null);
        if (firm==null && admin==null && user==null) {
            this.roleRepository.save(roleAdmin);
            this.roleRepository.save(roleFirm);
            this.roleRepository.save(roleUser);
        }
    }
}
