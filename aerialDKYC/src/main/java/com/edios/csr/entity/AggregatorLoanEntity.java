package com.edios.csr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edios.cdf.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "aggregator_loans")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggregatorLoanEntity extends AbstractEntity implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "AGGREGATOR_LOAN_ID")
		private Long aggregatorLoanId;
		
		@Column(name = "AGGREGATOR_ID")
		private Long aggregatorId;
		
		@Column(name = "LOAN_NO")
		private String loanNo;
		
		@Column(name = "LOAN_CLASS")
		private Long loanClass;

		@Column(name = "LOAN_TYPE")
		private String loanType;    
	
		@Column(name = "NET_DISBURSED")
		private Double netDisbursed;
		
		@Column(name = "LOAN_START_DATE")
		private Date loanStartDate;

		@Column(name = "LOAN_END_DATE")
		private Date loanEndDate;

		@Column(name = "LOAN_DURATION")
		private Double loanDuration;
		
		@Column(name = "LOAN_AMOUNT")
		private Double loanAmount;

		@Column(name = "LOAN_INSTALLMENT")
		private Double loanInstallment;

		@Column(name = "LOAN_RO_INETEREST")
		private Double loanRoInterest;

		@Column(name = "ADVANCE_AMOUNT")
		private Double advanceAmount;

		@Column(name = "PROCESSING_FEE")
		private Double processingFee;

		@Column(name = "DISBURSED_DATE")
		private Date amountDisbursedDate;

		@Column(name = "APPROVED_AMOUNT")
		private Double approvedAmount;

		@Column(name = "LOAN_BANK")
		private String loanBank;
	
		@Column(name = "TRANSACTION_COUNT")
		private Long transactionCount;
		
		@Column(name = "IP_ADDRESS")
		private String ipAddress;
		
		@Column(name = "RECORD_TYPE")
		private Character recordType;
		
		@Column(name = "CREATED_BY")
		private Long createdBy;
		
		@Column(name = "CREATED_DATE")
		private Date createdDate;
		
		@Column(name = "LAST_MODIFIED_BY")
		private Long lastModifiedBy;
		
		@Column(name = "LAST_MODIFIED_DATE")
		private Date lastModifiedDate;
	}



