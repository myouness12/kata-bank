package com.kata.dto;

import com.kata.enums.Operation;
import lombok.*;

import javax.persistence.Entity;
import java.util.Date;
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
