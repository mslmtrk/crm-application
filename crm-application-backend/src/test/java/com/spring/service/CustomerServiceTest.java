package com.spring.service;

import com.spring.entity.Customer;
import com.spring.exception.CustomerNotFoundException;
import com.spring.model.input.CustomerRequest;
import com.spring.model.input.CustomerUpdateRequest;
import com.spring.repository.CustomerRepository;
import com.spring.repository.projections.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerServiceTest {

    @Autowired
    private CustomerServiceImpl customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void getCustomers() {
    	
        List<CustomerDto> mockCustomers = List.of(
        		createMockCustomerDto(1L, "Muslum", "Turk", "muslumturkk@gmail.com"), 
        		createMockCustomerDto(2L, "John", "Doe", "john.doe@example.com")
        );
        when(customerRepository.getAllCustomers()).thenReturn(mockCustomers);

        List<CustomerDto> serviceResult = customerService.getCustomers();

        assertNotNull(serviceResult);
        assertEquals(mockCustomers.size(), serviceResult.size());
    }

    @Test
    @WithMockUser
    void saveCustomer() {
    	when(customerRepository.save(any())).thenReturn(new Customer());

        customerService.saveCustomer(new CustomerRequest("Muslum", "Turk", "muslumturkk@gmail.com"));

        verify(customerRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser
    void updateCustomer() {
    	when(customerRepository.save(any())).thenReturn(new Customer());

        customerService.updateCustomer(new CustomerUpdateRequest(1L, "Muslum", "Turk", "muslumturkk@gmail.com"));

        verify(customerRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser
    void getCustomer() {
        CustomerDto mockCustomer = createMockCustomerDto(1L, "Muslum", "Turk", "muslumturkk@gmail.com");
        when(customerRepository.getCustomerById(anyLong())).thenReturn(Optional.of(mockCustomer));

        CustomerDto serviceResult = customerService.getCustomer(1L);

        assertNotNull(serviceResult);
        assertEquals(mockCustomer, serviceResult);
    }

    @Test
    @WithMockUser
    void getCustomer_NotFound() {
        when(customerRepository.getCustomerById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(1L));
    }

    @Test
    @WithMockUser
    void deleteCustomer() {
        doNothing().when(customerRepository).deleteById(anyLong());

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(anyLong());
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
