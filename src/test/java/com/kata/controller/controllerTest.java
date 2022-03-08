package com.kata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.entity.Account;
import com.kata.enums.Operation;
import com.kata.exception.AccountNotFoundException;
import com.kata.exception.DepositeOperationException;
import com.kata.exception.NoOperationFoundException;
import com.kata.repository.AccountRepositoy;
import com.kata.services.AccountBankService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class controllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountBankService accountBankService;



    @Test
    void whenHistoryNotExistingThenShouldReturn404() throws Exception {
        given(accountBankService.listTransactionAccount(1L)).willThrow(NoOperationFoundException.class);
        mockMvc
                .perform(get("/bank/history/" + 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenAccountNotExistingThenShouldReturn404() throws Exception {
        given(accountBankService.listTransactionAccount(1L)).willThrow(AccountNotFoundException.class);
        mockMvc
                .perform(get("/bank/history/" + 1L))
                .andExpect(status().isNotFound());
    }




}
