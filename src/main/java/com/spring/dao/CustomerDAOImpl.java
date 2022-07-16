package com.spring.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Customer> getCustomers() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by firstName", Customer.class);
		
		List<Customer> customers = theQuery.getResultList();
		
		return customers;
	}

	@Override
	public Customer saveCustomer(Customer theCustomer) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(theCustomer);
		
		return theCustomer;
	}

	@Override
	public Customer getCustomer(int customerId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Customer theCustomer = currentSession.get(Customer.class, customerId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int customerId) {

		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("delete from Customer where id =:customerId");
		theQuery.setParameter("customerId", customerId);
		
		theQuery.executeUpdate();
	}

}


