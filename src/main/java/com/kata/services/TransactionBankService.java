package com.kata.services;

import com.kata.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kata.dto.TransactionDto;
import com.kata.entity.Transaction;

@Service
public class TransactionBankService implements TransactionService {
	
	private TransactionRepository tansactionRepo;
	
	
	


	private TransactionRepository getTansactionRepo() {
		return tansactionRepo;
	}

	@Autowired
	private void setTansactionRepo(TransactionRepository tansactionRepo) {
		this.tansactionRepo = tansactionRepo;
	}
}
