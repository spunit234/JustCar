package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.util.DateFormatter;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanMembersBean  {
	private static final long serialVersionUID = 1L;
		
	DateFormatter formatter = new DateFormatter();
	
	private Long customerMemberId;	
		
	private Long  custId;
		
	private LoanBean loanId;

	private Date membershipDate;
				
	private String memberName;	
		
	private String memberStatus;
		
	private Long transactionCount;	
		
	private String ipAddress;	
	private char recordType;
			
	private Long createdBy;	
		
	private Date createdDate;	
		
	private Long lastModifiedBy;	
		
	private Date lastModifiedDate;
	
	
	private String membersShipDate;
	
	public void setMembersShipDate(Date memberShipDate) {
		this.membersShipDate = formatter.formaDate(memberShipDate);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	private Long customerId;

	



	
}

	

