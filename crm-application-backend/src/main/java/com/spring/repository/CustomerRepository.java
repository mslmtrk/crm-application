package com.spring.repository;

import com.spring.entity.Customer;
import com.spring.repository.projections.CustomerDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.id = :id")
    Optional<CustomerDto> getCustomerById(Long id);

    @Query("SELECT c FROM Customer c ORDER BY c.firstName")
    List<CustomerDto> getAllCustomers();

    Optional<Customer> findByEmail(String email);
}