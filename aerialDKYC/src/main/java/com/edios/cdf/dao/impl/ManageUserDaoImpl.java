package com.edios.cdf.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.edios.cdf.bean.security.UserBean;
import com.edios.cdf.dao.ManageUserDao;
import com.edios.cdf.entity.security.UserEntity;
import com.edios.cdf.entity.to.UserProfileTO;
import com.edios.cdf.util.TransactionData;

@Repository
public class ManageUserDaoImpl extends BaseDaoImpl<UserEntity> implements ManageUserDao {

	@Override
	public UserEntity findUserById(Long userID) {
		try {
			Session session = (Session) entityManager.getDelegate();
			return (UserEntity) session.get(UserEntity.class, userID);

		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<UserProfileTO> fetchAccountUserDetails(Long userID) {
		List<UserProfileTO> userProfileTO = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select accountUser.accountID.accountID as accountID,accountUser.accountID.accountName as accountName,accountUser.roleID.roleID as roleID,"
					+ "	accountUser.roleID.roleName as roleName,accountUser.siteID.siteID as siteID, accountUser.siteID.siteName as siteName  from AccountUserEntity accountUser where  accountUser.userID.userID=:userID";

			userProfileTO = (List<UserProfileTO>) session.createQuery(sqlQuery).setParameter("userID", userID)
					.setResultTransformer(Transformers.aliasToBean(UserProfileTO.class)).list();
		}

		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return userProfileTO;
	}

	@SuppressWarnings("deprecation")
	@Override
	public UserBean fetchUserById(Long userID) {

		UserBean userBean = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select user.transactionCount As transactionCount , user.recordType As recordType  from UserEntity user "
					+ "where user.userID=:userID ";

			userBean = (UserBean) session.createQuery(sqlQuery).setParameter("userID", userID)
					.setResultTransformer(Transformers.aliasToBean(UserBean.class)).getSingleResult();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return userBean;
	}

	@Override
	public boolean updateUserInfo(UserEntity userEntity) {
		boolean result = true;
		try {
			Session session = (Session) entityManager.getDelegate();
			session.merge(userEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean deleteUserAccounts(Long userID) {
		try {
			Session session = (Session) entityManager.getDelegate();
			String sqlQuery = "delete from AccountUserEntity where userID.userID=:userID ";
			Query query = session.createQuery(sqlQuery).setParameter("userID", userID);
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public TransactionData fetchTransactionDataById(Long userID) {
		TransactionData transactionData = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select user.transactionCount as transactionCount,user.recordType as recordType "
					+ " from  UserEntity user  where user.userID=:userID";

			transactionData = (TransactionData) session.createQuery(sqlQuery).setParameter("userID", userID)
					.setResultTransformer(Transformers.aliasToBean(TransactionData.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

	@Override
	public boolean deleteUser(Long userID, Integer modifiedBy) {
		boolean result = true;
		try {
			String hql = "update UserEntity user set user.lastModifiedDate=:lastModifiedDate , user.recordType='D' , user.lastModifiedBy=:lastModifiedBy"
					+ " WHERE user.userID=:userID";

			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("userID", userID);
			query.setParameter("lastModifiedBy", modifiedBy);
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			int resultID = query.executeUpdate();
			System.out.println("Rows Affected: " + resultID);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public Long addUser(UserEntity userEntity) {
		Session session = (Session) entityManager.getDelegate();
		Long pkOfUser = (Long) session.save(userEntity);
		return pkOfUser;

	}

	@Override
	public boolean checkUniqueLoginName(String userName) {
		Session session = (Session) entityManager.getDelegate();
		try {
			String sqlQuery = "select user.loginName from UserEntity user where user.loginName=:userName and user.recordType<>'D'";
			return session.createQuery(sqlQuery).setParameter("userName", userName).getSingleResult() == null ? false
					: true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			return true;
		}
	}

}
