package com.edios.csr.entity.to;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerAddressTo implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;

	private Long customerAddId;
	private Long customerId;
	private Long loanId;
	private String addressType;
	private String houseType;
	private String city;
	private String district;
	private Long stateId;
	private String postalCode;
	private String tehsil;
	private String address;
	private String contactNumber;
	private String emailAddress;
	private String altContactNumber;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
}
