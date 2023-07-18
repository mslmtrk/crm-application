package com.spring.dao;

import java.util.List;

import com.spring.entity.Customer;

public interface CustomerDAO {
	
	public List<Customer> getCustomers();
	
	public Customer getCustomer(int customerId);

	public Customer saveCustomer(Customer theCustomer);
	
	public void deleteCustomer(int customerId);
	
}