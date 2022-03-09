package com.kata.controller;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record BankRequest(
        @NotNull(message = "The reference account must be defined.")
        @Min(value = 1 , message = "Invalid reference")
        Integer accountIdent,

        @NotNull(message = "The amount must be defined.")
        @DecimalMin(value = "0.01", message = "The minimum value allowed 0,01")
        Double amount


        ) {



}
