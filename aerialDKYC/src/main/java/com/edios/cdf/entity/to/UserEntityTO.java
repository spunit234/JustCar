package com.edios.cdf.entity.to;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEntityTO implements Serializable {

	private static final long serialVersionUID = 1L;

	Long userID;
	
	Long _id;

	String loginName;

	String firstName;

	String lastName;

	String userStatus;

	String emailAddress;

	Long transactionCount;

	String fullName;
	
	String password;
	
	String oldPassword;
	
	String newPassword;
	
	String confirmNewPassword;
	
	String username;
	
	Date passwordExpiryDate;
	
	Long passwordExpiryDays;
	
	String sessionID;
	
	Long userLoginId;
	
	String emailPassword;
}
