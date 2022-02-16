package com.edios.cdf.bean.common;

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
public class ApplicationParameterListBean extends AbstractBean {

	private static final long serialVersionUID = 5001075394771730147L;

	private Long parameterListID;

	private ApplicationParameterBean parameterID;

	private String parameterListCode;

	private String parameterListValue;

	private String parameterListDesc;

	private String parameterListSequence;

	private String customValue1;

	private String customValue2;

	private String customValue3;

	private String customValue4;

	private String parameterListStatus;

	private Date effectiveFromDate;

	private Date effectiveToDate;

	private String remarks;

	private Long transactionCount;

	private String ipAddress;

	private Character recordType;

	private Integer createdBy;

	private Date createdDate;

	private Integer lastModifiedBy;

	private Date lastModifiedDate;

}
