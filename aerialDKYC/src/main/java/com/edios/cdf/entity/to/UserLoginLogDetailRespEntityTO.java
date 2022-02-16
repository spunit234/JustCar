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
public class UserLoginLogDetailRespEntityTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6656209126465167817L;
	private Long userLoginId;
	private String userName;
	private String roleName;
	private String screenName;
	private String activityName;
	private Date activityDateTime;
}
