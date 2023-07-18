package com.spring.service;

import java.util.List;

import com.spring.entity.Customer;
import com.spring.model.input.CustomerRequest;
import com.spring.model.input.CustomerUpdateRequest;
import com.spring.repository.projections.CustomerDto;

public interface CustomerService {

    List<CustomerDto> getCustomers();

    void saveCustomer(CustomerRequest customerRequest);

    void updateCustomer(CustomerUpdateRequest customerUpdateRequest);

    CustomerDto getCustomer(Long id);

    void deleteCustomer(Long id);
}
