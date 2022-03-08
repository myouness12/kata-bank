package com.kata.repository;

import com.kata.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BankRepository<Customer, Long> {

}
