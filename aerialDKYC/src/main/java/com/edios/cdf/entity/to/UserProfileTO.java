package com.edios.cdf.entity.to;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserProfileTO {
	
	private Long userID;

	private Integer accountID;
	
	private String accountName;
	
	private Long roleID;
	
	private String roleName;
	
	private Long siteID;
	
	private String siteName;
	
	
}
