package com.edios.cdf.entity.to;

import java.io.Serializable;
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
public class UserLoginLogResponseEntityTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6656209126465167817L;
	private Long userLoginId;
	private String userName;
	private String roleName;
	private String ipAddress;
	private String hostName;
	private String deviceType;
	private String osName;
	private String browserName;
	private String browserVersion;
	private String loginDateTime;
	private String lastActivityDateTime;
	private String logoutDateTime;
	private String logoutReason;
	
	DateFormatter formatter = new DateFormatter();
	public void setLoginDateTime(Date activityDateTime) {
		this.loginDateTime=formatter.formatDateTime(activityDateTime);
	}
	public void setLastActivityDateTime(Date auditDateTime) {
		this.lastActivityDateTime=formatter.formatDateTime(auditDateTime);
	}
	public void setLogoutDateTime(Date auditDateTime) {
		this.logoutDateTime=formatter.formatDateTime(auditDateTime);
	}
	
}
