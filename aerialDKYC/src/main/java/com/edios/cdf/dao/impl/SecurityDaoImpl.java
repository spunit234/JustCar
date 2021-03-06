package com.edios.cdf.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.SecurityDao;
import com.edios.cdf.entity.security.UserEntity;
import com.edios.cdf.entity.security.UserLoginDetailsEntity;
import com.edios.cdf.entity.to.AccountUserEntityTO;
import com.edios.cdf.entity.to.MenuEntityTO;
import com.edios.cdf.entity.to.RoleRightsEntityTO;
import com.edios.cdf.entity.to.UserDetailTO;
import com.edios.cdf.entity.to.UserEntityTO;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.util.WebAppied;

@Repository
public class SecurityDaoImpl extends BaseDaoImpl<UserEntity> implements SecurityDao {

	@SuppressWarnings({ "deprecation" })
	@Override
	public UserDetailTO fetchUserDetails(String userName) {
		UserDetailTO userDetailTO = null;
		List<UserDetailTO> user = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select u.userID As userID , u.firstName As firstName , u.lastName As lastName , a.licenseStartDate As licenseStartDate , "
					+ " a.licenseExpiryDate As licenseExpiryDate , u.loginName As loginName , "
					+ " u.password As password ,u.recordType As recordType , u.passwordResetFlag As passwordResetFlag ,"
					+ " u.loginFailedTries As loginFailedTries , "
					+ " a.accountID as accountID  , u.userStatus As userStatus ,u.userType as userType ,"
					+ " CURRENT_TIMESTAMP  AS currentDate,u.emailPassword as emailPassword  from  UserEntity u  LEFT JOIN u.accountID as a "
					+ " where u.loginName=:userName";
			userDetailTO = (UserDetailTO) session.createQuery(sqlQuery).setParameter("userName", userName)
					.setResultTransformer(Transformers.aliasToBean(UserDetailTO.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return userDetailTO;
	}

	@Override
	public boolean updateLoginFailedTries(Long userID, int loginFlag) {
		int result = 0;
		try {
			Session session = (Session) entityManager.getDelegate();
			result = session
					.createQuery(
							"UPDATE UserEntity UE SET UE.loginFailedTries=" + loginFlag + " WHERE UE.userID=" + userID)
					.executeUpdate();
		} catch (Exception exception) {
			return false;
		}
		return true;
	}

	@Override
	public boolean updateUserStatusLocked(Long userID, String userStatus) {
		int result = 0;
		try {
			Session session = (Session) entityManager.getDelegate();
			result = session.createQuery("UPDATE UserEntity UE SET UE.userStatus=:userStatus WHERE UE.userID=" + userID)
					.setParameter("userStatus", userStatus).executeUpdate();
		} catch (Exception exception) {
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<MenuEntityTO> fetchRoleRightsMenus(Integer accountID) {
		Session session = (Session) entityManager.getDelegate();
		try {
			return (List<MenuEntityTO>) session.createQuery(
					"SELECT  ME.menuID as menuID,ME.menuDesc as menuDesc,ME.menuSequence as menuSequence,"
							+ " ME.menuIcon as menuIcon, ME.menuActiveOption as menuActiveOption,"
							+ " ME.pageUrl as pageUrl, ME.parentMenuID as parentMenuID "
							+ " FROM MenuEntity ME "
							+ " WHERE ME.accountID =:accountID and ME.menuStatus='Active' "
							+ " and ME.parentMenuID=null "
							+ " order by ME.menuSequence asc")
					.setParameter("accountID",accountID)
					.setResultTransformer(Transformers.aliasToBean(MenuEntityTO.class)).list();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<MenuEntityTO> fetchAccountMenus(Long roleID) {
		Session session = (Session) entityManager.getDelegate();
		try {
			System.out.println("roleID---------->>>"+roleID);
			return session.createQuery(
					"SELECT  menu.menuID as menuID, menu.menuDesc as menuDesc, menu.menuSequence as menuSequence,"
							+ " menu.menuIcon as menuIcon,menu.menuActiveOption as menuActiveOption,"
							+ " menu.pageUrl as pageUrl,menu.parentMenuID as parentMenuID FROM RolesRightEntity RRE "
							+ " left join RRE.menuID as menu left join RRE.roleID as role"
							+ " WHERE menu.menuStatus=:menuStatus AND role.roleID =:roleID "
							+ " order by menu.menuSequence asc")
					.setParameter("roleID", roleID).setParameter("menuStatus", "Active")
					.setResultTransformer(Transformers.aliasToBean(MenuEntityTO.class)).list();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public UserDetailTO getCurrentUser(String userName) {
		UserDetailTO userDetailTO = null;
		List<UserDetailTO> user = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select u.userID As userID , u.firstName As firstName , u.lastName As lastName , u.defaultRoleID as defaultRoleID,"
					+ "  u.loginName As loginName , a.accountID as accountID,u.defaultTheme as defaultTheme,u.sessionID as sessionID,u.defaultSiteID as defaultSiteID ,"
					+ "u.sessionTimeoutMin as sessionTimeoutMin,u.passwordExpiryDate as passwordExpiryDate,u.passwordExpiryDays as passwordExpiryDays "
					+ " from  UserEntity u  LEFT JOIN u.accountID as a " + "  where u.loginName=:userName";

			userDetailTO = (UserDetailTO) session.createQuery(sqlQuery).setParameter("userName", userName)
					.setResultTransformer(Transformers.aliasToBean(UserDetailTO.class)).list().get(0);
		} catch (Exception exception) {
			return null;
		}
		return userDetailTO;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<AccountUserEntityTO> fetchAccounts(Long userID) {
		List<AccountUserEntityTO> accountUserBeanList = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select aue.accountUserID As accountUserID,"
					+ " a.accountID As accountID ,a.accountName as accountName,"
					+ " role.roleID As roleID, role.roleName as roleName,"
					+ " user.userID as userID, user.loginName as loginName  "
					+ " from  AccountUserEntity aue  LEFT JOIN aue.accountID as a"
					+ " left join aue.roleID as role left join aue.userID as user " + " where user.userID=:userID";

			accountUserBeanList = session.createQuery(sqlQuery).setParameter("userID", userID)
					.setResultTransformer(Transformers.aliasToBean(AccountUserEntityTO.class)).list();
		} catch (Exception exception) {
			return null;
		}
		return accountUserBeanList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public RoleRightsEntityTO fetchRoleRights(RoleRightsEntityTO roleRightsEntityTO) {
		Session session = (Session) entityManager.getDelegate();
		try {
			return (RoleRightsEntityTO) session.createQuery(
					"SELECT  RRE.roleRightID as roleRightID,RRE.insertAccess as insertAccess,RRE.updateAccess as updateAccess,"
							+ " RRE.deleteAccess as deleteAccess, RRE.viewAccess as viewAccess, RRE.exportAccess as exportAccess "
							+ " FROM RolesRightEntity RRE "
							+ " left join RRE.menuID as menu left join RRE.roleID as role"
							+ " WHERE role.roleID =:roleID and  menu.menuID =:menuID and menu.menuStatus='Active' "
							+ " order by menu.menuSequence asc")
					.setParameter("roleID", roleRightsEntityTO.getRoleID())
					.setParameter("menuID", roleRightsEntityTO.getMenuID())
					.setResultTransformer(Transformers.aliasToBean(RoleRightsEntityTO.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer getMenuId(RoleRightsEntityTO roleRightsEntityTO) {
		Session session = (Session) entityManager.getDelegate();
		try {
			return (Integer) session.createQuery("select ME.menuID from MenuEntity ME "
					+ " where ME.menuName = :menuName"
					+ " and ME.accountID=:accountID")
					.setParameter("menuName", roleRightsEntityTO.getMenuName()).setParameter("accountID", roleRightsEntityTO.getAccountId()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean updateDefaultThemeForUser(PayloadBean payloadBean) {
			Session session = (Session) entityManager.getDelegate();
			session
					.createQuery(
							"UPDATE UserEntity UE SET UE.defaultTheme='" + payloadBean.getSearchParameter() + "',UE.lastModifiedBy="+payloadBean.getId()+",UE.lastModifiedDate='"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+"',UE.userIPAddress='"+payloadBean.getCustomParameter()+"' WHERE UE.userID=" + payloadBean.getId())
					.executeUpdate();
		
		return true;
	}

	@Override
	public Long insertUserLoginDetails(UserLoginDetailsEntity userLoginDetailsEntity) {
		Long pkOfUser=null;
		try {
			Session session = (Session) entityManager.getDelegate();
			 pkOfUser = (Long) session.save(userLoginDetailsEntity);
			if (pkOfUser != null) {
				return pkOfUser;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return 0L;
		}

		return pkOfUser;
	}

	@Override
	public Long updateUserLoginDetails(PayloadBean payloadBean) {
		Long result = 0L;
		try {
			Session session = (Session) entityManager.getDelegate();
			result = (long) session
					.createQuery(
							"UPDATE UserLoginDetailsEntity UE SET UE.logoutReason= :logoutReason,UE.logoutDateTime= :logoutDateTime WHERE UE.userLoginId= :userLoginId")
					.setParameter("logoutReason", payloadBean.getLogoutReason()).setParameter("logoutDateTime", new Date(),TemporalType.TIMESTAMP).setParameter("userLoginId", payloadBean.getLoginDetailId()).executeUpdate();
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return 0L;
		}
		return result;
	}


	@Override
	public Long resetUserPassword(UserEntityTO userEntityTO, String ipAddress) {
		Long result = 0L;
		WebAppied webapied = new WebAppied();
		try {
			Session session = (Session) entityManager.getDelegate();
			String encodedPassword = webapied.encrypt(userEntityTO.getNewPassword());
			result = (long) session
					.createQuery(
							"UPDATE UserEntity  SET password=:password,userIPAddress=:userIPAddress,lastModifiedBy=:lastModifiedBy,"
							+ "lastModifiedDate=:lastModifiedDate,passwordExpiryDate=:passwordExpiryDate,passwordChangedDate=:passwordChangedDate,emailPassword=null"
							+ " WHERE userID=:userID")
					.setParameter("password", encodedPassword)
					.setParameter("userIPAddress", ipAddress)
					.setParameter("lastModifiedBy",(int) userEntityTO.getUserID().longValue())
					.setParameter("lastModifiedDate", new Date(),TemporalType.TIMESTAMP)
					.setParameter("userID", userEntityTO.getUserID())
					.setParameter("passwordExpiryDate", userEntityTO.getPasswordExpiryDate())
					.setParameter("passwordChangedDate", new Date(),TemporalType.TIMESTAMP).executeUpdate();
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return 0L;
		}
		return result;
	}
	
	
	
	@Override
	public void updateUserSessionId(Long userId, String sessionID) {
		Session session = (Session) entityManager.getDelegate();
		 session.createQuery("UPDATE UserEntity UE SET UE.sessionID= :sessionID where UE.userID= :userID")
				.setParameter("sessionID", sessionID).setParameter("userID", userId).executeUpdate();
	}

	@Override
	public void updateSessionToNull(PayloadBean payloadBean) {
		try {
		Session session = (Session) entityManager.getDelegate();
		System.out.println(payloadBean.getUserId());
		
		 session.createQuery("UPDATE UserEntity UE SET UE.sessionID=:sessionID,"
		 		+ "UE.lastModifiedBy=:lastModifiedBy,UE.lastModifiedDate=:lastModifiedDate where UE.userID= :userID")
				.setParameter("sessionID", null)
				.setParameter("userID", payloadBean.getUserId())
				.setParameter("lastModifiedBy",(int) payloadBean.getUserId().longValue())
				.setParameter("lastModifiedDate", new Date(),TemporalType.TIMESTAMP).executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public UserEntityTO validateUsernameEmailAddress(UserEntityTO payloadBean) {
		Session session = (Session) entityManager.getDelegate();
		String sqlQuery = "select u.userID As userID from  UserEntity u where u.loginName=:userName and u.emailAddress=:emailAddress";
		return (UserEntityTO) session.createQuery(sqlQuery).setParameter("userName", payloadBean.getUsername()).setParameter("emailAddress", payloadBean.getEmailAddress())
				.setResultTransformer(Transformers.aliasToBean(UserEntityTO.class)).uniqueResult();
	}

	@Override
	public Long updateForgotPassword(UserEntityTO userEntityTO, String ipAddress) {
		Long result = 0L;
		WebAppied webapied = new WebAppied();
		try {
			Session session = (Session) entityManager.getDelegate();
			String encodedPassword = webapied.encrypt(userEntityTO.getNewPassword());
			result = (long) session
					.createQuery(
							"UPDATE UserEntity  SET password=:password,userIPAddress=:userIPAddress,lastModifiedBy=:lastModifiedBy,"
							+ "lastModifiedDate=:lastModifiedDate,emailPassword=null"
							+ " WHERE userID=:userID")
					.setParameter("password", encodedPassword)
					.setParameter("userIPAddress", ipAddress)
					.setParameter("lastModifiedBy",(int) userEntityTO.getUserID().longValue())
					.setParameter("lastModifiedDate", new Date(),TemporalType.TIMESTAMP)
					.setParameter("userID", userEntityTO.getUserID())
					.executeUpdate();
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return 0L;
		}
		return result;
	}
	
	@Override
	public Long updatEmailPassword(UserEntityTO userEntityTO, String ipAddress) {
		Long result = 0L;
			Session session = (Session) entityManager.getDelegate();
			result = (long) session
					.createQuery(
							"UPDATE UserEntity  SET emailPassword=:emailPassword,userIPAddress=:userIPAddress,lastModifiedBy=:lastModifiedBy,"
							+ "lastModifiedDate=:lastModifiedDate"
							+ " WHERE userID=:userID")
					.setParameter("emailPassword", userEntityTO.getEmailPassword())
					.setParameter("userIPAddress", ipAddress)
					.setParameter("lastModifiedBy",(int) userEntityTO.getUserID().longValue())
					.setParameter("lastModifiedDate", new Date(),TemporalType.TIMESTAMP)
					.setParameter("userID", userEntityTO.getUserID()).executeUpdate();
			
		return result;
	}

	@Override
	public UserDetailTO getSiteName(Long id) {
		UserDetailTO userDetailTO = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select s.siteName As siteName from  SiteEntity s  where s.siteID=:siteID";

			userDetailTO = (UserDetailTO) session.createQuery(sqlQuery).setParameter("siteID",id)
					.setResultTransformer(Transformers.aliasToBean(UserDetailTO.class)).list().get(0);
		} catch (Exception exception) {
			return null;
		}
		return userDetailTO;
	}

}
