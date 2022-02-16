package com.edios.csr.entity.to;

import java.util.Date;

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
public class InquiryDocumentTO {
	private Long inquiryVehImageId;
	private String documentName;
	private String fileName;
	private String ipAddress;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	private Character recordType;
	private Long transactionCount;
	private Long userActivityId;
	private Long createdBy;
	private String createdDate;
	private Long viewDocument;// documentLocationID
	private InquiriesEntity inquiryId;
	private StorageLocationBean storageLocationId;
}
