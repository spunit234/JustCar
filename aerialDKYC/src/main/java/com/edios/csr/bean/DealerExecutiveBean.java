package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class DealerExecutiveBean  extends AbstractBean{
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long dealerExecutiveId;    
     
     private Long dealerId;

     
     private String dealerExecutiveName;

     
     private String executiveDesignation;
     
     private String contactNumber;

     
     private String emailAddress;

     
     private String altContactNumber;

     
     private String executiveLocation;

     
     private String executiveStatus;

     
     private String userActivityId;

     
     private Long transactionCount;

     
     private String ipAddress;

     
     private Character recordType;

     
     private Long createdBy;

     
     private Date createdDate;

     
     private Long lastModifiedBy;

     
     private Date lastModifiedDate;
     
     private boolean duplicateFlag;

}
