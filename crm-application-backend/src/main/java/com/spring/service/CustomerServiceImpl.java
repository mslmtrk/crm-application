package com.spring.service;

import java.util.List;

import com.spring.exception.CustomerNotFoundException;
import com.spring.model.input.CustomerRequest;
import com.spring.model.input.CustomerUpdateRequest;
import com.spring.repository.projections.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.repository.CustomerRepository;
import com.spring.entity.Customer;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerDto> getCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public void saveCustomer(CustomerRequest customerRequest) {

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRequest, customer);

        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(CustomerUpdateRequest customerUpdateRequest) {

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerUpdateRequest, customer);

        customerRepository.save(customer);
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        return customerRepository.getCustomerById(id)
                .orElseThrow(() -> new CustomerNotFoundException("No such customer found with id: " + id));
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
