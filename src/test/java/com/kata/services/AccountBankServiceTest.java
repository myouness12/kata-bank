package com.kata.services;

import com.kata.entity.Account;
import com.kata.entity.Transaction;
import com.kata.enums.Operation;
import com.kata.exception.AccountNotFoundException;
import com.kata.exception.DepositeOperationException;
import com.kata.exception.NoOperationFoundException;
import com.kata.exception.WithdrawalOperationException;
import com.kata.repository.AccountRepositoy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Repeat;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class AccountBankServiceTest {


    static final long CUSTOMER_IDENT = 1L;
    static final long ACCOUNT_IDENT = 1L;
    @Mock
    private AccountRepositoy accountRepositoy;

    @InjectMocks
    private AccountBankService accountBankService;

    private Account account;
  //  private Transaction tx;

    @BeforeEach
    void setUp(){
        account = Account.builder().account_ident(CUSTOMER_IDENT).balance(0D).transactions(new HashSet<>(0)).build();
       //  tx = Transaction.builder().amount(0D).build();

    }

    @Test
    void whenAccountNotFoundThenThrows(){
          when(accountRepositoy.findAccountByCustomer(CUSTOMER_IDENT)).thenReturn(Optional.empty());
          assertThatThrownBy(() -> accountBankService.findAccountByCustomer(CUSTOMER_IDENT))
                  .isInstanceOf(AccountNotFoundException.class)
                  .hasMessage(AccountNotFoundException.ACCOUNT_NOT_FOUND);
    }

    @Test
    void whenDepositeLessThanMinValueThenThrows(){
        var minDeposit = 0D;
        when(accountRepositoy.findAccountByCustomer(CUSTOMER_IDENT)).thenReturn(Optional.ofNullable(account));
        assertThatThrownBy(() -> accountBankService.operation(Operation.DEPOSIT, minDeposit,  account.getAccount_ident())  )
                .isInstanceOf(DepositeOperationException.class)
                .hasMessage(DepositeOperationException.OPERATION_REFESUD);
    }

    @Test
    void operationDepositeAccount(){
        var actualBalance = 200D;
        var amount = 100D;
        var expectedBalance = actualBalance + amount;
        account.setBalance(actualBalance);
        when(accountRepositoy.save(account)).thenReturn(account);
        assertThat(accountBankService.depositeAcount(account, amount).getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    void operationWithOperationTypeDEPOSIT(){
        var actualBalance = 200D;
        var amount = 100D;
        var expectedBalance = actualBalance + amount;
        account.setBalance(actualBalance);
        when(accountRepositoy.findAccountByCustomer(CUSTOMER_IDENT)).thenReturn(Optional.ofNullable(account));
        when(accountRepositoy.save(account)).thenReturn(account);
        assertThat(accountBankService.operation(Operation.DEPOSIT ,amount, account.getAccount_ident()).getBalance()).isEqualTo(expectedBalance);
    }



    @Test
    void whenBalanceGreatThanAmountThenThrows(){
        var balance = 0D;
        var amount = 50D;
        account.setBalance(balance);
        when(accountRepositoy.findAccountByCustomer(CUSTOMER_IDENT)).thenReturn(Optional.of(account));
        assertThatThrownBy(() -> accountBankService.operation(Operation.WITHDRAWAL, amount,  account.getAccount_ident())  )
                .isInstanceOf(WithdrawalOperationException.class)
                .hasMessage(WithdrawalOperationException.INSUFFICIENT_CREDIT);
    }


    @Test
    void operationWithdrawAccount(){
        var actualBalance = 200D;
        var amount = 100D;
        var expectedBalance = actualBalance - amount;
        account.setBalance(actualBalance);
        when(accountRepositoy.save(account)).thenReturn(account);
        assertThat(accountBankService.withdrawAcount(account, amount).getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    void operationWithOperationTypeWITHDRAWAL(){
        var actualBalance = 200D;
        var amount = new Random().doubles(0,199).findAny().getAsDouble();
        var expectedBalance = actualBalance - amount;
        account.setBalance(actualBalance);
        when(accountRepositoy.findAccountByCustomer(CUSTOMER_IDENT)).thenReturn(Optional.ofNullable(account));
        when(accountRepositoy.save(account)).thenReturn(account);
        assertThat(accountBankService.operation(Operation.WITHDRAWAL ,amount, account.getAccount_ident()).getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    void whenNoOperationFoundThenThrows(){
        when(accountRepositoy.findAccount(ACCOUNT_IDENT)).thenReturn(Optional.of(account));
        assertThatThrownBy(() -> accountBankService.listTransactionAccount(ACCOUNT_IDENT)  )
                .isInstanceOf(NoOperationFoundException.class)
                .hasMessage(NoOperationFoundException.NO_OPERATION_FOUND);
    }

    @Test
    void whenNoAccountFondThenThrows(){
        when(accountRepositoy.findAccount(ACCOUNT_IDENT)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> accountBankService.listTransactionAccount(ACCOUNT_IDENT)  )
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage(AccountNotFoundException.ACCOUNT_NOT_FOUND);
    }

    @Test
    void listOfTransactionAccount() {
        Transaction tx = Transaction.builder().amount(0D).build();
        Set<Transaction> listTx = new HashSet<>(Arrays.asList(tx));
        account.setTransactions(listTx);
        when(accountRepositoy.findAccount(ACCOUNT_IDENT)).thenReturn(Optional.of(account));
        assertThat(accountBankService.listTransactionAccount(ACCOUNT_IDENT).getListTransaction()).hasSize(1);
    }



}