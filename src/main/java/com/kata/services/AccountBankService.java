package com.kata.services;

import com.kata.dto.AccountHistory;
import com.kata.dto.AccountHistoryMapper;
import com.kata.dto.ResultOperation;
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

import java.util.Optional;


@Service
 public class AccountBankService implements AccountService {


	static final Double MIN_DEPOSIT = 0.01;

	private  AccountRepositoy accountRepositoy;

	@Transactional(readOnly = true)
	public Account findAccountByCustomer(Long customer) {
		return accountRepositoy.findAccountByCustomer(customer).orElseThrow(AccountNotFoundException::new);
	}

	public ResultOperation operation(Operation operationType, double amount, Long customerIdent) {
		Account account = accountRepositoy.findAccountByCustomer(customerIdent).orElseThrow(AccountNotFoundException::new);
		switch (operationType) {
			case DEPOSIT:
				if(amount > MIN_DEPOSIT) {
					return appliedOperationAccount(account, amount, operationType);
				}else {
					throw new DepositeOperationException();
				}
			case WITHDRAWAL:
				if (account.getBalance() >= amount){
					return  appliedOperationAccount(account , amount, operationType);
				}else {
					throw new WithdrawalOperationException();
				}
		}
		return null;


	}

	public ResultOperation appliedOperationAccount(Account account, double amount, Operation operationType){
		if(Operation.WITHDRAWAL.equals(operationType)){
			account.setBalance(account.getBalance() - amount);
		}else if(Operation.DEPOSIT.equals(operationType)){
			account.setBalance(account.getBalance() + amount);
		}
		account.getTransactions().add(createTransactionByOperation(amount , operationType));
		accountRepositoy.save(account);
		return mapper.accountToResultOperation(account);
	}



	public Transaction createTransactionByOperation(double amount, Operation operation){
		return Transaction.builder()
				.amount(amount)
				.operation(operation)
				.build();
	}
	public AccountHistory listTransactionAccount(Long accountIdent){
		Optional<Account> account = accountRepositoy.findAccountAndListTransaction(accountIdent);
		if (account.isPresent()){
			if(account.get().getTransactions().isEmpty()){
				throw new NoOperationFoundException();
			}
			return mapper.accountToAccountHistory(account.get());
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


	private final  AccountHistoryMapper mapper = Mappers.getMapper(AccountHistoryMapper.class);

}
