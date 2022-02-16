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

import com.edios.cdf.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dealer_executives")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DealerExecutiveEntity2 extends AbstractEntity implements Serializable{
				
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "DEALER_EXECUTIVE_ID")
		private Long dealerExecutiveId;

		@JoinColumn(name = "DEALER_ID")
		@ManyToOne(fetch = FetchType.EAGER)
		private DealerEntity2 dealerId;

		@Column(name = "EXECUTIVE_NAME")
		private String dealerExecutiveName;

		@Column(name = "EXECUTIVE_DESIGNATION")
		private String executiveDesignation;

		@Column(name = "CONTACT_NUMBER")
		private String contactNumber;

		@Column(name = "EXECUTIVE_ADDRESS")
		private String emailAddress;

		@Column(name = "ALT_CONTACT_NUMBER")
		private Long altContactNumber;
		
		@Column(name = "EXECUTIVE_LOCATION")
		private String executiveLocation;
		
		@Column(name = "EXECUTIVE_STATUS")
		private String executiveStatus;
		
		@Column(name = "USER_ACTIVITY_ID")
		private String userActivityId;

		@Column(name = "TRANSACTION_COUNT")
		private Long transactionCount;

		@Column(name = "IP_ADDRESS")
		private String ipAddress;

		@Column(name = "RECORD_TYPE")
		private Character recordType;

		@Column(name = "CREATED_BY")
		private Long createdBy;

		@Column(name = "CREATED_DATE")
		private Date createdDate;

		@Column(name = "LAST_MODIFIED_BY")
		private Long lastModifiedBy;

		@Column(name = "LAST_MODIFIED_DATE")
		private Date lastModifiedDate;
	}



