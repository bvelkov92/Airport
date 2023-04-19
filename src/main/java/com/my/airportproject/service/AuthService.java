package com.my.airportproject.service;


import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.dto.user.AdminChangeSomeUsernameDto;
import com.my.airportproject.model.dto.user.ChangeMyUsernameDto;
import com.my.airportproject.model.dto.user.UserRegisterDto;
import com.my.airportproject.model.entity.Role;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public void register(UserRegisterDto userRegisterDto) {
        User user = new User(
                userRegisterDto.getUsername(),
                passwordEncoder.encode(userRegisterDto.getPassword()),
                userRegisterDto.getEmail(),
                userRegisterDto.getCompanyName()
        );
        this.userRepository.save(user);
        this.roleService.setRole(user.getEmail());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    public boolean getUserByUsernameForChangeUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElse(null);
        return !username.isEmpty() && !username.isBlank() && user == null;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }


    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    public boolean checkCompany(String value) {
        return value.length() >= 1;
    }

    public boolean changeRole(ChangeRoleDto dto) {
        EnumRoles newRoleEnum = dto.getRole();
        User user = this.userRepository.findByEmail(dto.getEmail()).orElse(null);
        if (user != null) {
            Role currentRole = user.getRoles().get(0);
            Role newRole = new Role(newRoleEnum);
            return !currentRole.equals(newRole);
        }
        return false;
    }

    public boolean deleteRow(Long id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user != null) {
            this.userRepository.delete(user);
            return true;
        }
        return false;
    }

    public boolean changeSomeUsername(AdminChangeSomeUsernameDto dto) {
        User user = this.userRepository.findByUsername(dto.getUsername()).orElse(null);
        if (user != null) {
            user.setUsername(dto.getNewUsername());
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    public User changeMyUsername(String username, ChangeMyUsernameDto dto) {
        User user = this.userRepository.findByEmail(username).orElse(null);
        if (user != null) {
            user.setUsername(dto.getNewUsername());
            this.userRepository.save(user);
        }
        return user;
    }

}