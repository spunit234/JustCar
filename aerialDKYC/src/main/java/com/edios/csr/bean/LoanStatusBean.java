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
public class LoanStatusBean extends AbstractBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int vehopenid;
	private int vehicleId;
	private Date openDate;
	private LoanStatusBean loanstastusbean;
	private String loanNo;
	private Long bankName;
	private Double foreclouserAmount;
	private Long transactionCount;
	private char recordType;
	private String ipAddress;
	private Long createdBy;
	private Date createdDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;
	private String dateForFormatting;
	}

