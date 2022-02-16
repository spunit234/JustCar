package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.csr.entity.InquiriesEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggregatorsBean extends AbstractBean {
	
	private Long aggregatorId;

	private InquiriesEntity inquiryId;
	
	private Date aggregatorDate;
	
	private Double amountOnHold;
	
	private Double  amountDeductedByRTO;
	
	private String larStatus;
	
	private Date larBankDate;
	
	private String executiveName;
	
	private String executivePhone;
	
	private String aggregatorStatus;
	
	private Integer transactionCount;
	
	private String ipAddress;
	
	private char recordType;
	
	private int createdBy;
	
	private Date createdDate;
	
	private int lastModifiedBy;
	
	private Date lastModifiedDate;
	
	private Double netDisbursed;
	
	private String loanBankName;
	
	private Long vehicleId;
	
	private Long loanId;

	private Long vehicleInsuranceId;
	private String inquiryType;

	private Double amountReceivedFromBank;
	private Double amountDisbToCustomer;
	private Double pendingAmountToDist;
	private String bankName;
	private String customerBankName;
	private String aggregatorType;

}

