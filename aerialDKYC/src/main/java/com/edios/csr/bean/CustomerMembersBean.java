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
public class CustomerMembersBean  {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
		
	DateFormatter formatter = new DateFormatter();
	
	private Long customerMemberId;	
		
	private Long  custId;
		
	private CustomerBean customerId;
	
	private Date membershipDate;
				
	private String memberName;	
		
	private String memberStatus;
		
	private int transactionCount;	
		
	private String ipAddress;	
		
	private char recordType;
			
	private int createdBy;	
		
	private Date createdDate;	
		
	private int lastModifiedBy;	
		
	private Date lastModifiedDate;
	
	
	private String membersShipDate;
	
	public void setMembersShipDate(Date memberShipDate) {
		this.membersShipDate = formatter.formaDate(memberShipDate);
	}
	
	
	

	



	
}

	

