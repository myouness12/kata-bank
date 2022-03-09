package com.kata.services;

import com.kata.entity.Account;
import com.kata.enums.Operation;


public interface AccountService {
	
	 Account findAccountByCustomer(Long customer);

	Account appliedOperationAccount(Account account, double amount, Operation typeOperation);
	




}

