package com.kata.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BankRepository <T, ID> extends  JpaRepository<T, ID> {

}
