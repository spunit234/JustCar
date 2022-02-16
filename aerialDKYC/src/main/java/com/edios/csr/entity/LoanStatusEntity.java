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
@Table(name = "vehicles_open_details")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanStatusEntity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VEH_OPENL_ID")
	private int vehopenid; 
	@Column(name = "VEHICLE_ID")
	private int vehicleId;
	@Column(name = "OPEN_DATE")
	private Date openDate;
	@Column(name ="OPEN_LOAN_NO")
	private String loanNo;
	@Column(name ="BANK_NAME_ID")
	private Long bankName;
	@Column(name ="FORECLOSURE_AMOUNT")
	private Double foreclouserAmount;
	@Column(name ="TRANSACTION_COUNT")
	private Long transactionCount;
	@Column(name ="RECORD_TYPE")
	private char recordType;
	@Column(name ="IP_ADDRESS")
	private String ipAddress;
	@Column(name ="CREATED_BY")
	private Long createdBy;
	@Column(name ="CREATED_DATE")
	private Date createdDate;
	@Column(name ="LAST_MODIFIED_BY")
	private String lastModifiedBy;
	@Column(name ="LAST_MODIFIED_DATE")
	private Date lastModifiedDate;
}
