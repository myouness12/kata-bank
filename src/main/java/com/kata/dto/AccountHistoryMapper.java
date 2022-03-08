package com.kata.dto;

import com.kata.entity.Account;
import com.kata.entity.Transaction;
import com.kata.enums.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper
public interface AccountHistoryMapper {
    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "transactions" , target ="listTransaction")
    AccountHistory entityToApi(Account account);


    Set<AccountHistory> entityListToApiList(Set<Transaction> listTx);

}
