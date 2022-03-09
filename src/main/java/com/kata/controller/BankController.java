package com.kata.controller;

import com.kata.dto.AccountHistory;
import com.kata.dto.ResultOperation;
import com.kata.enums.Operation;
import com.kata.services.AccountBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("bank")
public class BankController {

	private AccountBankService accountBankService;


    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultOperation deposit(@Valid @RequestBody BankRequest bankRequest)  {
        return accountBankService.operation(Operation.DEPOSIT, bankRequest.amount(), Long.valueOf(bankRequest.accountIdent()));
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultOperation withdraw(@Valid @RequestBody BankRequest bankRequest)  {
        return accountBankService.operation(Operation.WITHDRAWAL, bankRequest.amount(),Long.valueOf(bankRequest.accountIdent()));
    }

    @GetMapping("/history/{accountId}")
    public AccountHistory getHistory(@PathVariable  Integer accountId){
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
