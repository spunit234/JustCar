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
public class LoanCommissionTo implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;
	private Long commissionId;
	private Long paybleTo;
	private String transactionType;
	private String transactionDate;
	private String commissionType;
	private String paymentMode;
	private String paymentType;
	private Double transactionPercentage;
	private Double transactionValue;
	private String bankName;

	private Long transactionCount;
	private String ifscCode;
	private String accountNo;
	private String accountType;
	
}
