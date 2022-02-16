
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

import org.hibernate.annotations.DynamicUpdate;

import com.edios.cdf.entity.AbstractEntity;
import com.edios.csr.bean.AggregatorPaymentsBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@DynamicUpdate
@Table(name = "aggregator_payments")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggregatorPaymentsEntity extends AbstractEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AGGREGATOR_PAYMENT_ID")
	private Long aggregatorPaymentId;
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "AGGREGATOR_ID")
	private AggregatorsEntity aggregatorId;
	
	
	
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	
	@Column(name = "APPLICATION_NO")
	private String  applicationNo;
	
	@Column(name = "TRANSACTION_NO")
	private String transactionNo;
	
	@Column(name = "TRANSACTION_DATE")
	private Date transactionDate;
	
	@Column(name = "TRANSACTION_MODE")
	private String transactionMode;
	
	@Column(name = "TRANSACTION_AMOUNT")
	private Double transactionAmount;
	
	@Column(name = "COMPANY_BANK_ID")
	private Long companyBankId;
	
	@Column(name = "CUSTOMER_BANK_ID")
	private Long customerbankId;
	
	@Column(name = "NOTES")
	private String notes;
	
	@Column(name = "TRANSACTION_COUNT")
	private int transactionCount;
	
	@Column(name = "IP_ADDRESS")
	private String ipAddress;
	
	@Column(name = "RECORD_TYPE")
	private char recordType;
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "LAST_MODIFIED_BY")
	private int lastModifiedBy;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Date lastModifiedDate;

	public void setUpdatedDetails(AggregatorPaymentsBean payment) {
		this.transactionDate = payment.getTransactionDate();
		this.transactionNo = payment.getTransactionNo();
		this.applicationNo = payment.getApplicationNo();
		this.paymentType = payment.getPaymentType();
		this.transactionMode = payment.getTransactionMode();
		this.companyBankId = payment.getCompanyBankId();
		this.transactionAmount = payment.getTransactionAmount();
		this.notes = payment.getNotes();
	}
	
	
	
	
	
	
	

}
