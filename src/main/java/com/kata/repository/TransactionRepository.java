package com.kata.repository;

import com.kata.entity.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BankRepository<Transaction , Long> {



}
