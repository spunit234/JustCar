package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerBean extends AbstractBean {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private Long customerId;
	private String nameTitle;
	private Date dateOfBirth;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;

	private String customerType;
	private String husbandName;
	private String fatherName;
	private String motherName;
	private String passportNumber;
	private String occupation;
	private String companyType;
	private String industryType;
	private String professionType;
	private String companyTypeSE;
	private String buisnessTypeSE;
	private String otherOccupation;
	private String dlNo;
	private Date firmFormationDate;

	private String designation;
	
	private String landholding;
	private String actualLandholding;
	private String villageName;
	private String tehsil;
	private String district;
	private String ownerName;
	private String relationWOwner;
	private String city;
	
	private String presentHouseType;
	private String presentTehsil;
	
	private String presentDistrict;

	private String firmType;

	
	private String gstNo;

	private String cibilScore;
	private Long employmentId;
	private Long professionId;
	private Long typeOfCompanyId;
	private Long typeOfBusinessId;
//	private String contactNumber;
	private String presentContNum;
	private String permanentContNum;
	private String officeContNum;
//	private String emailAddress;
	private String presentEmailAdd;
	private String permanentEmailAdd;
	private String officeEmailAdd;
//	private Long altContactNumber;
	private Long presentAltContNum;
	private Long permanentAltContNum;
	private Long officeAltContNum;
	private String panNo;
	private Long presentCounteryListId;
	private String aadharNo;
	private String voterId;

	private String presentAddress;
	private String presentCity;
	private Long presentStateListId;

	private int isPermanentAddressSame;
	private String permanentHouseType;
	private String permanentTehsil;
	private String permanentDistrict;
	private String presentPinCode;
	private String permanentAddress;
	private String permanentCity;
	private int permanenetStateListId;
	private int permanenetCountryListId;
	private String permanentPinCode;
	private String officeAddress;
	private String officeTehsil;
	private String officeDistrict;
	private String officeCity;
	private int officeStateListId;
	private int officeCountryListId;
	private String officePinCode;
	private String status;
	private Long accountId;
	private Long siteId;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	private int userId;
	private boolean duplicateFlag;
	private String maritalStatus;
	private String age;
	
	private String ignoreFlag;

}
