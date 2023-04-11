package com.my.airportproject.service;


import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.dto.user.ChangeUsernameDto;
import com.my.airportproject.model.dto.user.UserRegisterDto;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.net.Authenticator;
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
        User user = new User(
                userRegisterDto.getUsername(),
                passwordEncoder.encode(userRegisterDto.getPassword()),
                userRegisterDto.getEmail(),
                userRegisterDto.getCompanyName()
        );
        this.userRepository.save(user);
        this.roleService.setRole(user.getUsername());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }


    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    public boolean checkCompany(String value) {
        return value.length() >= 1;
    }

    public boolean findByRole(ChangeRoleDto dto) {
        EnumRoles newRole = dto.getRole();
        User user = this.userRepository.findByUsername(dto.getUsername()).orElse(null);
        EnumRoles currentRole = null;
        if (user != null) {
            currentRole = user.getRoles().get(0).getName();
        }
        return !newRole.equals(currentRole);
    }

    public void deleteRow(Long id) {
        User user = this.userRepository.findById(id).get();
        this.userRepository.delete(user);
    }


    public void changeUsername(ChangeUsernameDto dto) {
        User user=new User();
        if (dto.getUsername()==null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           user = this.userRepository.findByEmail(authentication.getName()).get();
        }else {
            user=this.userRepository.findByUsername(dto.getUsername()).get();
        }
        user.setUsername(dto.getNewUsername());
        this.userRepository.save(user);
    }
}