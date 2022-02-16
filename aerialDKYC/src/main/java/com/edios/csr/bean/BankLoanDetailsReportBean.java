package com.edios.csr.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BankLoanDetailsReportBean {

	private Long bankName;
	private Long loanClass;
	private Date fromDate;
	private Date toDate;
	private String reportType;
	private String customerType;
	private Long loanStatus;
	private String customerName;
}
