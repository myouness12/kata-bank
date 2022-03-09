package com.kata.repository;

import com.kata.entity.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepositoy extends BankRepository<Account, Long> {

	@Query("from Account where customer.customerIdent = ?1")
	Optional<Account>  findAccountByCustomer(long customer);

	@Query("From Account as a JOIN FETCH a.transactions where  a.accountIdent = ?1")
	Optional<Account> findAccountAndListTransaction(long accountIdent);

	@Modifying
	@Query("UPDATE Account set balance = ?1 where accountIdent = ?2 ")
	@Transactional(readOnly = false)
	void updateBalanceAccount(double newBalance, long accountIdent);





}
