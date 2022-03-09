package com.kata.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "B_ACCOUNT")
public class Account implements Serializable {

	@Column(name = "ACCOUNT_IDENT", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long accountIdent;
	
	
	@Column(name="BALANCE", nullable=true)
	private Double balance;

	@OneToMany(targetEntity = Transaction.class    , cascade = {CascadeType.MERGE , CascadeType.PERSIST})
	@JoinColumn(name = "T_ACCOUNT_IDENT_FK")
	private java.util.Set<Transaction> transactions = new java.util.HashSet<>();


	@OneToOne(mappedBy = "account"  , cascade = {CascadeType.MERGE , CascadeType.PERSIST})
	private  Customer customer;




	
	
}
