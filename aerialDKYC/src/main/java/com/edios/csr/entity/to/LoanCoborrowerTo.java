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
public class LoanCoborrowerTo implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;
	private Long customerCoborrowersId;		
	private String relationWithCustomer;
	private String nameTitle;
	private String firstName;
	private String middleName;
	private String lastName;
	private String contactNumber;
	private String emailAddress;
	private String panNo;
	private String aadharNo;
	private int transactionCount;	
	private String city;
	private String fullName;
	private String postalCode;		
	private String customerType;
	private Long loanId;
	private Long customerId;
	private String designation;
	private String occupation;
	private String employerName;
	private String age;
	private String professionType;
	private String cibilScore;

}
