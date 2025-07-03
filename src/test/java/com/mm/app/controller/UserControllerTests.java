/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mm.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mm.app.exception.EntityExistsException;
import com.mm.app.model.User;
import com.mm.app.service.UserService;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author Milos
 */
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void saveUserSuccessTest() throws Exception {
        User user = new User(1L, "UC1", "UC1", "uc1@mail.com", "uc1");
        Mockito.when(userService.save(user)).thenReturn(user);
        mockMvc.perform(post("/user/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(user.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.equalTo(user.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.equalTo(user.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.equalTo(user.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.equalTo(user.getPassword())));
    }

    @Test
    public void saveUserFailureTest() throws Exception {
        User user = new User(2L, "UC2", "UC2", "uc2@mail.com", "uc2");
        Mockito.when(userService.save(user)).thenThrow(EntityExistsException.class);
        mockMvc.perform(post("/user/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void findAllTest() throws Exception {
        User user1 = new User(3L, "UC3", "UC3", "uc3@mail.com", "uc3");
        User user2 = new User(4L, "UC4", "UC4", "uc4@mail.com", "uc4");
        List<User> users = Arrays.asList(user1, user2);
        Mockito.when(userService.findAll()).thenReturn(users);
        MvcResult result = mockMvc.perform(get("/user/all")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        List<User> userData = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<User>>() {
        });
        Assertions.assertTrue(userData.contains(user1));
        Assertions.assertTrue(userData.contains(user2));
    }
}
