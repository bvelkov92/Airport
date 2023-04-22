package com.my.airportproject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlaneControllerIT {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@admin.bg", roles = {"ADMIN"})
    void testGetAddPlane() throws Exception {
        mockMvc.perform(get("/planes/add-plane"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-plane"))
                .andExpect(model().attributeExists("addPlane"));
    }

    @Test
    @WithMockUser(username = "admin@admin.bg", roles = {"ADMIN"})
    void testPostAddPlane() throws Exception {
        mockMvc.perform(post("/planes/add-plane")
                        .param("planeNumber", "testPlaneNumber")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "admin@admin.bg", roles = {"ADMIN"})
    void testPostAddPlane_InvalidInput() throws Exception {
        mockMvc.perform(post("/planes/add-plane")
                        .param("planeNumber", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/planes/add-plane"));
    }



}
