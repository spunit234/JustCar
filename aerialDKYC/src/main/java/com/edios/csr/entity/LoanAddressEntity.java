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
@Table(name = "loan_addresses")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanAddressEntity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CUSTOMER_ADD_ID")
	private Long customerAddId;

	@JoinColumn(name = "LOAN_ID")
	@ManyToOne(fetch = FetchType.EAGER)
	private LoanEntity loanId;

	@Column(name = "ADDRESS_TYPE")
	private String addressType;

	@Column(name = "HOUSE_TYPE")
	private String houseType;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "DISTRICT")
	private String district;
	
	@Column(name = "STATE_ID")
	private Long stateId;
	
	@Column(name = "POSTAL_CODE")
	private String postalCode;

	@Column(name = "TEHSIL")
	private String tehsil;
	

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CONTACT_NUMBER")
	private String contactNumber;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name = "ALT_CONTACT_NUMBER")
	private String altContactNumber;
	
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

	@Column(name = "EMPLOYER_NAME")
	private String employerName;
	
	@Column(name = "CUSTOMER_ID")
	private Long customerId;
}
