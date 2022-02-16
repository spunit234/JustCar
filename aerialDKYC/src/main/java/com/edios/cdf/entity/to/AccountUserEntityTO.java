package com.edios.cdf.entity.to;

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
public class AccountUserEntityTO implements Serializable {

	private static final long serialVersionUID = 6342094965674122373L;

	private Long accountUserID;

	private Integer accountID;

	private Long roleID;

	private Long userID;

	private Long siteID;
	
	private String accountName;
	
	private String roleName;
	
	private String loginName; 

	private String siteName;

}
