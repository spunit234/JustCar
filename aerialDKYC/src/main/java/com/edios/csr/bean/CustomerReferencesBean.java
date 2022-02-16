package com.edios.csr.bean;

import java.util.Date;

import com.edios.csr.entity.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerReferencesBean {
	private static final long serialVersionUID = 1L;
		
		
	private Long customerRefId;
	
	private CustomerEntity customerId;
	
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
	
	private int transactionCount;	
		
	private String ipAddress;	
		
	private char recordType;
			
	private int createdBy;	
		
	private Date createdDate;	
		
	private int lastModifiedBy;	
		
	private Date lastModifiedDate;
}

	

