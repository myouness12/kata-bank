package com.kata.dto;

import com.kata.entity.Account;
import com.kata.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.Set;

@Mapper
public interface AccountHistoryMapper {
    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "transactions" , target ="listTransaction")
    AccountHistory accountToAccountHistory(Account account);


    Set<AccountHistory> entityListToApiList(Set<Transaction> listTx);


    default ResultOperation accountToResultOperation(Account account){
       Transaction tx = account.getTransactions().stream()
                .max(Comparator.comparing(Transaction::getTransIdent)).get();

        return ResultOperation.builder()
                .balance(account.getBalance())
                .transaction(TransactionDto.builder().operation(tx.getOperation())
                 .dateOperation(tx.getDateOperation()).amount(tx.getAmount()).build())
                .build();
    }


}
