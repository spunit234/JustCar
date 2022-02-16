package com.edios.csr.bean;

import java.util.Date;

import javax.persistence.Column;

import com.edios.cdf.bean.AbstractBean;
import com.edios.csr.entity.AggregatorsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggregatorPaymentsBean extends AbstractBean {

	private Long aggregatorPaymentId;

	private AggregatorsEntity aggregatorId;

	private String paymentType;

	private String applicationNo;

	private String transactionNo;

	private Date transactionDate;

	private String transactionMode;

	private Double transactionAmount;

	private Long companyBankId;

	private Long customerbankId;

	private String notes;

	private int transactionCount;

	private String ipAddress;

	private char recordType;

	private int createdBy;

	private Date createdDate;

	private int lastModifiedBy;

	private Date lastModifiedDate;

	private String transactionDateStr;

	private String customerBankName;

	private String customerAccNo;

	private String customerIFSC;

	private String customerAccType;

	private String customerName;

	private String customerContNo;

	private String panNo;

	private String aadharNo;

	private Long customerId;
	private Double totalPaidAmount;

}
