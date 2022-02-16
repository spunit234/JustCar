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
public class MailDataTO implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;


	private String fullName;
	private String inquiryNo;
	private String inquiryDate;
	private String inquiryType;
	private String emailAddress;
	private String emailAddress1;
	private String contactNumber;
	private String customerEmail;
	private String assigneeContact;
	private String assigneeName;

}
