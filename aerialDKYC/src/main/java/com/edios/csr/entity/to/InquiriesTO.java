package com.edios.csr.entity.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InquiriesTO implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;

	private Long customerId;
	private String nameTitle;
	private String firstName;
	private String middleName;
	private String lastName;
	private String contactNumber;
	private String emailAddress;
	private Long altContactNumber;
	private String panNo;
	private String aadharNo;
	private String status;
	private Long accountId;
	private Long siteId;
	private Long transactionCount;

}
