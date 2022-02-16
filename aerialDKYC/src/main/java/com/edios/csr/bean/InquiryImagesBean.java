package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.bean.security.StorageLocationBean;
import com.edios.csr.entity.InquiriesEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InquiryImagesBean extends AbstractBean {
	private static final long serialVersionUID = -8373386124725597640L;
	private Long inquiryVehImageId;
	private Long documentName;
	private String fileName;
	private String ipAddress;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	private Character recordType;
	private Long transactionCount;
	private Long userActivityId;
	private Long createdBy;
	private Date createdDate;
	private InquiriesEntity inquiryId;
	private StorageLocationBean storageLocationId;
}
