package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.csr.entity.AggregatorsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggregatorTasksBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long aggregatorTaskId;
	
	private AggregatorsEntity aggregatorId;

	private Long staffId;
	
	private String staffName;

	private String aggregatorTaskName;
	
	private Integer aggregatorTaskNameId;

	private Date aggregatorTaskDate;

	private String aggregatorTaskStatus;
	
	private Integer aggregatorTaskCharges;
	
	private String notes;
	
	private int transactionCount;
	
	private String ipAddress;
	
	private char recordType;
	
	private int createdBy;
	
	private Date createdDate;
	
	private int lastModifiedBy;
	
	private Date lastModifiedDate;
	
	private String aggregatorTaskDateStr;

}

