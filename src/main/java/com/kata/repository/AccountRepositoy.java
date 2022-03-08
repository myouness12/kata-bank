package com.kata.repository;

import java.util.Optional;
import java.util.Set;

import com.kata.entity.Account;
import com.kata.entity.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepositoy extends BankRepository<Account, Long> {

	@Query("from Account where customer.customer_ident = ?1")
	Optional<Account>  findAccountByCustomer(long customer);

	@Query("From Account as a JOIN FETCH a.transactions where  a.account_ident = ?1")
	Optional<Account> findAccount(long accountIdent);

	@Modifying
	@Query("UPDATE Account set balance = ?1 where account_ident = ?2 ")
	@Transactional(readOnly = false)
	void updateBalanceAccount(double newBalance, long account_ident);





}
