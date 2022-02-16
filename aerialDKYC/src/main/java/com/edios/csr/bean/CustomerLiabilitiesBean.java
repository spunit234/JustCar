package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.util.DateFormatter;
import com.edios.csr.entity.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerLiabilitiesBean {
	private static final long serialVersionUID = 1L;
		
		
	private Long customerLiabId;
	
	private CustomerEntity customerId;
		
	private String loanNo;
	
	private int bankNameId;
	
	private String bankName;
	
	private Date startDate;
	
	private Date endDate;
	
	private Double loanAmount;
	
	private Double foreclosureAmount;
		
	private String status;
	
	private int transactionCount;	
		
	private String ipAddress;	
		
	private char recordType;
			
	private int createdBy;	
		
	private Date createdDate;	
		
	private int lastModifiedBy;	
		
	private Date lastModifiedDate;
	
	private String liabEndDate;	

	private String liabStartDate;	

	
	DateFormatter formatter = new DateFormatter();

	public void setLiabStartDate(Date startDate) {
		this.liabStartDate = formatter.formaDate(startDate);
	}
	
	public void setLiabEndDate(Date endDate) {
		this.liabEndDate = formatter.formaDate(endDate);
	}
}

	

