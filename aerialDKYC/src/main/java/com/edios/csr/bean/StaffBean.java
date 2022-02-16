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
public class StaffBean extends AbstractBean {

	private static final long serialVersionUID = 6342094965674122373L;

	
	private Long staffId;
	private String staffType;
	private String staffCode;	
	private String nameTitle;
	private String staffName;
	private String firstName;	
	private String middleName;
	private String siteName;
	private String lastName;
	private String fatherName;
	private String motherName;

	private Long staffLeaderID;
	private String contactNumber;	
	private String emailAddress;	
	private String aadharNo;
	private String panNo;	
	private Long designation;	
	private Long reportingManagerId;	
	private String mobileAccess;
	private String mobileAppUserName;	
	private String mobileAppPassword;	
	private String mobileAppFcmId;	
	private String status;
	private Long accountId;	
	private Long siteId;
	private String altContactNumber;	
	private String accountNo;	
	private String ifscCode;	
	private String accountType;	
	private Long transactionCount;
	private String ipAddress;	
	private Character recordType;	
	private Long createdBy;	
	private Date createdDate;	
	private Long  lastModifiedBy;	
	private Date lastModifiedDate;
	private Date birthDate;
	private String gender;
	private String address;
	private String city;
	private String district;
	private Long state;
	private String postalCode;
	private Long bank;
	private String tehsil;
}
