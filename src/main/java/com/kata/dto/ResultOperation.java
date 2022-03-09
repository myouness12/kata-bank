package com.kata.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultOperation {
    private double balance;
    private TransactionDto transaction;
}
