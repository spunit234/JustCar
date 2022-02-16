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
public class CustomersBankDetailsTO implements Serializable {
	
	private static final long serialVersionUID = 6342094965674122373L;

   private Long customerBankId;	
	
	private Long customerId;
	
	private Long bankListId;	
	
	private String accountNo;	

	private String  ifscCode;	
	
	private String accountType;
	
	private String primaryAccount;

	private String bankName;
	
	private int transactionCount;

	

	
}
