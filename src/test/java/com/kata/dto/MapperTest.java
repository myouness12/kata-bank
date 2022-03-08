package com.kata.dto;

import com.kata.entity.Account;
import com.kata.entity.Transaction;
import com.kata.enums.Operation;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MapperTest {
    private AccountHistoryMapper mapper = Mappers.getMapper(AccountHistoryMapper.class);


    @Test
    void mapperTest(){
        assertNotNull(mapper);
        Transaction txSource = Transaction.builder().operation(Operation.DEPOSIT)
                         .amount(200D)
                         .dateOperation(LocalDateTime.now())
                         .operation(Operation.DEPOSIT).build();
        Account account = Account.builder().balance(200D)
                .transactions(new HashSet<>(Arrays.asList(txSource)))
                .build();

        AccountHistory history = mapper.entityToApi(account);
        TransactionDto transactionDto = history.getListTransaction().stream().findFirst().get();

        assertEquals(history.getListTransaction().size(), account.getTransactions().size());
        assertEquals(history.getBalance(), account.getBalance());
        assertEquals(transactionDto.getOperation(), txSource.getOperation());
        assertEquals(transactionDto.getAmount(), txSource.getAmount());
        assertEquals(transactionDto.getDateOperation(), txSource.getDateOperation());
    }



}
