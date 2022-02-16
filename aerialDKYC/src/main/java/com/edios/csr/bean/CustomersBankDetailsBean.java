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
public class CustomersBankDetailsBean {
	private static final long serialVersionUID = 1L;
	
	
	private Long customerBankId;	
	
	private Long  custId;
	
	private CustomerEntity customerId;
	
	private Long bankListId;	
	
	private String accountNo;	
	
//	private String bankStatus;


	private String  ifscCode;	
	
	private String accountType;	
	
	private String primaryAccount;	
	
	private int transactionCount;	
	
	private String ipAddress;	
	
	private char recordType;
		
	private int createdBy;	
	
	private Date createdDate;	
	
	private int lastModifiedBy;	
	
	private Date lastModifiedDate;
}
