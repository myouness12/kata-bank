package com.kata.dto;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.kata.enums.Operation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {

	private Operation operation;

	private double amount;

	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss" , timezone="Europe/Paris")
	private LocalDateTime dateOperation;


}
