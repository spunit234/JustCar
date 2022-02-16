package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggregatorLoanBean extends AbstractBean {

	private static final long serialVersionUID = 6342094965674122373L;
	private Long aggregatorLoanId;
	private Long aggregatorId;
	private String loanNo;
	private Long loanClass;
	private String loanType;    
	private Double netDisbursed;
	private Date loanStartDate;
	private Date loanEndDate;
	private Double loanDuration;
	private Double loanAmount;
	private Double loanInstallment;
	private Double loanRoInterest;
	private Double advanceAmount;
	private Double processingFee;
	private Date amountDisbursedDate;
	private Double approvedAmount;
	private String loanBank;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	private String loanClassName;

}
