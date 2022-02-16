package com.edios.csr.entity.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanSurityChequesTo implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;
	private Long loanChequeId;
	private String chequeNo;
	private String chequeDate;
	private String bankName;
	private Double amount;
	private String chequeReceived;
	private String chequeType;
	private String ifscCode;
	private String accountNo;
	private String accountType;
	private String reasonNotes ;
	private Long transactionCount;
	private Long bankNameId;

}
