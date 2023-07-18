package com.spring.service;

import java.util.List;

import com.spring.entity.Customer;

public interface EntityService {
	public List<Customer> getCustomers();

	public Customer saveCustomer(Customer theCustomer);

	public Customer getCustomer(int customerId);

	public void deleteCustomer(int customerId);
}
