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
@Table(name = "B_CUSTOMER")
public class Customer implements Serializable {
	
	@Column(name = "CUSTOMER_IDENT", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long customerIdent;
	

	@Column(name = "NOM", nullable = false, length = 255)
	private String nom;
	
	@Column(name = "PRENOM", nullable = false, length = 255)
	private String prenom;
	
	
	@Column(name = "DATE_NAISSANCE", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date dateNaissance;


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_FK")
	private Account account;
	



}
