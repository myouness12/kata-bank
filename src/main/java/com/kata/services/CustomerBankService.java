package com.kata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kata.repository.CustomerRepository;

@Service
public class CustomerBankService implements CustomerService {
	
	
	private CustomerRepository customerRepository;
	
	

	

	private CustomerRepository getCustomerRepository() {
		return customerRepository;
	}

	
	@Autowired
	private void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	
	
	

}
