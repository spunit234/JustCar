package com.edios.cdf.entity.to;

import com.edios.cdf.bean.AbstractBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class RoleRightsTO extends AbstractBean {

	private static final long serialVersionUID = -7959006084525327336L;

	private Long roleRightID;

	private Long roleID;

	private Integer menuID;
	
	private Integer accountID;

	private Integer parentMenuID;

	private boolean viewAccess;

	private boolean insertAccess;

	private boolean updateAccess;

	private boolean deleteAccess;

	private boolean printAccess;

	private boolean exportAccess;
	
    private Integer insertVisible;
	
	private Integer updateVisible;
	
	private Integer deleteVisible;
	
	private Integer viewVisible;
	
	private Integer printVisible;
	
	private Integer exportVisible;
	
	private String menuDesc;
	
	private String menuName;
	
	private boolean selectAll;
	
	/*@JsonProperty("data")
	private RoleData roleData;
	
	@JsonProperty("expandedIcon")
	private String expandedIcon;
	
	@JsonProperty("collapsedIcon")
	private String collapsedIcon;
	
	@JsonProperty("children")
	private List<RoleRightsTO> roleRightsList;
	
	@JsonProperty("leaf")
	private Boolean leaf;

	public List<RoleRightsTO> getRoleRightsList() {
		return roleRightsList==null ?roleRightsList= new ArrayList<>():roleRightsList ;
	}

	public void setRoleRightsList(List<RoleRightsTO> roleRightsList) {
		this.roleRightsList = roleRightsList;
	}

	public RoleData getRoleData() {
		return roleData==null ? roleData= new RoleData():roleData;
	}

	public void setRoleData(RoleData roleData) {
		this.roleData = roleData;
	}*/
	
	
	
	
}
