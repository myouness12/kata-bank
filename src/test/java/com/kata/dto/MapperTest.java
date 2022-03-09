package com.kata.dto;

import com.kata.entity.Account;
import com.kata.entity.Transaction;
import com.kata.enums.Operation;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class MapperTest {
    private final AccountHistoryMapper mapper = Mappers.getMapper(AccountHistoryMapper.class);


    @Test
    void mapperAccountToAccountHistoryTest(){
        assertNotNull(mapper);
        Transaction txSource = Transaction.builder().operation(Operation.DEPOSIT)
                         .amount(200D)
                         .dateOperation(LocalDateTime.now())
                         .operation(Operation.DEPOSIT).build();
        Account account = Account.builder().balance(200D)
                .transactions(new HashSet<>(List.of(txSource)))
                .build();

        AccountHistory history = mapper.accountToAccountHistory(account);
        TransactionDto transactionDto = history.getListTransaction().stream().findFirst().get();

        assertEquals(history.getListTransaction().size(), account.getTransactions().size());
        assertEquals(history.getBalance(), account.getBalance());
        assertEquals(transactionDto.getOperation(), txSource.getOperation());
        assertEquals(transactionDto.getAmount(), txSource.getAmount());
        assertEquals(transactionDto.getDateOperation(), txSource.getDateOperation());
    }


     @Test
     void mapperAccountToResultOperationTest(){
         assertNotNull(mapper);
         Transaction txSourceDeposit = Transaction.builder()
                 .transIdent(1L)
                 .operation(Operation.DEPOSIT)
                 .amount(200D)
                 .dateOperation(LocalDateTime.now())
                 .operation(Operation.DEPOSIT).build();

         Transaction txSourceWithdrawal = Transaction.builder().operation(Operation.WITHDRAWAL)
                 .transIdent(2L)
                 .amount(400D)
                 .dateOperation(LocalDateTime.now())
                 .operation(Operation.WITHDRAWAL).build();

         Account account = Account.builder()
                 .balance(200D)
                 .transactions(new HashSet<>(Arrays.asList(txSourceWithdrawal, txSourceDeposit)))
                 .build();

         ResultOperation operation = mapper.accountToResultOperation(account);
         assertEquals(operation.getBalance(), account.getBalance());
         assertEquals(400D, operation.getTransaction().getAmount());
         assertEquals(Operation.WITHDRAWAL, operation.getTransaction().getOperation());
     }


}
