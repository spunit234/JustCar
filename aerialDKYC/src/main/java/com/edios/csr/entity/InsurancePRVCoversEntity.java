
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
import com.edios.csr.bean.InsuranceCoversBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicUpdate
@Table(name = "vehicle_insurance_prv_covers")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InsurancePRVCoversEntity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INSURANCE_PRV_COVER_ID")
	private Long insurancePRVCoverId;

	@JoinColumn(name = "VEHICLE_INSURANCE_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private VehicleInsuranceEntity vehicleInsuranceId;

	@Column(name = "CUSTOMER_ID")
	private Long customerId;
	
	
	@Column(name = "POLICY_NO")
	private String policyNo;
	
	@Column(name = "RENEWAL_TYPE")
	private String renewalType;

	@Column(name = "INSURANCE_COVER_TYPE")
	private String insuranceCoverType;
	
	@Column(name = "ADD_ON_COVERAGE_ID")
	private Integer addOnCoverageId;
	
	@Column(name = "INSURANCE_COMPANY_ID")
	private Long insuranceCompanyId;
	
	@Column(name = "INSURANCE_VALID_FROM")
	private Date insuranceValidFrom;
	
	@Column(name = "INSURANCE_VALID_TO")
	private Date insuranceValidTo;
	
	@Column(name = "INSURANCE_PREMIUM_AMOUNT")
	private Double insurancePremiumAmount;

	@Column(name = "SUM_INSURED_AMOUNT")
	private Double sumInsuredAmount;

	

	@Column(name = "NO_CLAIM_BONUS")
	private String noClaimBonus;

	@Column(name = "NUMBER_OF_YEARS")
	private String numberOfYears;

	@Column(name = "INSPECTION_DATE")
	private Date inspectionDate;

	@Column(name = "INSPECTION_DONE_BY")
	private String inspectionDoneBy;
	
	@Column(name = "INSPECTION_REPORT_NUMBER")
	private String inspectionReportNo;
	
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

	public void setUpdatedFields(InsuranceCoversBean updated) {
		this.renewalType = updated.getRenewalType();
		this.insuranceCoverType = updated.getInsuranceCoverType();
		this.addOnCoverageId = updated.getAddOnCoverageId();
		this.inspectionDate = updated.getInspectionDate();
		this.inspectionDoneBy = updated.getInspectionDoneBy();
		this.insurancePremiumAmount = updated.getInsurancePremiumAmount();
		this.insuranceValidFrom = updated.getInsuranceValidFrom();
		this.insuranceValidTo = updated.getInsuranceValidTo();
		this.numberOfYears = updated.getNumberOfYears();
		this.noClaimBonus = updated.getNoClaimBonus();
		this.sumInsuredAmount = updated.getSumInsuredAmount();	
		this.policyNo  = updated.getPolicyNo();	
		this.insuranceCompanyId = updated.getInsuranceCompanyId();	
		this.customerId = updated.getCustomerId();
		this.inspectionReportNo = updated.getInspectionReportNo();

		
		
	}
	public void setUpdatedFields(InsuranceNewCoversEntity updated) {
		this.renewalType = updated.getRenewalType();
		this.insuranceCoverType = updated.getInsuranceCoverType();
		this.addOnCoverageId = updated.getAddOnCoverageId();
		this.inspectionDate = updated.getInspectionDate();
		this.inspectionDoneBy = updated.getInspectionDoneBy();
		this.insurancePremiumAmount = updated.getInsurancePremiumAmount();
		this.insuranceValidFrom = updated.getInsuranceValidFrom();
		this.insuranceValidTo = updated.getInsuranceValidTo();
		this.numberOfYears = updated.getNumberOfYears();
		this.noClaimBonus = updated.getNoClaimBonus();
		this.sumInsuredAmount = updated.getSumInsuredAmount();	
		this.inspectionReportNo = updated.getInspectionReportNo();

//		this.policyNo  = updated.getPolicyNo();	
//		this.insuranceCompanyId = updated.getInsuranceCompanyId();	
//		this.customerId = updated.getCustomerId();	
		
		
	}
	
	public void setUpdatedFields(InsurancePRVCoversEntity updated) {
		this.renewalType = updated.getRenewalType();
		this.insuranceCoverType = updated.getInsuranceCoverType();
		this.addOnCoverageId = updated.getAddOnCoverageId();
		this.inspectionDate = updated.getInspectionDate();
		this.inspectionDoneBy = updated.getInspectionDoneBy();
		this.insurancePremiumAmount = updated.getInsurancePremiumAmount();
		this.insuranceValidFrom = updated.getInsuranceValidFrom();
		this.insuranceValidTo = updated.getInsuranceValidTo();
		this.numberOfYears = updated.getNumberOfYears();
		this.noClaimBonus = updated.getNoClaimBonus();
		this.sumInsuredAmount = updated.getSumInsuredAmount();
		this.inspectionReportNo = updated.getInspectionReportNo();
		this.policyNo  = updated.getPolicyNo();	
		this.insuranceCompanyId = updated.getInsuranceCompanyId();	
		this.customerId = updated.getCustomerId();	
		
		
	}
	
	

}
