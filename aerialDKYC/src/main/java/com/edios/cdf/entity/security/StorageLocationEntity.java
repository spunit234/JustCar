package com.edios.cdf.entity.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.edios.cdf.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "storage_locations")
public class StorageLocationEntity extends AbstractEntity {

	private static final long serialVersionUID = -4361018752519562729L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="LOCATION_ID")
	private Long locationId;
	
	/*@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private AccountEntity accountID*/;
		
	@Column(name="LOCATION_NAME")
	private String locationName;
	
	@Column(name="LOCATION_DESCRIPTION")
	private String locationDescription;
	
/*	@ManyToOne
	@JoinColumn(name="STORAGE_ENTITY_TYPE_LIST_ID")
	private ApplicationParameterListEntity storageTypeListId;*/
	
	@Column(name="LOCATION_MAX_SIZE")
	private Long locationMaxSize;
	
	@Column(name="LOCATION_USAGE_ALERT_PERCENT")
	private Long locationUsageAlertPercent; 
	
	@Column(name="LOCATION_ROOT_PATH")
	private String locationRootPath;
	
	@Column(name="LOCATION_FOLDER_NAME")
	private String locationFolderName;
	
	@Column(name="SUB_FOLDER_PREFIX")
	private String subFolderPrefix;
	
	@Column(name="MAX_FILES_PER_SUB_FOLDER")
	private Long maxFilesPerSubFolder;
	
	@Column(name="CUR_SUBFOLDER_NAME" )
	private String curSubFolderName;
	
	@Column(name="CUR_SUBFOLDER_FILESCOUNT" )
	private Long curSubFolderFilesCount;
	
	@Column(name="LOCATION_STATUS" )
	private String locationStatus;
	
	@Column(name="TRANSACTION_COUNT")
	private Long transactionCount; 
	
	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Integer createdBy;

	@Column(name = "LAST_MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	@Column(name = "RECORD_TYPE")
	private Character recordType;
	
	@Column(name = "LAST_MODIFIED_BY")
	private Integer lastModifiedBy;
	
	@Column(name = "IP_ADDRESS")
	private String userIPAddress;

}
