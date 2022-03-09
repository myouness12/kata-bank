package com.kata.dto;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountHistory {
    private double balance;
    private Set<TransactionDto> listTransaction;
}
