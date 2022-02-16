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
@Table(name = "inquiries")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InquiriesEntity extends AbstractEntity implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "INQUIRY_ID")
		private Long inquiryId;
		
		@Column(name = "INQUIRY_NO")
		private String inquiryNo;
		
		@Column(name = "INQUIRY_DATE")
		private Date inquiryDate;
		
		@Column(name = "INQUIRY_TYPE")
		private String inquiryType;
		
		
		@Column(name = "CUSTOMER_ID")
		private Long customerId;

		@Column(name = "CALLBACK_DATE")
		private Date callbackDate;
		
		@Column(name = "SOURCE_STAFF_ID1")
		private Long sourceStaffId;

		
		@Column(name = "ASSIGNED_SITE_ID")
		private Long assignedSiteId;
		
		
		@Column(name = "ASSIGNED_STAFF_ID")
		private Long assignedStaffId;
		
		@Column(name = "INQUIRY_DESCRIPTION")
		private String inquiryDescription;
		
		@Column(name = "INQUIRY_STATUS")
		private String inquiryStatus;
		
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
		
		@Column(name = "TRANSACTION_INQUIRY_NO")
		private String transactionInquiryNo;

		@Column(name = "STAFF_ID")
		private Long staffId;

		
		@Column(name = "NAME_TITLE")
		private String nameTitle;
		
		@Column(name = "FIRST_NAME")
		private String firstName;
		
		@Column(name = "MIDDLE_NAME")
		private String middleName;
		
		@Column(name = "LAST_NAME")
		private String lastName;
		
		@Column(name = "CONTACT_NUMBER")
		private String contactNumber;
		
		@Column(name = "EMAIL_ADDRESS")
		private String emailAddress;
		
		@Column(name = "CITY")
		private String city;
		
		@Column(name = "POSTAL_CODE")
		private String postalCode;

		
		@Column(name = "SOURCE_STAFF_ID2")
		private Long sourceStaffId2;
		
		@Column(name = "SOURCE_STAFF_ID3")
		private Long sourceStaffId3;
		
		@Column(name = "SOURCE_STAFF_ID4")
		private Long sourceStaffId4;
		
		@Column(name = "STATUS_DATE")
		private Date statusDate;
	}



