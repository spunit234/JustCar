package com.edios.csr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edios.cdf.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customerslatest")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerEntity extends AbstractEntity implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "CUSTOMER_ID")
		private Long customerId;
		
		@Column(name = "NAME_TITLE")
		private String nameTitle;
		
		@Column(name = "FIRST_NAME")
		private String firstName;
		
		@Column(name = "MIDDLE_NAME")
		private String middleName;
		
		@Column(name = "LAST_NAME")
		private String lastName;
		
		
		
		@Column(name = "HUSBAND_NAME")
		private String husbandName;
		
		@Column(name = "FATHER_NAME")
		private String fatherName;
		
		@Column(name = "BIRTH_DATE")
		private Date dateOfBirth;

		@Column(name = "GENDER")
		private String gender;

		
		@Column(name = "MOTHER_NAME")
		private String motherName;
		
	
		
		@Column(name = "CIBIL_SCORE")
		private String cibilScore;
		
		@Column(name = "GST_NO")
		private String gstNo;
		
		@Column(name = "AADHAR_NO")
		private String aadharNo;
		
		@Column(name = "PAN_NO")
		private String panNo;
		
		@Column(name = "VOTER_ID")
		private String voterId;
		
		@Column(name = "PASSPORT_NO")
		private String passportNumber;
		
		@Column(name = "DESIGNATION")
		private String designation;
		
		@Column(name = "CUSTOMER_TYPE")
		private String customerType;
		
		@Column(name = "FIRM_TYPE")
		private String firmType;

		
		@Column(name = "OCCUPATION")
		private String occupation;
		
		@Column(name = "PROFESSION_TYPE")
		private String professionType;
		
		@Column(name = "JOB_COMPANY_TYPE")
		private String companyType;
		
		@Column(name = "JOB_INDUSTRY_TYPE")
		private String industryType;
		
		@Column(name = "SE_COMPANY_TYPE")
		private String companyTypeSE;
		
		@Column(name = "SE_BUSINESS_TYPE")
		private String buisnessTypeSE;
		
		@Column(name = "OCCUPATION_OTHER")
		private String otherOccupation;
//		
//		@Column(name = "LOAN_PROGRAM")
//		private String panNo;
		
		@Column(name = "LAND_HOLDING")
		private String landholding;
		
		@Column(name = "ACTUAL_LAND_HOLDING")
		private String actualLandholding;
		
		@Column(name = "VILLAGE")
		private String villageName;
		
		@Column(name = "TEHSIL")
		private String tehsil;
		
		@Column(name = "DISTRICT")
		private String district;
		
		@Column(name = "CITY")
		private String city;
		
		@Column(name = "OWNER_NAME")
		private String ownerName;
		
		@Column(name = "RELATION_WITH_OWNER")
		private String relationWOwner;
		
		
		
		@Column(name = "DL_NO")
		private String dlNo;
		
		@Column(name = "FIRM_FORMATION_DATE")
		private Date firmFormationDate;
		
		@Column(name = "STATUS")
		private String status;
		
		@Column(name = "ACCOUNT_ID")
		private Long accountId;
		
		@Column(name = "SITE_ID")
		private Long siteId;
		
		@Column(name = "TRANSACTION_COUNT")
		private Long transactionCount;
		
		@Column(name = "IP_ADDRESS")
		private String ipAddress;
		
		@Column(name = "RECORD_TYPE")
		private Character recordType;
		
		@Column(name = "CREATED_BY")
		private Long createdBy;
		
		@Column(name = "CREATED_DATE")
		private Date createdDate;
		
		@Column(name = "LAST_MODIFIED_BY")
		private Long lastModifiedBy;
		
		@Column(name = "LAST_MODIFIED_DATE")
		private Date lastModifiedDate;

		@Column(name = "MARITAL_STATUS")
		private String maritalStatus;
		
		@Column(name = "AGE")
		private String age;
		
		@Column(name = "IGNORE_FLAG")
		private String ignoreFlag;
	}



