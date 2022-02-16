package com.edios.cdf.dao;

import java.util.List;

import com.edios.cdf.entity.security.RoleEntity;
import com.edios.cdf.entity.to.MenuEntityTO;
import com.edios.cdf.entity.to.RoleEntityTO;
import com.edios.cdf.util.TransactionData;

public interface RoleDao  extends BaseDao<RoleEntity> {

	List<RoleEntityTO>  fetchRoles(Integer accountId);

	List<RoleEntityTO> fetchRoles(String searchParameter, Integer accountId);

	boolean isRoleNameExist(String roleName);

	//boolean isRoleCodeExists(String roleCode);
	
	boolean isRoleNameExist(String roleName, Long siteID);

	//boolean isRoleCodeExists(String roleCode, Long siteID);

	boolean addRole(RoleEntity siteEntity);

	RoleEntity findRoleById(Long id, Integer accountId);

	TransactionData fetchTransactionDataById(Long siteID);

	boolean updateRole(RoleEntity siteEntity);

	boolean deleteRole(Long id, Integer modifiedBy);

	List<MenuEntityTO> fetchAccountMenus();

	boolean deleteRoleRights(Long roleID);

	boolean isRoleExists(Long id);
	
}
