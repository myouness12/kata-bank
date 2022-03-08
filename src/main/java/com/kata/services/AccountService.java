package com.kata.services;

import com.kata.entity.Transaction;

import com.kata.entity.Account;

import java.util.Optional;


public interface AccountService {
	
	 Account findAccountByCustomer(Long customer);
	
	void save(Account account);

	void saveTransactionInAccount(Account account, Transaction tx);



}

