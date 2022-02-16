package com.edios.cdf.bean.security;

import java.util.Date;

import javax.persistence.Column;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.display.ParameterDropDownBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StorageLocationBean extends AbstractBean {

	private static final long serialVersionUID = 8473987606992754343L;

	private Long locationId;

	private String locationName;

	private String locationDescription;

	private Long locationMaxSize;

	private Long locationUsageAlertPercent;

	private String locationRootPath;

	private String locationFolderName;

	private String subFolderPrefix;

	private Long maxFilesPerSubFolder;

	private String curSubFolderName;

	private Long curSubFolderFilesCount;

	private String locationStatus;

	private Long transactionCount;

	private Date createdDate;

	private Integer createdBy;

	private Date lastModifiedDate;

	private Character recordType;

	private String userIPAddress;

	private Integer lastModifiedBy;

}
