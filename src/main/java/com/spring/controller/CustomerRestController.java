package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.Customer;
import com.spring.exception.CustomerNotFoundException;
import com.spring.service.EntityService;

@RestController
@RequestMapping("/v1")
public class CustomerRestController {
	
	@Autowired
	private EntityService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		
		return customerService.getCustomers();
	}
	
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId){
		
		Customer theCustomer = customerService.getCustomer(customerId);
		
		if(theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}

		return theCustomer;
	}
	
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		
		theCustomer.setId(0);
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	@PutMapping("/customers")
	public Customer UpdateCustomer(@RequestBody Customer theCustomer) {
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId){
		
		Customer theCustomer = customerService.getCustomer(customerId);
		
		if(theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}

		customerService.deleteCustomer(customerId);
		
		return "Deleted customer id - " + customerId;
	}
	
}



