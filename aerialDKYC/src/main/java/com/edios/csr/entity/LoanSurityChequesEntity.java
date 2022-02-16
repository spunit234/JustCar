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
@Table(name = "loan_surity_cheques")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanSurityChequesEntity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOAN_CHEQUE_ID")
	private Long loanChequeId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LOAN_ID")
	private LoanEntity loanId;

	@Column(name = "CHEQUE_NO")
	private String chequeNo;

	@Column(name = "CHEQUE_DATE")
	private Date chequeDate;

	@Column(name = "BANK_NAME_ID")
	private Long bankName;

	@Column(name = "AMOUNT")
	private Double amount;

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

	@Column(name = "CHEQUE_RECEIVED")
	private String chequeReceived;

	@Column(name = "CHEQUE_TYPE")
	private String chequeType;

	@Column(name = "IFSC_CODE")
	private String ifscCode;

	@Column(name = "ACCOUNT_NO")
	private String accountNo;

	@Column(name = "ACCOUNT_TYPE")
	private String accountType;
	
	@Column(name = "REASON_NOTES")
	private String reasonNotes;
}
