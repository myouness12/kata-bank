package com.kata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.controller.BankRequest;
import com.kata.dto.AccountHistory;
import com.kata.dto.ResultOperation;
import com.kata.dto.TransactionDto;
import com.kata.enums.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankApplicationTests {

	@Autowired
	private WebTestClient webTestClient;


	@Test
	void whenPostRequestDepositThenCreated() {
	var expected = new BankRequest(1,300D);

		webTestClient
				.post()
				.uri("/bank/deposit")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(expected)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(ResultOperation.class).value(resultOperation -> {
					assertThat(resultOperation).isNotNull();
					assertThat(resultOperation.getBalance()).isEqualTo(expected.amount());
					assertThat(resultOperation.getTransaction().getOperation()).isEqualTo(Operation.DEPOSIT);
					assertThat(resultOperation.getTransaction().getAmount()).isEqualTo(300D);
				});
	}

	@Test
	void whenPostRequestWithdrawThenCreated() {
		var expected = new BankRequest(1,300D);
		// Deposit First
		webTestClient
				.post()
				.uri("/bank/deposit")
				.bodyValue(expected)
				.exchange()
				.expectStatus().isCreated();


		webTestClient
				.post()
				.uri("/bank/withdraw")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(expected)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(ResultOperation.class).value(resultOperation -> {
					assertThat(resultOperation).isNotNull();
					assertThat(resultOperation.getBalance()).isZero();
					assertThat(resultOperation.getTransaction().getOperation()).isEqualTo(Operation.WITHDRAWAL);
					assertThat(resultOperation.getTransaction().getAmount()).isEqualTo(300D);
				});
	}


	@Test
	void whenGetRequestHistoryThenAcountHistoryReturned() {
		var expected = new BankRequest(1,300D);

		webTestClient
				.post()
				.uri("/bank/deposit")
				.bodyValue(expected)
				.exchange()
				.expectStatus().isCreated();

		webTestClient
				.post()
				.uri("/bank/deposit")
				.bodyValue(expected)
				.exchange()
				.expectStatus().isCreated();

		webTestClient
				.post()
				.uri("/bank/withdraw")
				.bodyValue(expected)
				.exchange()
				.expectStatus().isCreated();


		// 2 operation type deposit sum amount = 600D
		// 1 operation type withdraw sum amount = 300D
		webTestClient
				.get()
				.uri("/bank/history/1")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(AccountHistory.class).value(history -> {
					assertThat(history).isNotNull();
					assertThat(history.getBalance()).isEqualTo(300);
					assertThat(history.getListTransaction().stream().filter(tx -> Operation.DEPOSIT.equals(tx.getOperation())).count())
							.isEqualTo(2);
					assertThat(history.getListTransaction().stream().filter(tx -> Operation.WITHDRAWAL.equals(tx.getOperation())).count())
							.isEqualTo(1);
					assertThat(history.getListTransaction().stream().filter(tx ->
								Operation.WITHDRAWAL.equals(tx.getOperation()))
								.mapToDouble(TransactionDto::getAmount).sum())
							.isEqualTo(300D);
					assertThat(history.getListTransaction().stream().filter(tx ->
									Operation.DEPOSIT.equals(tx.getOperation()))
							.mapToDouble(TransactionDto::getAmount).sum())
							.isEqualTo(600D);
				});

	}
// {"balance":300.0,"listTransaction":[{"operation":"DEPOSIT","amount":300.0,"dateOperation":"09/03/2022 07:00:13"},{"operation":"WITHDRAWAL","amount":300.0,"dateOperation":"09/03/2022 07:00:13"},{"operation":"DEPOSIT","amount":300.0,"dateOperation":"09/03/2022 07:00:13"}]}


}
