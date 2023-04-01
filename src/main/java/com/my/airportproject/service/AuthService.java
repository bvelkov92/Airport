package com.my.airportproject.service;


import com.my.airportproject.model.dto.roles.ChangeRoleDto;
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
import java.util.Optional;

@Service
@Getter
@Setter
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            throw new RuntimeException("password.match");
        }
        Optional<User> byUsername = this.userRepository.findByUsername(userRegisterDto.getUsername());

        if (byUsername.isPresent()) {
            throw new RuntimeException("user.used");
        }

        User user = new User(
                userRegisterDto.getUsername(),
                passwordEncoder.encode(userRegisterDto.getPassword()),
                userRegisterDto.getEmail()
        );
        this.userRepository.save(user);
        setRole(user.getUsername());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow((() -> new UsernameNotFoundException("User not found!")));
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void setRole(String username) {
        User user = this.userRepository.findByUsername(username).get();
        Role role;
        if (this.userRepository.count() <= 1) {
            role = new Role(EnumRoles.ADMIN);
            user.getRoles().add(role);
        } else {
            role = new Role(EnumRoles.USER);
            user.getRoles().add(role);
        }
        this.userRepository.save(user);
    }

    public void setNewRoleOnUser(ChangeRoleDto changeRoleDto) {

        if (this.userRepository.findByUsername(changeRoleDto.getUsername()).isEmpty()) {
            throw new RuntimeException("Username not found!");
        }
        User user = this.userRepository.findByUsername(changeRoleDto.getUsername()).get();
        user.getRoles().clear();
        Role role = new Role(changeRoleDto.getRole());
        user.getRoles().add(role);
        this.userRepository.save(user);
    }
}
