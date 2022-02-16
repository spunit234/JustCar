package com.edios.csr.bean;

import java.util.Date;

import javax.persistence.Column;

import com.edios.cdf.bean.AbstractBean;
import com.edios.csr.entity.CustomerAddressEntity;
import com.edios.csr.entity.CustomerEntity;
import com.edios.csr.entity.VehicleInsuranceEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InsuranceCoversBean  extends AbstractBean{
		

	private static final long serialVersionUID = 1L;

	private Long insuranceNewCoverId;
	
	private Long insurancePRVCoverId;

	private Long customerId;
	
	private String policyNo;

	private VehicleInsuranceEntity vehicleInsuranceId;

	private Long insuranceCompanyId;

	private String renewalType;

	private String insuranceCoverType;
	
	private Integer addOnCoverageId;
	
	private Date insuranceValidFrom;
	
	private Date insuranceValidTo;
	
	private Double insurancePremiumAmount;

	private Double sumInsuredAmount;
	
	private String noClaimBonus;

	private String numberOfYears;
	
	private String inspectionReportNo;

	private Date inspectionDate;

	private String inspectionDoneBy;
	
	private Long transactionCount;

	private String ipAddress;

	private char recordType;

	private Long createdBy;

	private Date createdDate;

	private Long lastModifiedBy;

	private Date lastModifiedDate;
	
	private String addOnCoverageName;
	
	private String contactNumber;
	
	private String customerName;
	
	private Long customerAddressId;
	
	private String insuranceCompany;
	
	private String insuranceSource;

}

	

