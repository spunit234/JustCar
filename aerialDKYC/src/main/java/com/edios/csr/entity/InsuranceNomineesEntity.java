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
import com.edios.csr.bean.InsuranceNomineesBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicUpdate
@Table(name = "vehicle_insurance_nominees")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InsuranceNomineesEntity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INSURANCE_NOMINEE_ID")
	private Long insuranceNomineeId;

	@JoinColumn(name = "VEHICLE_INSURANCE_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private VehicleInsuranceEntity vehicleInsuranceId;
	
	@Column(name = "NOMINEE_NAME")
	private String nomineeName;
	
	@Column(name = "NOMINEE_DOB")
	private Date nomineeDob;
	
	@Column(name = "NOMINEE_RELATIONSHIP")
	private String nomineeRelationShip;
	
	@Column(name = "NOMINEE_SHARE")
	private Double nomineeShare;

	@Column(name = "NOMINEE_CONTACT_NO")
	private String nomineeContactNo;
	
	
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


	public void setUpdatedFields(InsuranceNomineesBean updated) {
		// TODO Auto-generated method stub
		this.nomineeContactNo = updated.getNomineeContactNo();
		this.nomineeDob = updated.getNomineeDob();
		this.nomineeName =updated.getNomineeName();
		this.nomineeRelationShip =updated.getNomineeRelationShip();
		this.nomineeShare = updated.getNomineeShare();
		
	}
	
	public void setUpdatedFields(InsuranceNomineesEntity updated) {
		// TODO Auto-generated method stub
		this.nomineeContactNo = updated.getNomineeContactNo();
		this.nomineeDob = updated.getNomineeDob();
		this.nomineeName =updated.getNomineeName();
		this.nomineeRelationShip =updated.getNomineeRelationShip();
		this.nomineeShare = updated.getNomineeShare();
		
	}

}
