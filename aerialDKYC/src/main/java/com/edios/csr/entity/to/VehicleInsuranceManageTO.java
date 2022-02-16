package com.edios.csr.entity.to;

import java.io.Serializable;
import java.util.Date;

import com.edios.csr.entity.LoanEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VehicleInsuranceManageTO implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;

	private Long inquiryId;
	private String inquiryType;
	private String firstName;
	private String lastName;
	private String inquiryNo;
	private String customerName;
	private String contactNumber;
	private Long vehicleId;
	private String vehicleEngineNo;
	private String vehicleChassisNo;
	private String policyNo;
	private String insuranceSource;
	private Long vehicleInsuranceId;
	private Long customerId;
	private String signatureKey;
	private String modeOfPayment;

	private Date policyIssueFromDate;
	private Date policyIssueToDate;
	
	private Long companyBankId;
	
	private String modeOfDispatch;


	private LoanEntity loanId;

	private Date insuranceDate;
	
	private Date policyIssueDate;
	
	
	private Double insuranceAmount;
	
	private String insurancePaymentPaidBy;

	private Date insurancePaymentPaidDate;
	
	private String dispatchNo;

	private Date dispatchDate;

	private Long insuranceCompanyId;
	
	private String insuranceCompanyValue;

	private String insuranceStatus;

	private String notes;
	
	private Long transactionCount;
	
	private Long customerAddId;

	private Long makeId;
	private String previousOwner;
	
	private String insCustomerName;
	private String ignoreFlag;

}
