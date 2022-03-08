package com.kata.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;


@Entity
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
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
	


	public Long getCustomer_ident() {
		return customer_ident;
	}


	public String getNom() {
		return nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public java.util.Date getPhy_date_naissance() {
		return phy_date_naissance;
	}


	public void setCustomer_ident(Long customer_ident) {
		this.customer_ident = customer_ident;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public void setPhy_date_naissance(java.util.Date phy_date_naissance) {
		this.phy_date_naissance = phy_date_naissance;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
