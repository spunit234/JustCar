package com.edios.cdf.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.RoleDao;
import com.edios.cdf.entity.security.RoleEntity;
import com.edios.cdf.entity.security.RolesRightEntity;
import com.edios.cdf.entity.to.MenuEntityTO;
import com.edios.cdf.entity.to.RoleEntityTO;
import com.edios.cdf.util.TransactionData;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<RoleEntity> implements RoleDao {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<RoleEntityTO> fetchRoles(Integer accountID) {
		List<RoleEntityTO> siteEntityTOList = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select ro.roleID As roleID , ro.roleName As roleName , ro.roleDesc As roleDesc ,"
					+ " ro.roleStatus As roleStatus ,ro.transactionCount as transactionCount"
					+ " from  RoleEntity ro  " 
					+ " where ro.recordType<>'D' and ro.roleStatus=:roleStatus "
					+ " order by ro.roleName asc";
			siteEntityTOList = (List<RoleEntityTO>) session.createQuery(sqlQuery).setParameter("roleStatus", "Active")
					.setResultTransformer(Transformers.aliasToBean(RoleEntityTO.class)).list();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return siteEntityTOList;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<RoleEntityTO> fetchRoles(String searchParameter, Integer accountID) {
		List<RoleEntityTO> RoleEntityTOList = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			if (searchParameter.equalsIgnoreCase("Active") || searchParameter.equalsIgnoreCase("InActive")) {
				sqlQuery = "select ro.roleID As roleID , ro.roleName As roleName , ro.roleDesc As roleDesc ,"
						+ " ro.roleStatus As roleStatus ,ro.transactionCount as transactionCount"
						+ " from  RoleEntity ro  " 
						+ " where ro.recordType<>'D' and ro.roleStatus=:roleStatus "
						+ " order by ro.roleName asc";
				RoleEntityTOList = (List<RoleEntityTO>) session.createQuery(sqlQuery)
						.setParameter("roleStatus", searchParameter)
						.setResultTransformer(Transformers.aliasToBean(RoleEntityTO.class)).list();
			} else {
				sqlQuery = "select ro.roleID As roleID , ro.roleName As roleName , ro.roleDesc As roleDesc ,"
						+ " ro.roleStatus As roleStatus ,ro.transactionCount as transactionCount"
						+ " from  RoleEntity ro  " 
						+ " where ro.recordType<>'D' "
						+ " order by ro.roleName asc";
				RoleEntityTOList = (List<RoleEntityTO>) session.createQuery(sqlQuery)
						.setResultTransformer(Transformers.aliasToBean(RoleEntityTO.class)).list();
			}
		} catch (NoResultException noResultException) {
			return null;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return RoleEntityTOList;
	}

	

	@Override
	public boolean addRole(RoleEntity roleEntity) {
		boolean result = true;
			entityManager.persist(roleEntity);
		return result;
	}

	@Override
	public RoleEntity findRoleById(Long roleId, Integer accountId) {
			RoleEntity roleEntity = null;
			String sqlQuery = "";
			try {
				sqlQuery = "from  RoleEntity se  where se.roleID=:roleID";
				roleEntity = (RoleEntity) entityManager.createQuery(sqlQuery)
					.setParameter("roleID", roleId).getSingleResult();
			} catch (NoResultException noResultException) {
				return null;
			} catch (Exception exception) {
				exception.printStackTrace();
				return null;
			}
			return roleEntity;
	}

	@SuppressWarnings("deprecation")
	@Override
	public TransactionData fetchTransactionDataById(Long roleID) {
		TransactionData transactionData = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select table.transactionCount as transactionCount,table.recordType as recordType "
					+ " from  RoleEntity table where table.roleID=:roleID";
			transactionData = (TransactionData) session.createQuery(sqlQuery).setParameter("roleID", roleID)
					.setResultTransformer(Transformers.aliasToBean(TransactionData.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

	@Override
	public boolean updateRole(RoleEntity roleEntity) {
		boolean result = true;
		try {
			Session session = (Session) entityManager.getDelegate();
			session.update(roleEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}
	
	@Override
	public boolean isRoleNameExist(String roleName) {
		try {
			return entityManager.createQuery(
					"select table.roleName as roleName from RoleEntity table where "
							+ " table.roleName='" + roleName+"'")
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<MenuEntityTO> fetchAccountMenus() {
		Session session = (Session) entityManager.getDelegate();
		try {
			return session.createQuery(
					"SELECT  menu.menuID as menuID,menu.menuDesc as menuDesc,menu.menuSequence as menuSequence,"
							+ " menu.menuIcon as menuIcon,menu.menuActiveOption as menuActiveOption,"
					        + " menu.pageUrl as pageUrl,menu.parentMenuID as parentMenuID"
							+ " FROM MenuEntity menu LEFT JOIN menu.menuStatusID as status "
							+ " WHERE menu.recordStatus <> 'D' AND status.statusCode <>'INACTIVE'"
							+ " order by menu.menuSequence asc")
					.setResultTransformer(Transformers.aliasToBean(MenuEntityTO.class)).list();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	@Override
	public boolean isRoleNameExist(String roleName, Long roleID) {
		try {
			return entityManager.createQuery(
					"select table.roleName as roleName from RoleEntity table where "
							+ " table.roleName='" + roleName+"' and table.roleID!="+roleID)
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

/*	@Override
	public boolean isSiteCodeExists(String siteCode, Long siteID) {
		try {
			return entityManager.createQuery(
					"select table.siteCode as siteCode from SiteEntity table where "
							+ " table.siteCode='" + siteCode+"' and table.siteID!="+siteID)
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}*/

	@Override
	public boolean deleteRole(Long deleteRecordId, Integer lastModifiedBy) {
		boolean result = true;
		try {
			String hql = "update RoleEntity se set se.lastModifiedOn=:lastModifiedDate"
					+ " , se.recordType='D' , se.lastModifiedBy=:lastModifiedBy"
					+ " WHERE se.roleID=:deleteRecordId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("lastModifiedBy", lastModifiedBy);
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("deleteRecordId", deleteRecordId);
			int resultID = query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean deleteRoleRights(Long roleID) {
		boolean result = true;
		try {
			String hql = "DELETE FROM RolesRightEntity RRE  WHERE RRE.roleID.roleID=:roleID";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("roleID", roleID);
			int resultID = query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean isRoleExists(Long id) {
		try {
			return entityManager.createQuery(
					"select table.accountUserID  from AccountUserEntity table where "
							+ " table.roleID.roleID=" + id)
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
}
