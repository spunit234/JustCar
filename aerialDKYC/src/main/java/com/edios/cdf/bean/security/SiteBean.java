package com.edios.cdf.bean.security;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.display.ParameterDropDownBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
	

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SiteBean extends AbstractBean{

	private static final long serialVersionUID = -7959006084525327336L;
	
	private Long siteID;
	
	private Integer accountID;
	
	private String siteName;
	
	private String siteCode;
	
	private ParameterDropDownBean siteTypeListID;
	
	private String siteStatus;
	
	private String address1;
	
	private String address2;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String zipCode;
	
	private String workPhone;
	
	private String faxPhone;
	
	private String emailID;
	
	private Long parentSiteID;
	
	private String notes;
	
	private Long transactionCount;

	private Integer createdBy;

	private String ipAddress;

	private Date createdOn;

	private Integer lastModifiedBy;

	private Date lastModifiedOn;

	private Character recordType;
	
}
