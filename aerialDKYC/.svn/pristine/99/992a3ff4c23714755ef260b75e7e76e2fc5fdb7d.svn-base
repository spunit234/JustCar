package com.edios.cdf.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.bean.security.RoleBean;
import com.edios.cdf.bean.security.RoleRightBean;
import com.edios.cdf.dao.RoleDao;
import com.edios.cdf.entity.security.RoleEntity;
import com.edios.cdf.entity.security.RolesRightEntity;
import com.edios.cdf.entity.to.MenuEntityTO;
import com.edios.cdf.entity.to.RoleEntityTO;
import com.edios.cdf.entity.to.RoleRightsTO;
import com.edios.cdf.manager.RoleManager;
import com.edios.cdf.util.AppConstants;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.TransactionData;
import com.mchange.v2.sql.filter.SynchronizedFilterDataSource;

@Service("roleManager")
public class RoleManagerImpl extends AbstractManagerImpl<RoleBean, RoleEntity> implements RoleManager {

	@Autowired
	RoleDao roleDao;

	@Override
	@Transactional
	public List<RoleEntityTO> fetchRoles(Integer accountId) {
		return roleDao.fetchRoles(accountId);
	}

	@Override
	@Transactional
	public List<RoleEntityTO> fetchRolesOnCriteria(String searchParameter, Integer accountId) {
		return roleDao.fetchRoles(searchParameter, accountId);
	}

	@Override
	@Transactional
	public String addRole(RoleBean roleBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = roleDao.isRoleNameExist(roleBean.getRoleName());
		if (resultFlag) {
			return "NameAlreadyExist";
		}
		setAuditInfo(roleBean, "newFlag");
		RoleEntity roleEntity = mapper.map(roleBean, RoleEntity.class);
		for (RoleRightsTO roleRightsTO : roleBean.getRoleRightList()) {
			RolesRightEntity roleRightBean = new RolesRightEntity();
			roleRightBean.getMenuID().setMenuID(roleRightsTO.getMenuID());
			roleRightBean.setInsertAccess(roleRightsTO.isInsertAccess());
			roleRightBean.setUpdateAccess(roleRightsTO.isUpdateAccess());
			roleRightBean.setDeleteAccess(roleRightsTO.isDeleteAccess());
			roleRightBean.setViewAccess(roleRightsTO.isViewAccess());
			roleRightBean.setPrintAccess(roleRightsTO.isPrintAccess());
			roleRightBean.setExportAccess(roleRightsTO.isExportAccess());
			roleRightBean.setCreatedBy(roleBean.getCreatedBy());
			roleRightBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			roleRightBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			roleRightBean.setCreatedDate(new Date());
			roleRightBean.setRoleID(roleEntity);
			roleEntity.getRoleRight().add(roleRightBean);
		}

		resultFlag = roleDao.addRole(roleEntity);
		if (resultFlag) {
			return "ADDED";
		}

		return resultString;
	}

	private void setAuditInfo(RoleBean roleBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			roleBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			roleBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			roleBean.setCreatedOn(new Date());
		} else {
			roleBean.setTransactionCount(roleBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			roleBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			roleBean.setLastModifiedOn(new Date());
		}

	}

	@Override
	@Transactional
	public RoleBean findRoleById(Long id, Integer accountId) {
		RoleBean roleBean = null;
		RoleEntity roleEntity = roleDao.findRoleById(id, accountId);
		if (roleEntity instanceof RoleEntity)
			roleBean = mapper.map(roleEntity, RoleBean.class);
		return roleBean;
	}

	@Override
	@Transactional
	public synchronized String updateRole(RoleBean roleBean) {
		String resultString = "";
		boolean resultFlag = false;

		resultFlag = roleDao.isRoleNameExist(roleBean.getRoleName(), roleBean.getRoleID());
		if (resultFlag) {
			return "NameAlreadyExist";
		}
		
		TransactionData latestData = roleDao.fetchTransactionDataById(roleBean.getRoleID());
		if (latestData.getTransactionCount() > (roleBean.getTransactionCount())) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}
        //Delete Role Rights on the basis of Role   
		resultFlag=roleDao.deleteRoleRights(roleBean.getRoleID());
		if(resultFlag) {
			setAuditInfo(roleBean, "editFlag");
			RoleEntity roleEntity = mapper.map(roleBean, RoleEntity.class);
			for (RoleRightsTO roleRightsTO : roleBean.getRoleRightList()) {
				RolesRightEntity roleRightBean = new RolesRightEntity();
				roleRightBean.getMenuID().setMenuID(roleRightsTO.getMenuID());
				roleRightBean.setInsertAccess(roleRightsTO.isInsertAccess());
				roleRightBean.setUpdateAccess(roleRightsTO.isUpdateAccess());
				roleRightBean.setDeleteAccess(roleRightsTO.isDeleteAccess());
				roleRightBean.setViewAccess(roleRightsTO.isViewAccess());
				roleRightBean.setPrintAccess(roleRightsTO.isPrintAccess());
				roleRightBean.setExportAccess(roleRightsTO.isExportAccess());
				roleRightBean.setCreatedBy(roleBean.getCreatedBy());
				roleRightBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
				roleRightBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
				roleRightBean.setCreatedDate(new Date());
				roleRightBean.setRoleID(roleEntity);
				roleEntity.getRoleRight().add(roleRightBean);
			}
			resultFlag = roleDao.updateRole(roleEntity);
			if (resultFlag) {
				return "UPDATED";
			}
			return resultString;
			
		}
		/*
		 * resultFlag =
		 * siteDao.isSiteCodeExists(siteBean.getSiteCode(),siteBean.getSiteID()); if
		 * (resultFlag) { return "CodeAlreadyExist"; }
		 */

		// Optimistic Locking for Hibernate
		
		return resultString;
	}

	@Override
	@Transactional
	public synchronized String deleteRole(DeleteRecords deleteRecords) {
		String resultString = "";
		boolean resultFlag = false;
		TransactionData latestData = roleDao.fetchTransactionDataById(deleteRecords.getId());
		if (latestData.getTransactionCount() > (deleteRecords.getTransactionCount())) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}
		
		resultFlag = roleDao.isRoleExists(deleteRecords.getId());
		if (resultFlag) 
			return "ROLE_EXISTS";
		
		resultFlag = roleDao.deleteRole(deleteRecords.getId(), deleteRecords.getModifiedBy());
		if (resultFlag)
			return "DELETED";

		return resultString;
	}

	@Override
	@Transactional
	public List<MenuEntityTO> fetchAccountMenus() {
		try {
			List<MenuEntityTO> menuEntityTOList = roleDao.fetchAccountMenus();
			List<MenuEntityTO> parentMenu = new ArrayList<>();
			for (MenuEntityTO menuList : menuEntityTOList) {
				if (menuList.getParentMenuID() == null) {
					MenuEntityTO menuEntityTO = new MenuEntityTO();
					menuEntityTO.setMenuID(menuList.getMenuID());
					menuEntityTO.setMenuDesc(menuList.getMenuDesc());
					menuEntityTO.setPageUrl(menuList.getPageUrl());
					menuEntityTO.setMenuIcon(menuList.getMenuIcon());
					menuEntityTO.setMenuActiveOption(menuList.getMenuActiveOption());
					parentMenu.add(menuEntityTO);
				} else {
					for (MenuEntityTO menus : parentMenu) {
						if (menus.getMenuID().equals(menuList.getParentMenuID())) {
							MenuEntityTO menuEntityTO = new MenuEntityTO();
							menuEntityTO.setMenuDesc(menuList.getMenuDesc());
							menuEntityTO.setPageUrl(menuList.getPageUrl());
							menuEntityTO.setMenuIcon(menuList.getMenuIcon());
							menuEntityTO.setMenuActiveOption(menuList.getMenuActiveOption());
							menus.getItems().add(menuEntityTO);
						}
					}
				}
			}
			return parentMenu;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}

	}

}
