package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.util.DateFormatter;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InquiryBean extends AbstractBean {

	private static final long serialVersionUID = 6342094965674122373L;
	private Long inquiryId;
	private String inquiryNo;
	private Date inquiryDate;
	private String inquiryType;
	private Long customerId;
	private Date callbackDate;
	private Long sourceStaffId;
	private Long assignedSiteId;
	private Long assignedStaffId;
	private String inquiryDescription;
	private String inquiryStatus;
	private Long accountId;
	private Long siteId;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	private String transactionInquiryNo;
	private String loanNo;
	private String panNo;
	private String aadharNo;
	private String name;
	private String nameTitle;
	private String firstName;
	private String middleName;
	private String lastName;
	private String contactNumber;
	private String emailAddress;
	private String city;
	private Long sourceStaffId2;
	private Long sourceStaffId3;
	private Long sourceStaffId4;
	private String postalCode;
	private String signatureKey;

	private String customerFullname;
	private String lastUpdatedOn;
	private String inqDate;
	private String callBackOn;
	private String inquiryAssigne;
	private Long staffId;
	private String staffFullName;
	private Date inquiryFromDate;
	private Date inquiryToDate;
	private Date fromDate;
	private Date toDate;
	private Long vehicleId;
	private String odoReading;
	private Double vehicleValue; 
	private String reportType;
	private String vehicleInfo;
	private Date statusDate;
	DateFormatter formatter = new DateFormatter();

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = formatter.formaDate(lastUpdatedOn);
	}

	public void setInqDate(Date inqDate) {
		this.inqDate = formatter.formaDate(inqDate);
	}

	public void setCallBackOn(Date callBackOn) {
		this.callBackOn = formatter.formaDate(callBackOn);
	}

}
