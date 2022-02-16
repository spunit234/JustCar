package com.edios.cdf.bean.security;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.entity.security.MenuEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleRightBean extends AbstractBean {

	private static final long serialVersionUID = -7959006084525327336L;
	
	private Long roleRightID;

	private RoleBean roleID= new RoleBean();

	private MenuEntity menuID= new MenuEntity();

	private boolean viewAccess;

	private boolean insertAccess;

	private boolean updateAccess;

	private boolean deleteAccess;

	private boolean printAccess;

	private boolean exportAccess;

	private Long transactionCount;

	private String userIPAddress;

	private Character recordType;

	private Integer createdBy;

	private Date createdDate;

	private Integer lastModifiedBy;

	private Date lastModifiedDate;

}
