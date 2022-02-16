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
public class CustomersTO implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;

	private Long customerId;
	private String nameTitle;
	private String firstName;
	private String middleName;
	private String lastName;
	private String contactNumber;
	private String emailAddress;
	private Long altContactNumber;
	private String panNo;
	private String aadharNo;
	private String status;
	private Long accountId;
	private Long siteId;
	private Long transactionCount;
	private String fullName;
	private Long customerAddId;
	private String postalCode;
	private String customerType;
	private String createdByName;
	
	private String landholding;
	private String actualLandholding;
	private String villageName;
	private String tehsil;
	private String district;
	private String ownerName;
	private String relationWOwner;
	private String city;
	
	private String gstNo;
	private String firmType;
	private Date firmFormationDate;	
	private Date dateOfBirth;
	private String voterId;
	private String passportNumber;
	private String dlNo;
	private String ignoreFlag;
	private String designation;
	private String occupation;
	private String cibilScore;
	private String age;
	private String professionType;
}
