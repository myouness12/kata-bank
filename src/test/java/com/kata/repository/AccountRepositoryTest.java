package com.kata.repository;

import com.kata.entity.Account;
import com.kata.entity.Transaction;
import com.kata.enums.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@DataJpaTest
@Transactional(propagation = SUPPORTS)
class AccountRepositoryTest {

    @Autowired
    private AccountRepositoy accountRepositoy;

    @Test
    void findAccountWhenExisting() {
        Long accountId = 1L;
        boolean existing = accountRepositoy.existsById(accountId);
        assertThat(existing).isTrue();
    }

    @Test
     void findAccountByCustomerWhenExisting() {
        Long customerId = 1L;
        Optional<Account> account = accountRepositoy.findAccountByCustomer(customerId);
        assertThat(account).isPresent();
    }


    @Test
    void updateBalanceAccount(){
        var expected = 200D;
        var customerId = 1L;
        accountRepositoy.updateBalanceAccount(expected, customerId);
        accountRepositoy.findAccountByCustomer(customerId);
        assertEquals(expected, accountRepositoy.findAccountByCustomer(customerId).get().getBalance());
    }

    @Test
    void saveTransactionAccount(){
        var customerId = 1L;
        Transaction tx = Transaction.builder().amount(300D)
                                    .dateOperation(LocalDateTime.now())
                                    .operation(Operation.DEPOSIT)
                                    .build();
        Account account = accountRepositoy.findAccountByCustomer(customerId).get();
        account.getTransactions().add(tx);
        accountRepositoy.save(account);
        Set<Transaction> listactualTransaction = accountRepositoy.findAccount(customerId).get().getTransactions();
        assertThat(listactualTransaction).hasSize(1);
    }



    //SELECT sum(AMOUNT ), OPERATION_TYPE  FROM B_TRANSACTION group by OPERATION_TYPE
    //select * from B_ACCOUNT  700,78








}
