package com.edios.cdf.bean.security;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.entity.security.AccountEntity;
import com.edios.cdf.entity.security.RoleEntity;
import com.edios.cdf.entity.security.SiteEntity;
import com.edios.cdf.entity.security.UserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountUserBean extends AbstractBean {

	private static final long serialVersionUID = -7959006084525327336L;

	private Long accountUserID;

	private AccountBean accountID;

	private RoleBean roleID;

	private UserBean userID;

	private SiteBean siteID;

	private Long transactionCount;

	private String userIPAddress;

	private Character recordType;

	private Integer createdBy;

	private Date createdDate;

	private Integer lastModifiedBy;

	private Date lastModifiedDate;

}
