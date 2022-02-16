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
@Table(name = "loan_references")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanReferencesEntity extends AbstractEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "CUSTOMER_REF_ID")
	private Long customerRefId;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "LOAN_ID")
	private LoanEntity loanId;
	
	@Column(name = "REFERENCE_NAME")
	private String referenceName;
	
	@Column(name = "CONTACT_NUMBER")
	private String contactNo;
	
	@Column(name = "ALT_CONTACT_NUMBER")
	private String altContactNo;
	
	@Column(name = "EMAIL_ADDRESS")
	private String email;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "TEHSIL")
	private String tehsil;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "POSTAL_CODE")
	private String postalCode;
	
	@Column(name = "DISTRICT")
	private String district;
	
	@Column(name = "STATE_ID")
	private String stateId;
			
	@Column(name = "STATUS")
	private String status;
	
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
	
	@Column(name = "CUSTOMER_ID")
	private Long customerId;
	
	
	
	
	

}
