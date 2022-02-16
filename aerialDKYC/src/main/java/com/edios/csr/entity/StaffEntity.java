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
@Table(name = "staff")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StaffEntity extends AbstractEntity implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "STAFF_ID")
		private Long staffId;
		
		@Column(name = "STAFF_TYPE")
		private String staffType;
		
		@Column(name = "STAFF_CODE")
		private String staffCode;
		
		@Column(name = "NAME_TITLE")
		private String nameTitle;
		
		@Column(name = "FIRST_NAME")
		private String firstName;
		
		@Column(name = "MIDDLE_NAME")
		private String middleName;
		
		@Column(name = "LAST_NAME")
		private String lastName;
		
		@Column(name = "FATHER_NAME")
		private String fatherName;
		
		@Column(name = "MOTHER_NAME")
		private String motherName;
		
		@Column(name = "CONTACT_NUMBER")
		private String contactNumber;
		
		@Column(name = "ALT_CONTACT_NUMBER")
		private String altContactNumber;	

		@Column(name = "ACCOUNT_NO")
		private String accountNo;	
		
		@Column(name = "IFSC_CODE")
		private String ifscCode;	
		
		@Column(name = "ACCOUNT_TYPE")
		private String accountType;
		
		@Column(name = "EMAIL_ADDRESS")
		private String emailAddress;
		
		@Column(name = "AADHAR_NO")
		private String aadharNo;

		@Column(name = "PAN_NO")
		private String panNo;
		
		@Column(name = "DESIGNATION_ID")
		private Long designation;
		
		@Column(name = "REPORTING_MANAGER_ID")
		private Long reportingManagerId;
		
		@Column(name = "MOBILE_ACCESS")
		private String mobileAccess;
		
		@Column(name = "MOBILE_APP_USER_NAME")
		private String mobileAppUserName;
		
		@Column(name = "MOBILE_APP_PASSWORD")
		private String mobileAppPassword;
		
		@Column(name = "MOBILE_APP_FCM_ID")
		private String mobileAppFcmId;
		
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
		private Long  lastModifiedBy;
		
		@Column(name = "LAST_MODIFIED_DATE")
		private Date lastModifiedDate;

		@Column(name = "BIRTH_DATE")
		private Date birthDate;
		
		@Column(name = "GENDER")
		private String gender;
		
		@Column(name = "ADDRESS")
		private String address;
		
		@Column(name = "CITY")
		private String city;
		
		@Column(name = "DISTRICT")
		private String district;
		
		@Column(name = "STATE")
		private Long state;
		
		@Column(name = "POSTAL_CODE")
		private String postalCode;

		@Column(name = "BANK_NAME_ID")
		private Long bank;
		
		@Column(name = "TEHSIL")
		private String tehsil;
	}



