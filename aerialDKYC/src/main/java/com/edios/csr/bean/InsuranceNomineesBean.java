
package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.csr.entity.VehicleInsuranceEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InsuranceNomineesBean  extends AbstractBean{

	private static final long serialVersionUID = 1L;
	
	private Long insuranceNomineeId;

	private VehicleInsuranceEntity vehicleInsuranceId;
	
	private String nomineeName;
	
	private Date nomineeDob;
	
	private String nomineeRelationShip;
	
	private Double nomineeShare;

	private String nomineeContactNo;
	
	private Long transactionCount;

	private String ipAddress;

	private char recordType;

	private Long createdBy;

	private Date createdDate;

	private Long lastModifiedBy;

	private Date lastModifiedDate;
	
	
}

	

