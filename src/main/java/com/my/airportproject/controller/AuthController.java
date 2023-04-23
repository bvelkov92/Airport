package com.my.airportproject.controller;
import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.dto.user.*;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.service.AuthService;
import com.my.airportproject.service.RoleService;
import com.my.airportproject.views.ViewUserInChangeUsernameOnNotAdminRole;
import com.my.airportproject.views.ViewUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.security.Principal;
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
    @GetMapping("/users/question")
    public String getQuestion() {
        return "isFirmOrUser";
    }

    @PostMapping("/users/isFirmOrUser")
    public String postAnswer(QuestionDto questionDto) {

        if (questionDto.getAnswer().equals("USER")){
            return "redirect:/users/registerUser";
        }

        return "redirect:/users/registerFirm";
    }





    @GetMapping("/users/registerUser")
    public String getRegister() {

        return "registerUser";
    }

    @Transactional
    @PostMapping("/users/registerUser")
    public String postRegister(@Valid UserRegisterDto userRegisterDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDto", bindingResult);
            return "redirect:/users/registerUser";
        }
        this.authService.registerUser(userRegisterDto);
        return "redirect:/";
    }



    @GetMapping("/users/registerFirm")
    public String getRegisterFirm() {
        return "registerFirm";
    }

    @Transactional
    @PostMapping("/users/registerFirm")
    public String postRegister(@Valid FirmRegisterDto firmRegisterDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("firmRegisterDto", firmRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.firmRegisterDto", bindingResult);
            return "redirect:/users/registerFirm";
        }

        this.authService.registerFirm(firmRegisterDto);
        return "redirect:/";
    }

    //================== O P T I O N S ===============

    //USERLIST

    @GetMapping("/admin/users-list")
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


    @GetMapping("/users/change-username")
    public String getChangeUser(Principal principal, Model model){
            User user = this.authService.getByEmail(principal.getName());
            ViewUserInChangeUsernameOnNotAdminRole viewUser = new ViewUserInChangeUsernameOnNotAdminRole(user.getUsername());
            model.addAttribute("username", viewUser);

        return "change-username";
    }


    @PostMapping("/users/change-username")
    public String postChangeUsername(Principal principal, @Valid ChangeMyUsernameDto usernameDto,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("usernameDto", usernameDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.usernameDto", bindingResult);
            return "redirect:/users/change-username";
        }
        String username = principal.getName();
        User user = this.authService.changeMyUsername(username, usernameDto);
        if (user!=null) {
            return "redirect:/";
        }
        return "redirect:/users/change-username";
    }


    @GetMapping("/admin/change-some-username")
    public String getChangeSomeUsername(){
        return "change-some-username";
    }


    @PostMapping("/admin/change-some-username")
    public String postChangeSomeUsername(@Valid AdminChangeSomeUsernameDto usernameDto,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("usernameDto", usernameDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.usernameDto", bindingResult);
            return "redirect:/users/change-username";
        }

        this.authService.changeSomeUsername(usernameDto);
        return "redirect:/";
    }



    // ============================ CHANGE ROLE OPTIONS ========================

    @GetMapping("/admin/roles")
    public String getChangeRoles() {
        return "roles";
    }

    @PostMapping("/admin/roles")
    public String postChangeRole(@Valid ChangeRoleDto changeRole,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("changeRole", changeRole);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeRole",bindingResult);
            return "redirect:/admin/roles";
        }

        this.roleService.setNewRoleOnUser(changeRole);
        return "redirect:/";
    }

    @Transactional
    @GetMapping("/admin/removeuser/{id}")
    public String removeUser(@PathVariable("id") Long id){
        this.authService.deleteRow(id);
        return "redirect:/admin/users-list";
    }




    //// ================= M O D E L   A T R I B U T E S ================================

    @ModelAttribute("userRegisterDto")
    public UserRegisterDto formForUsers() {
        return new UserRegisterDto();
    }

    @ModelAttribute("isFirmOrUser")
    public QuestionDto question() {
        return new QuestionDto();
    }

    @ModelAttribute("firmRegisterDto")
    public FirmRegisterDto formForFirms() {
        return new FirmRegisterDto();
    }

    @ModelAttribute("userLoginDto")
    public UserLoginDto loginForm() {
        return new UserLoginDto();
    }

    @ModelAttribute("changeRoleDto")
    public ChangeRoleDto changeRole() {
        return new ChangeRoleDto();
    }

    @ModelAttribute("changeMyUsername")
    public ChangeMyUsernameDto changeUsernameDto(){
        return new ChangeMyUsernameDto();
    }


    @ModelAttribute("changeSomeUsername")
    public AdminChangeSomeUsernameDto adminChangeSomeUsername(){
        return new AdminChangeSomeUsernameDto();
    }


}