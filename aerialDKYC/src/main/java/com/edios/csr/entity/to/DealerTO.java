package com.edios.csr.entity.to;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DealerTO {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 6342094965674122373L;
	
	private Long makeId;
	private Long dealerId;
	private String makeName;
	private String dealerName;
	private String dealerType;
	private String address;
	private String city;
	private String district;
	private String postalCode;
	private String state;
	private String country;
	private String contactNumber;
	private String altcontactNumber;
	private String emailAddress;
	private String dealerStatus;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;

}
