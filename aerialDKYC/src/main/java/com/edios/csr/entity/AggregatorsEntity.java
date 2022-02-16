
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
import com.edios.csr.bean.AggregatorsBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicUpdate
@Table(name = "aggregators")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggregatorsEntity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AGGREGATOR_ID")
	private Long aggregatorId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INQUIRY_ID")
	private InquiriesEntity inquiryId;

	@Column(name = "AGGREGATOR_DATE")
	private Date aggregatorDate;

	@Column(name = "AMOUNT_ON_HOLD")
	private Double amountOnHold;

	@Column(name = "AMOUNT_DEDUCTED_BY_RTO")
	private Double amountDeductedByRTO;

	@Column(name = "LAR_STATUS")
	private String larStatus;

	@Column(name = "LAR_BANK_DATE")
	private Date larBankDate;

	@Column(name = "EXECUTIVE_NAME")
	private String executiveName;

	@Column(name = "EXECUTIVE_PHONE")
	private String executivePhone;

	@Column(name = "AGGREGATOR_STATUS")
	private String aggregatorStatus;

	@Column(name = "TRANSACTION_COUNT")
	private Integer transactionCount;

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

	public void setUpdatedDetails(AggregatorsBean agg) {
		this.aggregatorType = agg.getAggregatorType();
		this.amountOnHold = agg.getAmountOnHold();
		this.amountDeductedByRTO = agg.getAmountDeductedByRTO();
		this.larBankDate = agg.getLarBankDate();
		this.larStatus = agg.getLarStatus();
		this.executiveName = agg.getExecutiveName();
		this.executivePhone = agg.getExecutivePhone();
	}

	@Column(name = "AMOUNT_RECEIVED_FROM_BANK")
	private Double amountReceivedFromBank;

	@Column(name = "AMOUNT_DIST_TO_CUSTOMER")
	private Double amountDisbToCustomer;

	@Column(name = "PENDING_AMOUNT_TO_DIST")
	private Double pendingAmountToDist;

	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "AGGREGATOR_TYPE")
	private String aggregatorType;
}
