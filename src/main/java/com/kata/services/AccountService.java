package com.kata.services;

import com.kata.dto.ResultOperation;
import com.kata.entity.Account;
import com.kata.enums.Operation;


public interface AccountService {
	
	 Account findAccountByCustomer(Long customer);

	ResultOperation appliedOperationAccount(Account account, double amount, Operation typeOperation);
	




}

