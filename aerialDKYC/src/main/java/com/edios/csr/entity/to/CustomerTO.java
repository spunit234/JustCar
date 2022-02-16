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
public class CustomerTO implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;

	
	
	
	private Long customerId;
	private String customerName;
	
	
	private String nameTitle;
	private String firstName;
	private String middleName;
	private String lastName;
	private String contactNumber;
	private String customerType;
	private String husbandName;
	private String fatherName;
	private String motherName;
	private String passportNumber;
	private Date dateOfBirth;
	
	private String gender;
	private String cibilScore;
	private Long employmentId;
	private Long professionId;
	private Long typeOfCompanyId;
	private Long typeOfBusinessId;
	private String emailAddress;
	private Long altContactNumber;
	private String panNo;
	private String aadharNo;
	private String presentAddress;
	private String presentCity;
	private int presentStateListId;
	private int presentCounteryListId;
	private int isPermanentAddressSame;
	private String presentPinCode ;
	private String permanentAddress;
	private String permanentCity;
	private int permanenetStateListId;
	private int permanenetCountryListId;
	private String permanentPinCode;
	private String officeAddress;
	private String officeCity;
	private int officeStateListId;
	private int officeCountryListId;
	private String officePinCode;
	private String status;
	private Long accountId;
	private Long siteId;
	private Date toDateOfBirth;
	private Date fromDateOfBirth;
	private String occupation;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	
	private String ignoreFlag;

	
	

}
