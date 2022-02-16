package com.edios.cdf.util;

import java.io.Serializable;
import java.util.Date;

import com.edios.cdf.entity.to.AccountUserEntityTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class PayloadBean implements Serializable {

	private static final long serialVersionUID = 2832385012847929536L;

	Long id;
	Integer accountId;
	String signatureKey;
	String searchParameter;
	String customParameter;
	String custom1;
	Date fromDate;
	Date toDate;
	Long userId;
	String status;
	// End By Harshit
	String itemName;
	String item;
	String itemClass;
	Long daysCount;
	Long loginDetailId;
	String screenName;
	String activityName;
	Boolean activityFlag;
	String tableName;
	String pkColName;
	Long pkColValue;
	String remarks;
	String sessionID;
	String logoutReason;
	Long sessionTimeoutMin;
	int startPosition;
	int maxResult;
	AccountUserEntityTO siteId;

}
