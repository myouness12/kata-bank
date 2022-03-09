package com.kata.entity;

import com.kata.enums.Operation;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
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
	private Long transIdent;


	
	@Column(name="AMOUNT", nullable=false)
	private Double amount;
	
	@Column(name = "DATE_OPERATION", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime dateOperation;


	
	@Column(name = "OPERATION_TYPE", nullable = false, length = 255)
	@Enumerated(EnumType.STRING)
	private Operation operation;
















}
