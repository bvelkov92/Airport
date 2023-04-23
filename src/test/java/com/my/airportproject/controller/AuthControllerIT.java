package com.my.airportproject.controller;

import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.entity.Role;
import com.my.airportproject.model.enums.EnumRoles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.my.airportproject.model.enums.EnumRoles.FIRM;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;


    //=========== REGISTER USER TEST ===============

    @Test
    void testGetRegistrationUser() throws Exception {
        mockMvc.perform(get("/users/registerUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerUser"))
                .andExpect(model().attributeExists("userRegisterDto"));


    }


    @Test
    void testPostRegistrationUser() throws Exception {
        mockMvc.perform(post("/users/registerUser")
                        .param("firstName", "First Name")
                        .param("lastName", "Last Name")
                        .param("username", "testUser")
                        .param("password", "testPassword")
                        .param("confirmPassword", "testPassword")
                        .param("email", "testUser@email.bg")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

    @Test
    void testPostRegistrationUser_withError() throws Exception {
        mockMvc.perform(post("/users/registerUser")
                        .param("firstName", "First Name")
                        .param("lastName", "Last Name")
                        .param("username", "testUser")
                        .param("password", "testPassword")
                        .param("confirmPassword", "test1Password")
                        .param("email", "testUser@email.bg")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/registerUser"));

    }

//============================ REGISTER FIRM TESTS ====================================

    @Test
    void testGetRegistrationFirm() throws Exception {
        mockMvc.perform(get("/users/registerFirm"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerFirm"))
                .andExpect(model().attributeExists("firmRegisterDto"));


    }

    @Test
    void testPostRegistrationFirm() throws Exception {

        mockMvc.perform(post("/users/registerFirm")
                        .param("companyName", "Company Name")
                        .param("username", "testFirm")
                        .param("password", "testPassword")
                        .param("confirmPassword", "testPassword")
                        .param("email", "testFirm@email.bg")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

    @Test
    void testPostRegistrationFirm_withError() throws Exception {
        mockMvc.perform(post("/users/registerUser")
                        .param("companyName", "Company Name")
                        .param("username", "testFirm")
                        .param("password", "testPassword")
                        .param("confirmPassword", "test1Password")
                        .param("email", "testFirm@email.bg")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/registerUser"));

    }

    @Test
    @WithMockUser(username = "admin@admin.bg", roles = {"ADMIN"})
    void testGetUserList() throws Exception {
        mockMvc.perform(get("/admin/users-list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allUsers"))
                .andExpect(view().name("users-list"));
    }


    @Test
    void testGetChangeUsername() {
    }

    @Test
    @WithMockUser(username = "admin@admin.bg", roles = {"ADMIN"})
    void testRemoveUser() throws Exception {
        mockMvc.perform( get("/admin/removeuser/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/users-list"));
    }

    @Test
    @WithMockUser(username = "admin@admin.bg", roles = {"ADMIN"})
    void testChangeSomeUsername() throws Exception {
        mockMvc.perform( get("/admin/change-some-username"))
                .andExpect(status().isOk())
                .andExpect(view().name("change-some-username"));
    }

    @Test
    @WithMockUser(username = "admin@admin.bg", roles = {"ADMIN"})
    void testPostChangeSomeUsername() throws Exception {

        mockMvc.perform(post("/admin/change-some-username")
                .param("username", "Pesho")
                .param("newUsername", "Gosho")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

    }

    @Test
    @WithMockUser(username = "admin@admin.bg", roles = {"ADMIN"})
    void testPostChangeSomeUsername_Error() throws Exception {

        mockMvc.perform(post("/admin/change-some-username")
                        .param("username", "Pesho")
                        .param("newUsername", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/change-username"));
    }


    @Test
    @WithMockUser(username = "admin@admin.bg", roles = {"ADMIN"})
    void testGetChangeRole() throws Exception {
        mockMvc.perform( get("/admin/roles"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles"));
    }
    @Test
    @WithMockUser(username = "admin@admin.bg", roles = {"ADMIN"})
    void testPostChangeRole_Error() throws Exception {
        mockMvc.perform(post("/admin/change-some-username")
                        .param("username", "Pesho")
                        .param("newUsername", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/change-username"));
    }
}
