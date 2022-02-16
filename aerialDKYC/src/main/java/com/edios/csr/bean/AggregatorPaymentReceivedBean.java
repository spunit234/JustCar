package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggregatorPaymentReceivedBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long aggregatorPaymentReceivedId;
	private Long aggregatorId;
	private Date aggregatorPaymentReceivedDate;
	private Double receivedAmount;
	private Long companyBankId;
	private String notes;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	private String balajiBankName;
	private String simpleFormatDate;
	private Double totalAmount;
}

