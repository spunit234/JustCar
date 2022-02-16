package com.edios.cdf.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.dao.RoleRightsDao;
import com.edios.cdf.entity.security.RolesRightEntity;
import com.edios.cdf.entity.to.RoleRightsTO;
import com.edios.cdf.manager.RoleRightsManager;

@Service
public class RoleRightsManagerImpl extends AbstractManagerImpl<RoleRightsTO, RolesRightEntity>
		implements RoleRightsManager {

	@Autowired
	RoleRightsDao roleRightsDao;

	@Override
	@Transactional("db1Tx")
	public List<RoleRightsTO> fetchRoleRightsMenus() {
			return roleRightsDao.fetchRoleRightsMenus();

	}

	@Override
	@Transactional("db1Tx")
	public List<RoleRightsTO> fetchRoleRightsByRole(Long roleID) {
		return roleRightsDao.fetchRoleRightsByRole(roleID);
	}
}

/*
 * @Override
 * 
 * @Transactional("db1Tx") public List<RoleRightsTO> fetchRoleRightsMenus() { try {
 * List<RoleRightsTO> MenuEntityTOList = roleRightsDao.fetchRoleRightsMenus();
 * List<RoleRightsTO> parentMenu = new ArrayList<>(); for (RoleRightsTO menuList
 * : MenuEntityTOList) { if (menuList.getParentMenuID() == null) { RoleRightsTO
 * menuEntityTO = new RoleRightsTO();
 * menuEntityTO.setMenuID(menuList.getMenuID());
 * menuEntityTO.getRoleData().setMenuDesc(menuList.getMenuDesc());
 * menuEntityTO.getRoleData().setParent(true); parentMenu.add(menuEntityTO); }
 * else { for (RoleRightsTO menus : parentMenu) { if
 * (menus.getMenuID().equals(menuList.getParentMenuID())) { RoleRightsTO
 * menuEntityTO = new RoleRightsTO();
 * menuEntityTO.setMenuID(menuList.getMenuID());
 * menuEntityTO.getRoleData().setMenuDesc(menuList.getMenuDesc());
 * menuEntityTO.getRoleData().setMenuDesc(menuList.getMenuDesc());
 * menuEntityTO.getRoleData().setInsertVisible(menuList.getInsertVisible());
 * menuEntityTO.getRoleData().setUpdateVisible(menuList.getUpdateVisible());
 * menuEntityTO.getRoleData().setDeleteVisible(menuList.getDeleteVisible());
 * menuEntityTO.getRoleData().setViewVisible(menuList.getViewVisible());
 * menuEntityTO.getRoleData().setPrintVisible(menuList.getPrintVisible());
 * menuEntityTO.getRoleData().setExportVisible(menuList.getExportVisible());
 * menus.getRoleRightsList().add(menuEntityTO); } } } } return parentMenu; }
 * catch (Exception exception) { exception.printStackTrace(); return null; } }
 */


