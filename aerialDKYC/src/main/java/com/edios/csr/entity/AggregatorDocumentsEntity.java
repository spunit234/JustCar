package com.edios.csr.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.edios.cdf.entity.AbstractEntity;
import com.edios.cdf.entity.common.ApplicationParameterListEntity;
import com.edios.cdf.entity.security.StorageLocationEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "aggregator_documents")
public class AggregatorDocumentsEntity extends AbstractEntity  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -489502549319462995L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AGGREGATOR_DOCUMENT_ID")
	private Long 	aggregatorDocumentId;

	@Column(name="CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DOCUMENT_DESC")
	private String documentDesc;

	@Column(name="DOCUMENT_NAME")
	private String documentName;

	@Column(name="DOCUMENT_NUMBER")
	private String documentNumber;

	@ManyToOne
	@JoinColumn(name="DOCUMENT_TYPE_LIST_ID")
	private ApplicationParameterListEntity documentTypeListId;

	@Column(name="FILE_NAME")
	private String fileName;

	@Column(name="IP_ADDRESS")
	private String ipAddress;

	@Column(name="LAST_MODIFIED_BY")
	private Long lastModifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_MODIFIED_DATE")
	private Date lastModifiedDate;

	@Column(name="RECORD_TYPE")
	private Character recordType;

	@Column(name="TRANSACTION_COUNT")
	private Long transactionCount;

	@Column(name="USER_ACTIVITY_ID")
	private Long userActivityId;

	//bi-directional many-to-one association to Project
	
	@Column(name="AGGREGATOR_ID")
	private Long aggregatorId;

	//bi-directional many-to-one association to StorageLocation
	@ManyToOne
	@JoinColumn(name="DOCUMENT_LOCATION_ID")
	private StorageLocationEntity storageLocation;
}
