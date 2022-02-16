
package com.edios.csr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.edios.cdf.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicUpdate
@Table(name = "vehicle_insurances")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VehicleInsuranceEntity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VEHICLE_INSURANCE_ID")
	private Long vehicleInsuranceId;

	@JoinColumn(name = "CUSTOMER_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private CustomerEntity customerId;

	@JoinColumn(name = "VEHICLE_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private VehicelsEntity vehicleId;

	@JoinColumn(name = "INQUIRY_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private InquiriesEntity inquiryId;

	@Column(name = "MODE_OF_PAYMENT")
	private String modeOfPayment;

	@JoinColumn(name = "LOAN_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private LoanEntity loanId;

	@Column(name = "INSURANCE_DATE")
	private Date insuranceDate;

	@Column(name = "POLICY_NO")
	private String policyNo;

	@Column(name = "POLICY_ISSUE_DATE")
	private Date policyIssueDate;

	@Column(name = "INSURANCE_SOURCE")
	private String insuranceSource;

	@Column(name = "INSURANCE_AMOUNT")
	private Double insuranceAmount;

	@Column(name = "INSURANCE_PAYMENT_PAID_BY")
	private String insurancePaymentPaidBy;

	@Column(name = "INSURANCE_PAYMENT_PAID_DATE")
	private Date insurancePaymentPaidDate;

	@Column(name = "DISPATCH_NO")
	private String dispatchNo;

	@Column(name = "DISPATCH_DATE")
	private Date dispatchDate;

	@Column(name = "INSURANCE_COMPANY_ID")
	private Long insuranceCompanyId;

	@Column(name = "INSURANCE_STATUS")
	private String insuranceStatus;

	@Column(name = "COMPANY_BANK_ID")
	private Long companyBankId;

	@Column(name = "MODE_OF_DISPATCH")
	private String modeOfDispatch;

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "TRANSACTION_COUNT")
	private Long transactionCount;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "RECORD_TYPE")
	private char recordType;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "LAST_MODIFIED_BY")
	private Long lastModifiedBy;

	@Column(name = "LAST_MODIFIED_DATE")
	private Date lastModifiedDate;

	@Column(name = "CUSTOMER_NAME")
	private String insCustomerName;
	@Column(name = "CONTACT_NUMBER")
	private String contactNumber;
	@Column(name = "VEHICLE_ENGINE_NO")
	private String vehicleEngineNo;
	@Column(name = "VEHICLE_CHESIS_NO")
	private String vehicleChassisNo;
	
	@Column(name = "IGNORE_FLAG")
	private String ignoreFlag;
}
