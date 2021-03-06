package com.edios.cdf.entity.to;

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
public class UserDetailTO implements Serializable {

	private static final long serialVersionUID = -7627867187295244570L;
	
	private Long userID;
	
	private Integer accountID;
	
    private String firstName;
	
	private String lastName;
	
	private Date currentDate;
	
	private String loginName;
	
	private String userType;
	
	private String licenseStartDate;
	
	private String licenseExpiryDate;
	
	private String password;
	
	private Character recordType;
	
	private Integer passwordExpiryDays;
	
	private Date passwordExpiryDate;
	
	private Integer passwordResetFlag;
	
	private Date loginValidStartDate;
	
	private Date loginValidEndDate;
	
	private Integer loginFailedTries;
	
	private String userStatus;
	
	private Integer maxLoginTries;
	
	private String roleName;
	
	private String employeeCode;
	
	private String defaultTheme;
	
	private Long sessionTimeoutMin;
	
	private String sessionID;
	
	private Long defaultRoleID;
	
	private String emailPassword;
	 
	private Long defaultSiteID;

	private String siteName;
}
