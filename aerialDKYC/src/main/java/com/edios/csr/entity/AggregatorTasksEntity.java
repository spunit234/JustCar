
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
import com.edios.csr.bean.AggregatorTasksBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@DynamicUpdate
@Table(name = "aggregator_tasks")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggregatorTasksEntity extends AbstractEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AGGREGATOR_TASK_ID")
	private Long aggregatorTaskId;
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "AGGREGATOR_ID")
	private AggregatorsEntity aggregatorId;
	
	@Column(name = "STAFF_ID")
	private Long staffId;
	
	@Column(name = "AGGREGATOR_TASK_NAME_ID")
	private Integer aggregatorTaskNameId;
	
	
	@Column(name = "AGGREGATOR_TASK_DATE")
	private Date aggregatorTaskDate;
	
	@Column(name = "AGGREGATOR_TASK_STATUS")
	private String aggregatorTaskStatus;
	
	@Column(name = "AGGREGATOR_TASK_CHARGES")
	private Integer aggregatorTaskCharges;
	
	@Column(name = "NOTES")
	private String notes;
	
	
	
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

	public void setUpdatedDetails(AggregatorTasksBean task) {
		this.staffId = task.getStaffId();
		this.aggregatorTaskDate = task.getAggregatorTaskDate();
		this.aggregatorTaskNameId = task.getAggregatorTaskNameId();
		this.aggregatorTaskCharges = task.getAggregatorTaskCharges();
		this.aggregatorTaskStatus = task.getAggregatorTaskStatus();
		this.notes = task.getNotes();
	}
	
	
	
	
	
	
	

}
