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

import com.edios.cdf.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inquiry_vehloans")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InquiriesVehicleLoanEntity extends AbstractEntity implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)		
		@Column(name = "INQUIRY_VL_ID")
		private Long inquiryVlId;
		
		@ManyToOne(fetch=FetchType.EAGER)
		@JoinColumn(name = "INQUIRY_ID")
		private InquiriesEntity inquiryId;
		
		@Column(name = "MODEL")
		private String model;
		
		@Column(name = "MODEL_VARIANT")
		private String modelVariant;
		
		@Column(name = "MODEL_MAKE")
		private String modelMake;
		
		@Column(name = "LOAN_ON_VEHICLE")
		private String loanOnVehicle;
		
		@Column(name = "LOAN_AMOUNT")
		private Double loanAmount;

		@Column(name = "REG_NO")
		private String regNo;
		
		@Column(name = "VEHICLE_MANUF_YEAR")
		private Long vehicleManufYear;
		
		@Column(name = "VEHICLE_AGE")
		private Long vehicleAge;
		
		@Column(name = "VEHICLE_FINALIZE")
		private String vehicleFinalize;
		
		@Column(name = "BANK")
		private String bank;
		
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


	}



