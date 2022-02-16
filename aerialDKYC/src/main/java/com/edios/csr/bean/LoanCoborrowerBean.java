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
public class LoanCoborrowerBean  {
	private static final long serialVersionUID = 1L;
			
	private Long customerCoborrowersId;	
				
	private LoanBean loanId;
	
	private CustomerBean customerId;
	
	private String relationWithCustomer;
		
	private int transactionCount;	
		
	private String ipAddress;	
	
	private char recordType;
			
	private Long createdBy;	
		
	private Date createdDate;	
		
	private int lastModifiedBy;	
		
	private Date lastModifiedDate;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

	



	
}

	

