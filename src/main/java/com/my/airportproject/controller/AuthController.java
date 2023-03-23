package com.my.airportproject.controller;


import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.dto.user.UserLoginDto;
import com.my.airportproject.model.dto.user.UserRegisterDto;
import com.my.airportproject.service.AuthService;
import com.my.airportproject.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;
    private final UserRoleService roleService;

    @Autowired
    public AuthController(AuthService authService, UserRoleService roleService) {
        this.authService = authService;
        this.roleService = roleService;
    }

    @ModelAttribute("userRegisterDto")
    public UserRegisterDto initForm() {
        return new UserRegisterDto();
    }

    @ModelAttribute("userLoginDto")
    public UserLoginDto loginForm() {
        return new UserLoginDto();
    }

    @ModelAttribute("changeRoleDto")
    public ChangeRoleDto changeRole() {
        return new ChangeRoleDto();
    }

    @GetMapping("/roles")
    public String getChangeRoles() {
        return "roles";
    }

    @PostMapping("/roles")
    public String postChangeRole(@Valid ChangeRoleDto changeRole,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("changeRole", changeRole);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeRole");
            return "redirect:/users/roles";
        }
        this.roleService.setNewRoleOnUser(changeRole);
        return "redirect:/home";

    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }


    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid UserRegisterDto userRegisterDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDto");
            return "redirect:/users/register";
        }
        this.authService.register(userRegisterDto);
        this.roleService.setRole(userRegisterDto.getUsername());
        return "redirect:/users/login";
    }


}
