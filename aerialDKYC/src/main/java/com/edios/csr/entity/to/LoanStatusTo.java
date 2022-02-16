package com.edios.csr.entity.to;

import java.io.Serializable;
import java.util.Date;

import com.edios.csr.bean.LoanStatusBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanStatusTo implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;
	private int vehopenid;
	private int vehicleId;
	private String openDate;
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

}
