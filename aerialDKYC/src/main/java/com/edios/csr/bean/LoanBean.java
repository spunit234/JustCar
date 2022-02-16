package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.csr.entity.CustomerEntity;
import com.edios.csr.entity.InquiriesEntity;
import com.edios.csr.entity.VehicelsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanBean extends AbstractBean {

	private static final long serialVersionUID = 6342094965674122373L;

	private Long loanId;
	private InquiriesEntity inquiryId;
	private CustomerEntity customerId;
	private VehicelsEntity vehicleId;
	private String loanNo;
	private Long loanClass;
	private Long loanType;
	private Date loanStartDate;
	private Date loanEndDate;
	private Double loanDuration;
	private Double vehicleValue;
	private Double loanAmount;
	private Double loanInstallment;
	private Double loanRoInterest;
	private Double advanceAmount;
	private Double processingFee;
	private Double gstAmount;
	private Double tdsAmount;
	private Double netReceivable;
	private Double netDisbursed;
	private Date amountDisbursedDate;
	private Double approvedAmount;
	private Long loanBank;
	private String loanCreditedTo;
	private Long loanStatus;
	private String loanNotes;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	private String vehicleType;
	private String bankLocation;
	private String bankName;
	private String accountNo;
	private String  ifscCode;
	private String accountType;
	private String loanProgram;
}
