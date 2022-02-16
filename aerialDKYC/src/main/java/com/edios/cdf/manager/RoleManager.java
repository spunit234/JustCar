package com.edios.cdf.manager;

import java.util.List;

import com.edios.cdf.bean.security.RoleBean;
import com.edios.cdf.entity.to.MenuEntityTO;
import com.edios.cdf.entity.to.RoleEntityTO;
import com.edios.cdf.util.DeleteRecords;

public interface RoleManager extends AbstractManager {
	List<RoleEntityTO>  fetchRoles(Integer accountId);

	List<RoleEntityTO> fetchRolesOnCriteria(String searchParameter, Integer accountId);

	String addRole(RoleBean roleBean);

	RoleBean findRoleById(Long id, Integer accountId);

	String updateRole(RoleBean roleBean);

	String deleteRole(DeleteRecords deleteRecords);

	List<MenuEntityTO> fetchAccountMenus();

}
