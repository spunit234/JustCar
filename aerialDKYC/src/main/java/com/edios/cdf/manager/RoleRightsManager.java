package com.edios.cdf.manager;

import java.util.List;

import com.edios.cdf.entity.to.RoleRightsTO;

public interface RoleRightsManager extends AbstractManager{
	
	public List<RoleRightsTO> fetchRoleRightsMenus();

	public List<RoleRightsTO> fetchRoleRightsByRole(Long roleID);

}
