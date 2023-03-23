package com.my.airportproject.service;


import com.my.airportproject.model.dto.user.UserRegisterDto;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.repository.UserRepository;
import com.my.airportproject.repository.UserRoleRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
@Setter
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final UserRoleRepository roleRepository;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleService userRoleService, UserRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.roleRepository = roleRepository;
    }

    public void register(UserRegisterDto userRegisterDto) {
        this.userRoleService.addRole();

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
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow((() -> new UsernameNotFoundException("User not found!")));
    }
}
