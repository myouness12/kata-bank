package com.kata.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "B_CUSTOMER")
public class Customer implements Serializable {
	
	@Column(name = "CUSTOMER_IDENT", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long customer_ident;
	

	@Column(name = "NOM", nullable = false, length = 255)
	private String nom;
	
	@Column(name = "PRENOM", nullable = false, length = 255)
	private String prenom;
	
	
	@Column(name = "DATE_NAISSANCE", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date phy_date_naissance;


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_FK")
	private Account account;
	


}
