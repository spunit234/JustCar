
package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.csr.entity.CustomerEntity;
import com.edios.csr.entity.InquiriesEntity;
import com.edios.csr.entity.LoanEntity;
import com.edios.csr.entity.VehicelsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VehicleInsuranceBean extends AbstractBean{

	private static final long serialVersionUID = 1L;

	private Long vehicleInsuranceId;
	
	private String modeOfPayment;


	private CustomerEntity customerId;

	private VehicelsEntity vehicleId;

	private InquiriesEntity inquiryId;

	private LoanEntity loanId;

	private Date insuranceDate;

	private String policyNo;
	
	private Date policyIssueDate;
	
	private String insuranceSource;
	
	private Double insuranceAmount;
	
	private String insurancePaymentPaidBy;

	private Date insurancePaymentPaidDate;
	
	private String dispatchNo;

	private Date dispatchDate;
	
	private Long companyBankId;
	
	private String modeOfDispatch;

	private Long insuranceCompanyId;

	private String insuranceStatus;

	private String notes;
	
	private Long transactionCount;

	private String ipAddress;

	private char recordType;

	private Long createdBy;

	private Date createdDate;

	private Long lastModifiedBy;

	private Date lastModifiedDate;
	
	
	private Long vehicleInsuranceExistingId;
	
	private String insuranceExistingPolicyNo;
	private Long insuranceExistingInsurCompId;
	private Long insuranceExistingCustId;
	private String vehicleChassisNo;
	private String vehicleEngineNo;
	private String insCustomerName;
	private String contactNumber;
	private String ignoreFlag;

	
}

	

