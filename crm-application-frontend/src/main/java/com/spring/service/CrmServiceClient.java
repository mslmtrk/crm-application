package com.spring.service;

import com.spring.model.Customer;
import com.spring.model.JwtResponse;
import com.spring.model.LoginRequest;
import com.spring.model.SignupRequest;
import feign.Headers;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Headers("Content-Type: application/json")
@FeignClient(name = "crm-backend")
public interface CrmServiceClient {

    @PostMapping("/api/v1/auth/signin")
    ResponseEntity<JwtResponse> signIn(LoginRequest authenticationRequest);

    @PostMapping("/api/v1/auth/signup")
    void signUp(SignupRequest signupRequest);

    @GetMapping("/api/v1/customers")
    ResponseEntity<List<Customer>> getCustomers();

    @GetMapping("/api/v1/customers/{id}")
    ResponseEntity<Customer> getCustomer(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable Long id);

    @PostMapping("/api/v1/customers")
    void saveCustomer(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, Customer customer);

    @PutMapping("/api/v1/customers")
    void updateCustomer(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, Customer customer);

    @DeleteMapping("/api/v1/customers/{id}")
    void deleteCustomer(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable Long id);
}
