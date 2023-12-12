package com.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.model.input.AuthenticationRequest;
import com.spring.model.output.JwtResponse;
import com.spring.service.CustomerService;
import com.spring.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void signIn_ValidAuthentication_ReturnsJwtResponse() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new AuthenticationRequest("username", "password"));
        when(userService.signIn(new AuthenticationRequest("username", "password"))).thenReturn(new JwtResponse("token", "username", new Date()));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.expiryDate").exists());
    }

    @Test
    void signUp_NewUser_SuccessfullyRegistersUser() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new AuthenticationRequest("newUser", "password"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());
    }

}
