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
@Table(name = "loans")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanEntity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOAN_ID")
	private Long loanId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "INQUIRY_ID")
	private InquiriesEntity inquiryId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMER_ID")
	private CustomerEntity customerId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VEHICLE_ID")
	private VehicelsEntity vehicleId;
	
	@Column(name = "LOAN_NO")
	private String loanNo;

	@Column(name = "LOAN_CLASS")
	private Long loanClass;

	@Column(name = "LOAN_TYPE")
	private Long loanType;

	@Column(name = "LOAN_START_DATE")
	private Date loanStartDate;

	@Column(name = "LOAN_END_DATE")
	private Date loanEndDate;

	@Column(name = "LOAN_DURATION")
	private Double loanDuration;

	@Column(name = "VEHICLE_VALUE")
	private Double vehicleValue;

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

	@Column(name = "GST_AMOUNT")
	private Double gstAmount;

	@Column(name = "TDS_AMOUNT")
	private Double tdsAmount;

	@Column(name = "NET_RECEIVABLE")
	private Double netReceivable;

	@Column(name = "NET_DISBURSED")
	private Double netDisbursed;

	@Column(name = "AMOUNT_DISBURSED_DATE")
	private Date amountDisbursedDate;

	@Column(name = "APPROVED_AMOUNT")
	private Double approvedAmount;

	@Column(name = "LOAN_BANK_ID")
	private Long loanBank;

	@Column(name = "LOAN_CREDITED_TO")
	private String loanCreditedTo;

	@Column(name = "LOAN_STATUS")
	private Long loanStatus;

	@Column(name = "LOAN_NOTES")
	private String loanNotes;


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

	@Column(name = "VEHICLE_TYPE")
	private String vehicleType;
	
	@Column(name = "BANK_LOCATION")
	private String bankLocation;
	
	@Column(name = "BANK_NAME")
	private String bankName;
	
	@Column(name = "ACCOUNT_NO")
	private String accountNo;
	
	@Column(name = "IFSC_CODE")
	private String  ifscCode;
	
	@Column(name = "ACCOUNT_TYPE")
	private String accountType;
	
	@Column(name = "LOAN_PROGRAM")
	private String loanProgram;
}
