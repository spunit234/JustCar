package com.edios.cdf.entity.to;

import java.util.Date;

import javax.persistence.Column;

import com.edios.cdf.util.DateFormatter;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserActivityLogEntityTO {

	private Long userActivityId;
	private String userName;
	private String roleName;
	private String screenName;
	private String activityName;
	private String activityDateTime;
	private String auditDateTime;
	private String rowAction;
	private String tableName;
	private String columnName;
	private String oldValue;
	private String newValue;
	private String searchCriteria;
	private Long searchedRecordCount;
	private String remarks;
	private String tablePkColName;
	private Long tablePkColValue;
	
	
	DateFormatter formatter = new DateFormatter();
	public void setActivityDateTime(Date activityDateTime) {
		this.activityDateTime=formatter.formatDateTime(activityDateTime);
	}
	public void setAuditDateTime(Date auditDateTime) {
		this.auditDateTime=formatter.formatDateTime(auditDateTime);
	}
	
}
