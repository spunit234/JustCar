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
@Table(name = "customer_income")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerIncomeEntity extends AbstractEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "CUSTOMER_INC_ID")
	private Long customerIncId;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private CustomerEntity customerId;
	
	@Column(name = "ITR_FILLING_DATE")
	private Date itrFillingDate;
	
	@Column(name = "FINANCIAL_YEAR")
	private String financialYear;
	
	@Column(name = "INCOME")
	private Double income;
	
	@Column(name = "ITR_ACK_NO")
	private String itrAckNo;
	
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
