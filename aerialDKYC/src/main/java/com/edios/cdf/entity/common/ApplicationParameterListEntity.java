package com.edios.cdf.entity.common;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_parameter_lists")
@Setter @Getter @NoArgsConstructor
public class ApplicationParameterListEntity extends AbstractEntity{

	private static final long serialVersionUID = 4418464484775491796L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PARAMETER_LIST_ID")
	private Long parameterListID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARAMETER_ID")
	private ApplicationParameterEntity parameterID;
	
	
	@Column(name = "PARAMETER_LIST_CODE")
	private String parameterListCode;

	@Column(name = "PARAMETER_LIST_VALUE")
	private String parameterListValue;

	@Column(name = "PARAMETER_LIST_DESCRIPTION")
	private String parameterListDesc;
	
	@Column(name = "PARAMETER_LIST_SEQUENCE")
	private String parameterListSequence;
	
	@Column(name = "CUSTOM_VALUE_1")
	private String customValue1;

	@Column(name = "CUSTOM_VALUE_2")
	private String customValue2;

	@Column(name = "CUSTOM_VALUE_3")
	private String customValue3;

	@Column(name = "CUSTOM_VALUE_4")
	private String customValue4;
	
	@Column(name = "PARAMETER_LIST_STATUS")
	private String parameterListStatus;
	
	@Column(name = "EFFECTIVE_FROM_DATE")
	private Date effectiveFromDate;
	
	@Column(name = "EFFECTIVE_TO_DATE")
	private Date effectiveToDate;
	
	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "TRANSACTION_COUNT")
	private Long transactionCount;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "RECORD_TYPE")
	private Character recordType;

	@Column(name = "CREATED_BY")
	private Integer createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "LAST_MODIFIED_BY")
	private Integer lastModifiedBy;

	@Column(name = "LAST_MODIFIED_DATE")
	private Date lastModifiedDate;
	
	
	
	
	
	
}
