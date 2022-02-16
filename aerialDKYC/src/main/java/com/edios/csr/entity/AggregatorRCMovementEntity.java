
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
import com.edios.csr.bean.AggregatorRCMovementBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@DynamicUpdate
@Table(name = "aggregator_rcmovement")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggregatorRCMovementEntity extends AbstractEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AGGREGATOR_RCMOVEMENT_ID")
	private Long aggregatorRCMovementId;
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "AGGREGATOR_ID")
	private AggregatorsEntity aggregatorId;
	
	
	
	@Column(name = "RCMOVEMENT_NAME")
	private String rcMovementName;
	
	
	
	@Column(name = "RCMOVEMENT_DATE")
	private Date rcMovementDate;
	
	@Column(name = "DESPATCH_NOS")
	private String dispatchNos;
	
	@Column(name = "DESPATCH_REMARKS")
	private String dispatchRemarks;
	
	@Column(name = "RC_MOVEMENT_NOTES")
	private String rcMovementNotes;
	
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

	public void setUpdatedDetails(AggregatorRCMovementBean rcMov) {
		this.rcMovementDate = rcMov.getRcMovementDate();
		this.rcMovementName = rcMov.getRcMovementName();
		this.dispatchNos = rcMov.getDispatchNos();
		this.dispatchRemarks = rcMov.getDispatchRemarks();
		this.rcMovementNotes = rcMov.getRcMovementNotes();
	}
	
	
	
	
	
	
	

}
