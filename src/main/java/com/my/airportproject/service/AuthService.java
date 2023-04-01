package com.my.airportproject.service;


import com.my.airportproject.model.dto.user.UserRegisterDto;
import com.my.airportproject.model.entity.User;
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
    private final RoleService roleService;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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
        this.roleService.setRole(user.getUsername());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow((() -> new UsernameNotFoundException("User not found!")));
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }


}
