package com.edios.cdf.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.bean.security.SiteBean;
import com.edios.cdf.bean.security.UserBean;
import com.edios.cdf.dao.UserProfileDao;
import com.edios.cdf.entity.security.AccountUserEntity;
import com.edios.cdf.entity.security.UserEntity;
import com.edios.cdf.entity.to.AccountUserEntityTO;
import com.edios.cdf.entity.to.UserEntityTO;
import com.edios.cdf.entity.to.UserProfileTO;
import com.edios.cdf.manager.UserProfileManager;
import com.edios.cdf.util.AppConstants;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.util.TransactionData;
import com.edios.cdf.util.WebAppied;

@Service("userProfileManager")
public class UserProfileManagerImpl extends AbstractManagerImpl<UserBean, AccountUserEntity> implements UserProfileManager {
	
	
	@Autowired
	UserProfileDao userProfileDao;

	@Override
	@Transactional("db1Tx")
	public List<UserProfileTO> fetchDefaultAccount(Long id) {
		return userProfileDao.fetchDefaultAccount(id);
	}

	@Override
	@Transactional("db1Tx")
	public List<UserProfileTO> fetchDefaultRole(Long id) {
		return userProfileDao.fetchDefaultRole(id);
	}

	@Override
	@Transactional("db1Tx")
	public List<UserProfileTO> fetchDefaultSite(Long id) {
		return userProfileDao.fetchDefaultSite(id);
	}

	@Override
	@Transactional("db1Tx")
	public UserBean fetchUserProfileInfo(PayloadBean payloadBean) {
		UserBean userBean= null;
		UserEntity  userEntity=userProfileDao.fetchUserProfileInfo(payloadBean);
		userBean=mapper.map(userEntity, UserBean.class);
		WebAppied  appied= new WebAppied();
		userBean.setPassword(appied.decrypt(userBean.getPassword()));
		return userBean;
		
	}

	@Override
	@Transactional("db1Tx")
	public String updateUserProfile(UserBean userBean) {
		String resultString = "";
		boolean resultFlag = false;
		
		resultFlag = userProfileDao.checkUniqueLoginName(userBean.getLoginName(),userBean.getUserID());
		if (resultFlag) {
			return "NameAlreadyExist";
		}
		WebAppied  appied= new WebAppied();
		userBean.setPassword(appied.encrypt(userBean.getPassword()));
		
		// Optimistic Locking for Hibernate
		TransactionData latestData = userProfileDao.fetchTransactionDataById(userBean.getUserID());
		if (latestData.getTransactionCount() > (userBean.getTransactionCount())) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}

		setAuditInfo(userBean, "editFlag");
		UserEntity userEntity = mapper.map(userBean, UserEntity.class);
		resultFlag = userProfileDao.updateUserProfile(userEntity);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}
	@Override
	@Transactional("db1Tx")
	public List<AccountUserEntityTO> fetchAccountDetails() {
		return userProfileDao.fetchAccountDetails();
		
	}
	
	@Override
	@Transactional("db1Tx")
	public List<AccountUserEntityTO> fetchRoleDetails() {
		return userProfileDao.fetchRoleDetails();
		
	}
	
	@Override
	@Transactional("db1Tx")
	public List<AccountUserEntityTO> fetchSiteDetails(Integer accountID) {
		return userProfileDao.fetchSiteDetails(accountID);
		
	}
	@Override
	@Transactional("db1Tx")	
	public String addUser(UserBean userBean) {		
		WebAppied Stringencryptor=new WebAppied();
		String encryptedString=Stringencryptor.encrypt(userBean.getPassword());
		userBean.setPassword(encryptedString);
		boolean uniqueLoginUser=userProfileDao.checkUniqueLoginName(userBean.getLoginName());
		if(uniqueLoginUser) {
			return "user_exist";
		}
		String resultString = "";			
		setAuditInfo(userBean,"newFlag");
		//setAuditInfo(purchaseOrdersBean);
		 UserEntity userEntity = mapper.map(userBean, UserEntity.class);
		// userEntity.setDefaultRoleID(userBean.getRoleID().getRoleID());
		// userEntity.setDefaultSiteID(userBean.getSiteID().getSiteID());
		//List<Object[]> listOfDrawingItems=userBean.getUserRoleList();
		/*for(Object[] ob:listOfDrawingItems) {
			AccountUserEntity accountUserEntity = new AccountUserEntity();
		    @SuppressWarnings("unchecked")
			LinkedHashMap<String,Integer> acountMap=(LinkedHashMap<String, Integer>) ob[0];		    
			accountUserEntity.getAccountID().setAccountID(acountMap.get("accountID"));
			LinkedHashMap<String,Integer> siteMap=(LinkedHashMap<String, Integer>) ob[1];			
			accountUserEntity.getSiteID().setSiteID(Long.parseLong(siteMap.get("siteID").toString()));
			LinkedHashMap<String,Integer> roleMap=(LinkedHashMap<String, Integer>) ob[2];				
			accountUserEntity.getRoleID().setRoleID(Long.parseLong(roleMap.get("roleID").toString()));			
			accountUserEntity.setRecordType('I');			
			accountUserEntity.setCreatedBy(userBean.getCreatedBy());
			accountUserEntity.setTransactionCount(1L);
			accountUserEntity.setCreatedDate(new Date());			
			accountUserEntity.setUserID(userEntity);
			//userEntity.getAccountUserList().add(accountUserEntity);			
		}*/
		//UserEntity userEntity = mapper.map(userBean,UserEntity.class);
		Long pkOfPO = userProfileDao.addUser(userEntity);		
	    if (pkOfPO>0) {			
			resultString= "ADDED";			
			
		}
		return resultString;
	}
	
	private void setAuditInfo(UserBean userBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			userBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			userBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			userBean.setCreatedDate(new Date());
		} else {
			userBean.setTransactionCount(
					userBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			userBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			userBean.setLastModifiedDate(new Date());
		}
		
	}

	@Override
	 @Transactional("db1Tx")
	public List<UserEntityTO> fetchUsersOnCriteria(String searchParameter) {		
		System.out.println("Get in Here");
		return userProfileDao.fetchUsers(searchParameter);
	}
	
//	@Override
//	@Transactional("db1Tx")
//	public List<UserEntityTO> fetchUsersOnCriteria(String searchParameter) {
//		return userProfileDao.fetchUsers(searchParameter);
//	}
//	
//	@Override
//	@Transactional("db1Tx")
//	public synchronized String deleteUser(DeleteRecords deleteRecords) {
//		String resultString = "";
//		boolean resultFlag = false;
//		TransactionData latestData = userProfileDao.fetchTransactionDataById(deleteRecords.getId());
//		if (latestData.getTransactionCount() > (deleteRecords.getTransactionCount())) {
//			return "TransactionFailed";
//		}
//		if (latestData.getRecordType() == 'D') {
//			return "recordDeleted";
//		}
//		resultFlag = userProfileDao.deleteUser(deleteRecords.getId(), deleteRecords.getModifiedBy());
//		if (resultFlag)
//			return "DELETED";
//
//		return resultString;
//	}
//



}
