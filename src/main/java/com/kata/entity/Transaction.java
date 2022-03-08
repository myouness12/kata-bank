package com.kata.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.kata.enums.Operation;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "B_TRANSACTION")
@EntityListeners(AuditingEntityListener.class)
public class Transaction  implements Serializable {


	@Column(name = "TRANS_IDENT", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long trans_ident;


	
	@Column(name="AMOUNT", nullable=false)
	private Double amount;
	
	@Column(name = "DATE_OPERATION", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime dateOperation;


	
	@Column(name = "OPERATION_TYPE", nullable = false, length = 255)
	@Enumerated(EnumType.STRING)
	private Operation operation;
















}
