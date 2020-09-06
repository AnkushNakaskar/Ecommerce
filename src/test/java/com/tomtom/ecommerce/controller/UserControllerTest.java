package com.tomtom.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtom.ecommerce.bean.person.User;
import com.tomtom.ecommerce.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserService userService;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGetAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(get("/users")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testGetUser() throws Exception {
        User user = new User();
        user.setName("test");
        user.setId(1L);
        Mockito.when(userService.getUser(123L)).thenReturn(user);
        this.mockMvc.perform(get("/users/" + 123l)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testaddNewUser() throws Exception {
        User user = new User();
        user.setName("test");
        user.setId(1L);
        this.mockMvc.perform(post("/users").contentType("application/json").content(objectMapper.writeValueAsString(user))).andExpect(status().isOk()).andDo(print());
    }

}
