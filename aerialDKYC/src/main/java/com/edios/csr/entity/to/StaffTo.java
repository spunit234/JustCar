package com.edios.csr.entity.to;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StaffTo implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;
	private Long staffId;
	private String staffType;
	private String staffCode;
	private String nameTitle;
	private String firstName;
	private String middleName;
	private String lastName;
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
	private String siteName;
	private Long transactionCount;
	private String fullName;
	private String staffName;
	private Long staffLeaderID;
	private String ipAddress;	
	private Character recordType;	
	private Long createdBy;	
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
