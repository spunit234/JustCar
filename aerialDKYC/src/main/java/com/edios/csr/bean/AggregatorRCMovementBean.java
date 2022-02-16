package com.edios.csr.bean;

import java.util.Date;

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
public class AggregatorRCMovementBean extends AbstractBean {
	
	private Long aggregatorRCMovementId;

	private AggregatorsEntity aggregatorId;

	private String rcMovementName;
	
	private Date rcMovementDate;
	
	private String dispatchNos;
	
	private String dispatchRemarks;
	
	private int transactionCount;
	
	private String ipAddress;
	
	private char recordType;
	
	private int createdBy;
	
	private Date createdDate;
	
	private String rcMovementNotes;
	
	private int lastModifiedBy;
	
	private Date lastModifiedDate;
	
	private String rcMovementDateStr;
	
}
