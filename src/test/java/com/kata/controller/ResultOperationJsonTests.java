package com.kata.controller;

import com.kata.dto.AccountHistory;
import com.kata.dto.ResultOperation;
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

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@MockBean(JpaMetamodelMappingContext.class)
class ResultOperationJsonTests {

 static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


 @Autowired
 private JacksonTester<ResultOperation> json;

     @Test
     void testSerialize() throws Exception {
         var transactionDto = TransactionDto.builder()
                 .amount(300.33).dateOperation(LocalDateTime.now())
                 .operation(Operation.DEPOSIT)
                 .build();
         var resultOperation = ResultOperation.builder().balance(400D).transaction(transactionDto).build();
         var jsonContent = json.write(resultOperation);
         assertThat(jsonContent).extractingJsonPathNumberValue("@.balance")
                 .isEqualTo(resultOperation.getBalance());
         assertThat(jsonContent).extractingJsonPathNumberValue("@.transaction.amount")
              .isEqualTo(transactionDto.getAmount());
         assertThat(jsonContent).extractingJsonPathStringValue("@.transaction.dateOperation")
              .isEqualTo(transactionDto.getDateOperation().format(formatter));
         assertThat(jsonContent).extractingJsonPathStringValue("@.transaction.operation")
                 .isEqualTo(transactionDto.getOperation().name());

     }

     //JsonContent {"balance":400.0,"transaction":{"operation":"DEPOSIT","amount":300.33,"dateOperation":"09/03/2022 05:59:33"}} created from com.kata.dto.ResultOperation

     @Test
     void testDeserialize() throws Exception {
      var content = """
                    {
                        "balance": 400,
                        "transaction":{
                            "operation":"DEPOSIT",
                            "amount":300.33,
                            "dateOperation":"08/03/2022 02:56:15"
                        }
                    }
                    """;

      LocalDateTime dateTime = LocalDateTime.parse("08/03/2022 02:56:15" , formatter);
      var transactionDto = TransactionDto.builder()
              .amount(300.33).dateOperation(dateTime)
              .operation(Operation.DEPOSIT)
              .build();
      var resultOperation = ResultOperation.builder().balance(400D).transaction(transactionDto).build();


      assertThat(json.parse(content))
              .usingRecursiveComparison()
              .isEqualTo(resultOperation);

     }
}
