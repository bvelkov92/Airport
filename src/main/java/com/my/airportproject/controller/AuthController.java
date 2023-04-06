package com.my.airportproject.controller;


import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.dto.user.UserLoginDto;
import com.my.airportproject.model.dto.user.UserRegisterDto;
import com.my.airportproject.service.AuthService;
import com.my.airportproject.service.RoleService;
import com.my.airportproject.views.ViewUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthController {

    private final AuthService authService;
    private final RoleService roleService;

    @Autowired
    public AuthController(AuthService authService, RoleService roleService) {
        this.authService = authService;
        this.roleService = roleService;
    }


    //======================== L O G I N ======================================

    @GetMapping("users/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("users/login-error")
    private String failedLogin(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("bad_credentials", true);

        return "redirect:/users/login";
    }


    // =================== R E G I S T E R ============================

    @GetMapping("/users/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/users/register")
    public String postRegister(@Valid UserRegisterDto userRegisterDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDto");
            return "redirect:/users/register";
        }

        this.authService.register(userRegisterDto);
        return "redirect:/welcome";
    }
    //================== O P T I O N S ===============

    //USERLIST

    @GetMapping("/users/users-list")
    public String getUserList(Model model) {
        var userList = this.authService.getAllUsers();


        List<ViewUsers> allUsers = userList.stream().map(user ->
                        new ViewUsers(user.getId(), user.getUsername(), user.getEmail(),
                                user.getRoles().get(0).getName()))
                .collect(Collectors.toList());

        model.addAttribute("allUsers", allUsers);
        return "users-list";
    }

    // ========== OPTIONS FOR ADD PLANE AND FLIGHT ============

    @GetMapping("/users/options")
    public String getOptionMenu() {
        return "options";
    }


    // ============================ CHANGE ROLE OPTIONS ========================


    @GetMapping("/users/roles")
    public String getChangeRoles() {
        return "roles";
    }

    @PostMapping("/users/roles")
    public String postChangeRole(@Valid ChangeRoleDto changeRole,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("changeRole", changeRole);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeRole");
            return "redirect:/users/roles";
        }
        this.roleService.setNewRoleOnUser(changeRole);
        return "redirect:/welcome";
    }

    //// ================= M O D E L   A T R I B U T E S ================================

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
}