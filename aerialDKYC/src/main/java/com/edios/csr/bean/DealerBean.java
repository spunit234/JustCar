package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DealerBean extends AbstractBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long makeId;
	private Long dealerId;
	private String dealerName;
	private String makeName;
	private String dealerType;
	private String address;
	private String city;
	private String postalCode;
	private Long state;
	private String district;
	private String country;
	private String contactNumber;
	private String altContactNumber;
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
