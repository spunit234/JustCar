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
@Table(name = "customer_bank_details")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerBankDetailEntity extends AbstractEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "CUSTOMER_BANK_ID")
	private Long customerBankId;
	
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "CUSTOMER_ID")
	private CustomerEntity customerId;
	
	@Column(name = "BANK_NAME_ID")
	private Long bankListId;
	
	@Column(name = "ACCOUNT_NO")
	private String accountNo;
	
	@Column(name = "IFSC_CODE")
	private String  ifscCode;
	
	@Column(name = "ACCOUNT_TYPE")
	private String accountType;
	
	@Column(name = "PRIMARY_ACCOUNT")
	private String primaryAccount;
	
//	@Column(name = "BANK_DETAIL_STATUS")
//	private String bankStatus;
	
	@Column(name = "TRANSACTION_COUNT")
	private int transactionCount;
	
	@Column(name = "IP_ADDRESS")
	private String ipAddress;
	
	@Column(name = "RECORD_TYPE")
	private char recordType;
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "LAST_MODIFIED_BY")
	private int lastModifiedBy;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Date lastModifiedDate;
	
	
	
	
	
	
	

}
