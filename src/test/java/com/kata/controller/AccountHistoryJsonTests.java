package com.kata.controller;


import com.kata.dto.AccountHistory;
import com.kata.dto.TransactionDto;
import com.kata.enums.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@MockBean(JpaMetamodelMappingContext.class)
class AccountHistoryJsonTests {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Autowired
    private JacksonTester<AccountHistory> json;

    @Test
    void testSerialize() throws Exception {
        var transactionDto = TransactionDto.builder()
                .amount(300.33).dateOperation(LocalDateTime.now())
                .operation(Operation.DEPOSIT)
                .build();
        var accountHistory = AccountHistory.builder()
                .balance(300.33)
                .listTransaction(new HashSet<>(Arrays.asList(transactionDto))).build();
        var jsonContent = json.write(accountHistory);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.balance")
                .isEqualTo(accountHistory.getBalance());
        Set<TransactionDto> listTransaction = accountHistory.getListTransaction();

        for (int i = 0; i < listTransaction.size(); i++) {
            assertThat(jsonContent).extractingJsonPathNumberValue("@.listTransaction["+i+"].amount")
                    .isEqualTo(transactionDto.getAmount());
            assertThat(jsonContent).extractingJsonPathStringValue("@.listTransaction["+i+"].operation")
                    .isEqualTo(transactionDto.getOperation().name());
            assertThat(jsonContent).extractingJsonPathStringValue("@.listTransaction["+i+"].dateOperation")
                    .isEqualTo(transactionDto.getDateOperation().format(formatter));
        }

        //{"balance":300.0,"listTransaction":[{"operation":"DEPOSIT","amount":300.0,"dateOperation":"08/03/2022 02:56:15"}]}

    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
                {
                    "balance": 300.33,
                    "listTransaction":[{
                        "operation":"DEPOSIT",
                        "amount":300.33,
                        "dateOperation":"08/03/2022 02:56:15"
                    }]
                }
                """;

        LocalDateTime dateTime = LocalDateTime.parse("08/03/2022 02:56:15" , formatter);
        var transactionDto = TransactionDto.builder()
                .amount(300.33).dateOperation(dateTime)
                .operation(Operation.DEPOSIT)
                .build();
        var accountHistory = AccountHistory.builder()
                .balance(300.33)
                .listTransaction(new HashSet<>(Arrays.asList(transactionDto))).build();

        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(accountHistory);

    }

}
