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
public class InquiryTO implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;
	private Long inquiryId;
	private String inquiryNo;
	private String inquiryDate;
	private String inquiryType;
	private String customerFullname;
	private String callbackDate;
	private String assigneeName;
	private String createdDate;
	private String lastModifiedDate;
	private String inquiryStatus;
	private String contactNumber;
	private String presentCity;
	private String transactionInquiryNo;
	private Long transactionCount;
	private String loanNo;
	private String loanClass;
	private String loanType;
	private String loanBank;
	private String loanStatus;
	private Long loanId;
	private String sourceName;
	private int sNo;
	private String secondSource;
	private String name;

	private String inquiryAssigne;
	private String sourceStaff1;
	private String sourceStaff2;
	private String sourceStaff3;
	private String sourceStaff4;
	private String vehicleInfo;
	private String odoReading;
	private Double vehicleValue;

	private String lastUpdatedOn;
	private String inqDate;
	private String callBackOn;
	private Double loanAmount;
	private String customerType;

	private Long count;
	private Date dateOfBirth;

	private Double totalLoanAmount;
	private String firstName;
	private String lastName;
	private String duplicateValue;

	private String rcStatus;
	private Integer pendingDays;
	private String vehicleRegNo;
	private String vehicleChassisNo;
	private String aggTaskDateStr;
	private String aggTaskStatus;
	private int aggTaskCharges;
	private int aggTaskTotalCharges;
	private String aggTaskName;
	private String aggTaskType;
	private String rcOnline;
	private String rcReceived;
	private int aggregatorId;

	//car and cash report
	private String loanNumber;
	private String disbursedDate;
	private String customerName;
	private String refrence1;
	private String refrence2;
	private String refrence3;
	private String refrence4;
	private String vehicleInformation;
	private String loanStartDate;
	private Double installmentAmount;
	private Double advanceAmount;
	private Date statusDate;
}
