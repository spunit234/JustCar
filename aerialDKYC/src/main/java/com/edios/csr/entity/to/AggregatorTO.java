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
public class AggregatorTO implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;
	
	private String signatureKey;
	private Long aggregatorId;
	private Long inquiryId;
	private String aggregatorStatus;
	private Date aggregarorFromDate;
	private Date aggregarortoDate;
	private Integer assignedStaffId;
	private Integer loanStatusId;
	private String loanStatus;
	private String loanNo;
	private String inquiryNo;
	private String inquiryType;
	private String customerName;
	private String customerContNo;
	private String customerPanNo;
	private String customerAdharNo;
	private Date larBankDate;
	private String larStatus;
	private String executiveName;
	private String aggregatorDateStr;
	private String firstName;
	private String lastName;
	private String aggregatorType;
	private Long loanId;
}
