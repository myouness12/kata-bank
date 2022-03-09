package com.kata.services;

import com.kata.dto.AccountHistory;
import com.kata.dto.AccountHistoryMapper;
import com.kata.entity.Transaction;
import com.kata.enums.Operation;
import com.kata.exception.AccountNotFoundException;
import com.kata.exception.DepositeOperationException;
import com.kata.exception.NoOperationFoundException;
import com.kata.exception.WithdrawalOperationException;
import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kata.entity.Account;
import com.kata.repository.AccountRepositoy;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
 public class AccountBankService implements AccountService {


	static final Double MIN_DEPOSIT = 0.01;

	private  AccountRepositoy accountRepositoy;






	@Transactional(readOnly = true)
	public Account findAccountByCustomer(Long customer) {
		return accountRepositoy.findAccountByCustomer(customer).orElseThrow(AccountNotFoundException::new);
	}


	
	@Transactional(readOnly = false)
	public void save(Account account) {
		accountRepositoy.save(account);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveTransactionInAccount(Account account, Transaction tx) {
		accountRepositoy.updateBalanceAccount(account.getBalance(), account.getAccount_ident() );
		account.getTransactions().add(tx);
		accountRepositoy.save(account);
	}



	public Account operation(Operation operationType, double amount, Long customer_ident) {
		Account account = accountRepositoy.findAccountByCustomer(customer_ident).orElseThrow(AccountNotFoundException::new);
		switch (operationType) {
			case DEPOSIT:
				if(amount > MIN_DEPOSIT) {
					return depositeAcount(account, amount);
				}else {
					throw new DepositeOperationException();
				}
			case WITHDRAWAL:
				if (account.getBalance() >= amount){
					return  withdrawAcount(account , amount);
				}else {
					throw new WithdrawalOperationException();
				}
		}


		return null;


	}

	public Account withdrawAcount(Account account, double amount){
		account.setBalance(account.getBalance() - amount);
		saveTransactionInAccount(account, createTransactionByOperation(amount , Operation.WITHDRAWAL));
		return account;
	}



	public Account depositeAcount(Account account, double amount) {
		account.setBalance(account.getBalance() + amount);
		saveTransactionInAccount(account, createTransactionByOperation(amount , Operation.DEPOSIT));
		return account;
	}

	public Transaction createTransactionByOperation(double amount, Operation operation){
		return Transaction.builder()
				.amount(amount)
				.operation(operation)
				.build();
	}
	public AccountHistory listTransactionAccount(Long accountIdent){
		Optional<Account> account = accountRepositoy.findAccount(accountIdent);
		if (account.isPresent()){
			if(account.get().getTransactions().isEmpty()){
				throw new NoOperationFoundException();
			}
			AccountHistory accountHistory = accountHistoryMapper.entityToApi(account.get());
			return accountHistory;
		}else {
			throw new AccountNotFoundException();
		}
	}

	public AccountRepositoy getAccountRepositoy() {
		return accountRepositoy;
	}





	@Autowired
	public void setAccountRepositoy(AccountRepositoy accountRepositoy) {
		this.accountRepositoy = accountRepositoy;
	}


	private final  AccountHistoryMapper accountHistoryMapper = Mappers.getMapper(AccountHistoryMapper.class);

}
