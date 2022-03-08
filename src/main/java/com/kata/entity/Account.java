package com.kata.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "B_ACCOUNT")
public class Account implements Serializable {

	@Column(name = "ACCOUNT_IDENT", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long account_ident;
	
	
	@Column(name="BALANCE", nullable=true)
	private Double balance;


	@OneToMany(targetEntity = Transaction.class    , cascade = {CascadeType.MERGE , CascadeType.PERSIST})
	@JoinColumns({ @JoinColumn(name = "T_ACCOUNT_IDENT_FK", nullable = true) })
	private java.util.Set<Transaction> transactions = new java.util.HashSet<>();


	@OneToOne(mappedBy = "account"  , cascade = {CascadeType.MERGE , CascadeType.PERSIST})
	private  Customer customer;




	
	
}
