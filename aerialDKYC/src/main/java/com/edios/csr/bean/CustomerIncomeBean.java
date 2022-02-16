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
public class CustomerIncomeBean {
	private static final long serialVersionUID = 1L;
		
		
	private Long customerIncId;
	
	private CustomerEntity customerId;
	
	
	private Date itrFillingDate;
	
	private String financialYear;
	
	private Double income;
	
	private String itrAckNo;
		
	private String status;
	
	private int transactionCount;	
		
	private String ipAddress;	
		
	private char recordType;
			
	private int createdBy;	
		
	private Date createdDate;	
		
	private int lastModifiedBy;	
		
	private Date lastModifiedDate;
	
	private String incItrFillingDate;	

	
	DateFormatter formatter = new DateFormatter();

	public void setIncItrFillingDate(Date itrFillingDate) {
		this.incItrFillingDate = formatter.formaDate(itrFillingDate);
	}
	
}

	

