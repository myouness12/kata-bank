package com.kata.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BankRequestValidationTest {

     private static Validator validator;

    @BeforeAll
    static void setUp() {
      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
      var bankRequest = new BankRequest(657865, 10.56);
      Set<ConstraintViolation<BankRequest>> violations = validator.validate(bankRequest);
      assertThat(violations).isEmpty();
    }

    @Test
    void whenReferenceNotDefinedThenValidationFails() {
        var bankRequest = new BankRequest(null, 11.55);
        Set<ConstraintViolation<BankRequest>> violations = validator.validate(bankRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The reference account must be defined.");
    }

    @Test
    void whenAmountIsNotDefinedThenValidationFails() {
        var bankRequest = new BankRequest(76897, null);
        Set<ConstraintViolation<BankRequest>> violations = validator.validate(bankRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The amount must be defined.");
    }

    @Test
    void whenAmountIsLowerThanMinThenValidationFails() {
        var bankRequest = new BankRequest(98076, 0.0);
        Set<ConstraintViolation<BankRequest>> violations = validator.validate(bankRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The minimum value allowed 0,01");
    }





}
