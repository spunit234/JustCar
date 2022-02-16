package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.bean.security.StorageLocationBean;
import com.edios.cdf.display.ParameterDropDownBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProjectDocumentBean extends AbstractBean {
	private static final long serialVersionUID = -8373386124725597640L;

	private Long projectDocumentId;

	private String documentDesc;

	private String documentName;

	private String documentNumber;

	private ParameterDropDownBean documentTypeListId;

	private String fileName;

	private Long project;

	private StorageLocationBean storageLocation;

	private Long transactionCount;

	private String ipAddress;

	private Long userActivityId;

	private Character recordType;

	private Integer createdBy;

	private Date createdDate;

	private Integer lastModifiedBy;

	private Date lastModifiedDate;

	private Long aggregatorId;
	
	private Long vehicleInsuranceId;
	

}
