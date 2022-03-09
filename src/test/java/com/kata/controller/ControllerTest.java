package com.kata.controller;

import com.kata.exception.AccountNotFoundException;
import com.kata.exception.NoOperationFoundException;
import com.kata.services.AccountBankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ControllerTest {

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
