package com.edios.cdf.entity.to;

import com.edios.cdf.bean.AbstractBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleRightsEntityTO extends AbstractBean {

	private static final long serialVersionUID = -7959006084525327336L;

	private Long roleRightID;

	private Long roleID;

	private Integer menuID;

	private boolean viewAccess;

	private boolean insertAccess;

	private boolean updateAccess;

	private boolean deleteAccess;

	private boolean printAccess;

	private boolean exportAccess;

	private String menuName;

	private Integer accountId;

}
