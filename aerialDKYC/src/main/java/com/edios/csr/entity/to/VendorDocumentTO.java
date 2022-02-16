package com.edios.csr.entity.to;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VendorDocumentTO {
private Long vendorDocumentID;
private String documentType;
private String documentName;
private String documentNumber;
private String documentDesc;
private Long viewDocument;// documentLocationID
private String uploadDate;// createdDate and modifiedDate
private Long transactionCount;
private String fileName;
private Long locationId;

//
private Long activityDocumentId;

private Long projectDocumentId;

private Long aggregatorDocumentId;



}
