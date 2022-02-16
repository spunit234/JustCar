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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.edios.cdf.entity.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_parameter_values")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationParameterValuesEntity extends AbstractEntity {

	private static final long serialVersionUID = -3661657124529484877L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PARAMETER_VALUE_ID")
	private Long parameterValueID;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "PARAMETER_ID", referencedColumnName = "PARAMETER_ID")
	private ApplicationParameterEntity parameterID;
	
	@Column(name = "PARAMETER_VALUE")
	private String parameterValue;
	
	@Column(name="PARAMETER_BINARY_VALUE")
	private String parameterBinaryValue;

	@Column(name = "PARAMETER_LIST_ID")
	private Long parameterListID;

	@Column(name = "PARAMETER_VALUE_DESCRIPTION")
	private String parameterValueDescription;

	@Column(name = "PARAMETER_VALUE_STATUS")
	private String parameterValueStatus;
	
	@Column(name = "CUSTOM_VALUE_1")
	private String customValue1;

	@Column(name = "CUSTOM_VALUE_2")
	private String customValue2;

	@Column(name = "CUSTOM_VALUE_3")
	private String customValue3;

	@Column(name = "CUSTOM_VALUE_4")
	private String customValue4;

	@Column(name = "TRANSACTION_COUNT")
	private Long transactionCount;
	
	@Column(name = "IP_ADDRESS")
	private String userIPAddress;
	
	@Column(name = "RECORD_TYPE")
	private Character recordType;
	
	@Column(name = "CREATED_BY")
	private Integer createdBy;
	
	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name = "LAST_MODIFIED_BY")
	private Integer lastModifiedBy;
	
	@Column(name = "LAST_MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

}
