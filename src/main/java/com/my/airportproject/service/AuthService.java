package com.my.airportproject.service;


import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.dto.user.AdminChangeSomeUsernameDto;
import com.my.airportproject.model.dto.user.ChangeMyUsernameDto;
import com.my.airportproject.model.dto.user.FirmRegisterDto;
import com.my.airportproject.model.dto.user.UserRegisterDto;
import com.my.airportproject.model.entity.Role;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.repository.RoleRepository;
import com.my.airportproject.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public void registerUser(UserRegisterDto userRegisterDto) {
        User user = new User(
                userRegisterDto.getUsername(),
                passwordEncoder.encode(userRegisterDto.getPassword()),
                userRegisterDto.getEmail(),
                userRegisterDto.getFirstName(),
                userRegisterDto.getLastName()
        );
        if (this.userRepository.count()==0) {

            user.getRoles().add( this.roleRepository.findRoleByName(EnumRoles.ADMIN).get());
        }else {
            user.getRoles().add( this.roleRepository.findRoleByName(EnumRoles.USER).get());
        }

        User map = this.modelMapper.map(user, User.class);

        this.userRepository.saveAndFlush(map);
    }

    public void registerFirm(FirmRegisterDto firmRegisterDto) {
        User user = new User(
                firmRegisterDto.getCompanyName(),
                firmRegisterDto.getUsername(),
                passwordEncoder.encode(firmRegisterDto.getPassword()),
                firmRegisterDto.getEmail());
        Optional<Role> firm = this.roleRepository.findRoleByName(EnumRoles.FIRM);
        user.getRoles().add(firm.get());
        User map = this.modelMapper.map(user, User.class);
        this.userRepository.saveAndFlush(map);
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