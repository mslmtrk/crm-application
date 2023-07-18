package com.spring.controller;

import java.util.List;

import com.spring.model.input.CustomerRequest;
import com.spring.model.input.CustomerUpdateRequest;
import com.spring.repository.projections.CustomerDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.service.CustomerService;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping
    @Retry(name = "get-customers")
    public ResponseEntity<List<CustomerDto>> getCustomers() {

        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {

        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Bulkhead(name = "modify-customer")
    public void saveCustomer(@Valid @RequestBody CustomerRequest customerRequest) {

        customerService.saveCustomer(customerRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Bulkhead(name = "modify-customer")
    public void updateCustomer(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {

        customerService.updateCustomer(customerUpdateRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Bulkhead(name = "modify-customer")
    public void deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);
    }
}



