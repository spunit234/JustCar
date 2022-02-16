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
public class UserActivityDetailsBean  extends AbstractBean {

	private static final long serialVersionUID = -7959006084525327336L;

	private Long userActivityId;
	private UserLoginDetailsBean userLoginDetails;
	private Date activityDateTime;	
	private String screenName;
	private String activityName;
	private String searchCriteria;
	private Long searchedRecordCount;
	private String tableName;
	private String pkColName;
	private Long pkColValue;
	private String remarks;
	
	
}
