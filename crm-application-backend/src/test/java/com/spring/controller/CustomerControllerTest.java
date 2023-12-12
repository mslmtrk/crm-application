package com.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.model.input.CustomerRequest;
import com.spring.model.input.CustomerUpdateRequest;
import com.spring.repository.projections.CustomerDto;
import com.spring.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCustomers_ShouldReturnCustomerList() throws Exception {
        when(customerService.getCustomers()).thenReturn(List.of(
        		createMockCustomerDto(1L, "Muslum", "Turk", "muslumturkk@gmail.com"), 
        		createMockCustomerDto(2L, "John", "Doe", "john.doe@example.com")
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value("muslumturkk@gmail.com"))
                .andExpect(jsonPath("$[1].email").value("john.doe@example.com"));
    }

    @Test
    @WithMockUser
    void getCustomer_ShouldReturnCustomerDto() throws Exception {
        Long customerId = 1L;
        when(customerService.getCustomer(customerId)).thenReturn(createMockCustomerDto(1L, "Muslum", "Turk", "muslumturkk@gmail.com"));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("muslumturkk@gmail.com"));
    }

    @Test
    @WithMockUser
    void saveCustomer_ShouldReturnCreated() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest("Muslum", "Turk", "muslumturkk@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isCreated());

        verify(customerService, times(1)).saveCustomer(customerRequest);
    }

    @Test
    @WithMockUser
    void updateCustomer_ShouldReturnNoContent() throws Exception {
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(1L, "Muslum", "Turk", "muslumturkk@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNoContent());

        verify(customerService, times(1)).updateCustomer(updateRequest);
    }

    @Test
    @WithMockUser
    void deleteCustomer_ShouldReturnNoContent() throws Exception {
        Long customerId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/customers/{id}", customerId))
                .andExpect(status().isNoContent());

        verify(customerService, times(1)).deleteCustomer(customerId);
    }
    
    private CustomerDto createMockCustomerDto(Long id, String firstName, String lastName, String email) {
        return new CustomerDto() {
            @Override
            public Long getId() {
                return id;
            }

            @Override
            public String getFirstName() {
                return firstName;
            }

            @Override
            public String getLastName() {
                return lastName;
            }

            @Override
            public String getEmail() {
                return email;
            }
        };
    }
}
