package com.edios.csr.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanReferencesBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
		
		
	private Long customerRefId;
	
	private LoanBean loanId;
	
	private String referenceName;
	
	private String contactNo;

	private String altContactNo;
	
	private String email;
	
	private String address;
	
	private String tehsil;
	
	private String city;
	
	private String postalCode;
	
	private String district;
	
	private String stateId;
	
	public String stateName;
			
	private String status;
	
	private Long transactionCount;	
		
	private String ipAddress;	
		
	private Character recordType;
			
	private Long createdBy;	
		
	private Date createdDate;	
		
	private Long lastModifiedBy;	
		
	private Date lastModifiedDate;
	
	private Long customerId;
}

	

