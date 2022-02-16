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
public class InsurancePaymentsBean extends AbstractBean{

	private static final long serialVersionUID = 1L;

	private Long insurancePaymentId;

	private VehicleInsuranceBean vehicleInsuranceId;
	
	private Date insurancePaymentDate;
	
	private Double insurancePaymentAmount;
	
	private String insuranceModePayment;
	
	private Long companyBankId;
	
	private Long transactionCount;

	private String ipAddress;

	private char recordType;

	private Long createdBy;

	private Date createdDate;

	private Long lastModifiedBy;

	private Date lastModifiedDate;
	
	private String insurancePaymentDateStr;

	
}


