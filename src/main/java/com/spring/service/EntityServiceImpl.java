package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.CustomerDAO;
import com.spring.entity.Customer;

@Service
public class EntityServiceImpl implements EntityService {
	
	@Autowired
	private CustomerDAO customerDao;
	
	@Override
	@Transactional
	public List<Customer> getCustomers() {
		return customerDao.getCustomers();
	}

	@Override
	@Transactional
	public Customer saveCustomer(Customer theCustomer) {

		return customerDao.saveCustomer(theCustomer);	
	}

	@Override
	@Transactional
	public Customer getCustomer(int customerId) {
		
		return customerDao.getCustomer(customerId);
	}
	
	@Override
	@Transactional	
	public void deleteCustomer(int customerId) {

		customerDao.deleteCustomer(customerId);
	}

}
