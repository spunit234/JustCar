package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.csr.entity.LoanEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanSurityChequesBean extends AbstractBean {

	private static final long serialVersionUID = 6342094965674122373L;
	private Long loanChequeId;
	private LoanEntity loanId;
	private String chequeNo;
	private Date chequeDate;
	private Long bankName;
	private Double amount;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	private String chequeReceived;
	private String chequeType;
	private String ifscCode;
	private String accountNo;
	private String accountType;
	private String reasonNotes;
}
