package com.edios.cdf.bean.security;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountBean extends AbstractBean {

	private static final long serialVersionUID = -6042596735484450206L;

	private Integer accountID;

	private String accountName;

	@JsonIgnore
	private String accountStatus;

	@JsonIgnore
	private String concurrentUsers;

	@JsonIgnore
	private String namedUsers;

	@JsonIgnore
	private String licenseStartDate;

	@JsonIgnore
	private String licenseExpiryDate;

	@JsonIgnore
	private Long transactionCount;

	@JsonIgnore
	private String userIPAddress;

	@JsonIgnore
	private Character recordType;

	@JsonIgnore
	private Integer createdBy;

	@JsonIgnore
	private Date createdDate;

	@JsonIgnore
	private Integer lastModifiedBy;

	@JsonIgnore
	private Date lastModifiedDate;
}
