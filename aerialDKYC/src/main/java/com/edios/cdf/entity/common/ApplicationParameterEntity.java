package com.edios.cdf.entity.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edios.cdf.entity.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_parameters")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationParameterEntity extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PARAMETER_ID")
	private Long parameterID;

/*	@Column(name = "ACCOUNT_ID")
	private Integer accountID;*/

	@Column(name = "PARAMETER_NAME")
	private String parameterName;

	@Column(name = "PARAMETER_CODE")
	private String parameterCode;

	@Column(name = "PARAMETER_DESCRIPTION")
	private String parameterDesc;

	@Column(name = "PARAMETER_TYPE")
	private String parameterType;

	@Column(name = "PARAMETER_VALUES_COUNT")
	private Integer parameterValueCount;

	@Column(name = "PARAMETER_DATA_TYPE")
	private String parameterDataType;

	@Column(name = "PARAMETER_DATA_LENGTH")
	private Integer parameterLength;

	@Column(name = "PARAMETER_MIN_VALUE")
	private Integer parameterMinValue;

	@Column(name = "PARAMETER_MAX_VALUE")
	private Integer parameterMaxValue;

	@Column(name = "PARAMETER_DATE_FORMAT")
	private String parameterDateFormat;

	@Column(name = "PARAMETER_DECIMAL_LENGTH")
	private Integer parameterDecimalLength;

	@Column(name = "PARAMETER_STATUS")
	private String parameterStatus;

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
