package com.kata.controller;

import com.kata.dto.AccountHistory;
import com.kata.entity.Account;
import com.kata.entity.Transaction;
import com.kata.enums.Operation;
import com.kata.services.AccountBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("bank")
public class BankController {

	private AccountBankService accountBankService;


    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public void deposit(@Valid @RequestBody BankRequest bankRequest)  {
        accountBankService.operation(Operation.DEPOSIT, bankRequest.amount(), Long.valueOf(bankRequest.accountIdent()));
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.CREATED)
    public void withdraw(@Valid @RequestBody BankRequest bankRequest)  {
        accountBankService.operation(Operation.WITHDRAWAL, bankRequest.amount(),Long.valueOf(bankRequest.accountIdent()));
    }

    @GetMapping("/history/{accountId}")
    public AccountHistory getHistory(@PathVariable @Positive int accountId){
       return accountBankService.listTransactionAccount(Long.valueOf(accountId));
    }










    public AccountBankService getAccountBankService() {
        return accountBankService;
    }

    @Autowired
    public void setAccountBankService(AccountBankService accountBankService) {
        this.accountBankService = accountBankService;
    }
}
