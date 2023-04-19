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


    @Test
    void testGetRegistration() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userRegisterDto"));


    }

    @Test
    void testPostRegistration() throws Exception {

        mockMvc.perform(post("/users/register")
                        .param("companyName", "Test Company")
                        .param("username", "testUser")
                        .param("password", "testPassword")
                        .param("confirmPassword", "testPassword")
                        .param("email", "test@email.bg")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }


    @Test
    void testPostRegistration_withError() throws Exception {

        mockMvc.perform(post("/users/register")
                        .param("companyName", "Test Company")
                        .param("username", "testUser")
                        .param("password", "testPassword")
                        .param("confirmPassword", "test1Password")
                        .param("email", "test@email.bg")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"));

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
    void testPostChangeRole() throws Exception {
        ChangeRoleDto changeRoleDto = new ChangeRoleDto();
        changeRoleDto.setEmail("test@test.bg");
        changeRoleDto.setRole(FIRM);

        mockMvc.perform(post("/admin/roles").flashAttr("changeRoleDto", changeRoleDto)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

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
