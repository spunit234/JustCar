package com.edios.cdf.bean.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.entity.to.RoleRightsTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleBean extends AbstractBean {

	private static final long serialVersionUID = -7959006084525327336L;

	private Long roleID;

	private String roleName;

	private String roleDesc;

	private String roleStatus;

	private Long transactionCount;

	private Integer createdBy;

	private String ipAddress;

	private Date createdOn;

	private Integer lastModifiedBy;

	private Date lastModifiedOn;

	private Character recordType;
	
	private List<RoleRightsTO> roleRightList = new ArrayList<RoleRightsTO>();

}
