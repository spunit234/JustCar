package com.edios.csr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edios.cdf.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loan_commission")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class LoanCommissionEntity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMMISSION_ID")
	private Long commissionId;

	@Column(name = "LOAN_ID")
	private Long loanId;

	@Column(name = "STAFF_ID")
	private Long paybleTo;

	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;

	@Column(name = "TRANSACTION_DATE")
	private Date transactionDate;

	@Column(name = "COMMISSION_TYPE")
	private String commissionType;

	@Column(name = "PAYMENT_MODE")
	private String paymentMode;

	@Column(name = "PAYMENT_TYPE")
	private String paymentType;

	@Column(name = "TRANSACTION_PERCENTAGE")
	private Double transactionPercentage;

	@Column(name = "TRANSACTION_VALUE")
	private Double transactionValue;

	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "IFSC_CODE")
	private String ifscCode;

	@Column(name = "ACCOUNT_NO")
	private String accountNo;

	@Column(name = "ACCOUNT_TYPE")
	private String accountType;

	@Column(name = "NOTES")
	private String notes;

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
