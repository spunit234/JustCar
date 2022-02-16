package com.edios.cdf.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.RoleRightsDao;
import com.edios.cdf.entity.security.RolesRightEntity;
import com.edios.cdf.entity.to.RoleRightsTO;

@Repository
public class RoleRightsDaoImpl extends BaseDaoImpl<RolesRightEntity> implements RoleRightsDao {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<RoleRightsTO> fetchRoleRightsMenus() {
		Session session = (Session) entityManager.getDelegate();
		try {
			return (List<RoleRightsTO>) session.createQuery(
					"SELECT  ME.parentMenuID as parentMenuID,"
							+ " ME.insertAccess as insertVisible,ME.updateAccess as updateVisible,"
							+ " ME.deleteAccess as deleteVisible, ME.viewAccess as viewVisible, ME.exportAccess as exportVisible,ME.printAccess as printVisible,"
							+ " ME.menuName as menuName, ME.menuDesc as menuDesc, ME.menuID as menuID, ME.accountID as accountID  "
							+ " FROM MenuEntity ME "
							+ " WHERE ME.accountID =:accountID and ME.menuStatus='Active' "
							+ " order by ME.menuSequence asc")
					.setParameter("accountID",1)
					.setResultTransformer(Transformers.aliasToBean(RoleRightsTO.class)).list();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<RoleRightsTO> fetchRoleRightsByRole(Long roleID) {
		Session session = (Session) entityManager.getDelegate();
		try {
			return (List<RoleRightsTO>) session.createQuery(
					"SELECT  RRE.roleRightID as roleRightID,"
							+ " RRE.insertAccess as insertAccess,RRE.updateAccess as updateAccess,"
							+ " RRE.deleteAccess as deleteAccess, RRE.viewAccess as viewAccess, RRE.exportAccess as exportAccess,RRE.printAccess as printAccess,"
							+ " ME.menuName as menuName, ME.menuDesc as menuDesc, ME.menuID as menuID, ME.accountID as accountID  "
							+ " FROM RolesRightEntity RRE left join RRE.menuID as ME left join RRE.roleID as role"
							+ " WHERE role.roleID =:roleID "
							+ " order by RRE.roleRightID asc")
					.setParameter("roleID",roleID)
					.setResultTransformer(Transformers.aliasToBean(RoleRightsTO.class)).list();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

}
