package com.edios.cdf.bean.security;

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
public class UserLoginDetailsBean extends AbstractBean {

	private static final long serialVersionUID = -7959006084525327336L;

	private Long userLoginId;
	private RoleBean role;
	private UserBean user;
	private Date loginDateTime;
	private Date logoutDateTime;
	private String logoutReason;
	private String ipAddress;
	private String macAddress;
	private String hostName;
	private String browserName;
	private String browserVersion;
	private String osName;
	private String deviceType;
	
}
