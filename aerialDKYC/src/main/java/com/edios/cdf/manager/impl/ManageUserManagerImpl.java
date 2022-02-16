package com.edios.cdf.manager.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.bean.security.UserBean;
import com.edios.cdf.controller.SendMailUsingAuthentication;
import com.edios.cdf.dao.ManageUserDao;
import com.edios.cdf.entity.security.AccountUserEntity;
import com.edios.cdf.entity.security.UserEntity;
import com.edios.cdf.entity.to.UserProfileTO;
import com.edios.cdf.manager.ManageUserManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.EmailTemplate;
import com.edios.cdf.util.TransactionData;
import com.edios.cdf.util.WebAppied;

@Service
public class ManageUserManagerImpl extends AbstractManagerImpl<UserBean, UserEntity> implements ManageUserManager {
	private static final Long TRANSACTION_BEGIN = 1l;

	private static final Character RECORDTYPE_INSERT = 'I';

	private static final Character RECORDTYPE_DELETE = 'D';

	private static final Character RECORDTYPE_UPDATE = 'U';

	@Autowired
	ManageUserDao manageUserDao;

	@Override
	@Transactional("db1Tx")
	public UserBean finduserById(Long userID) {
		UserBean userBean = null;
		userBean = mapper.map(manageUserDao.findUserById(userID), UserBean.class);
		WebAppied decryption = new WebAppied();
		userBean.setPassword(decryption.decrypt(userBean.getPassword()));
		return userBean;
	}

	@Override
	@Transactional("db1Tx")
	public List<UserProfileTO> fetchAccountUsers(Long userID) {
		List<UserProfileTO> userProfileTO = null;
		try {
			userProfileTO = manageUserDao.fetchAccountUserDetails(userID);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return userProfileTO;
	}

	@Override
	@Transactional("db1Tx")
	public String updateUserInfo(UserBean userBean) {
		String resultString = "";
		String userPassword = userBean.getPassword();
		String userName = userBean.getLoginName();
		WebAppied Stringencryptor = new WebAppied();
		String encryptedString = Stringencryptor.encrypt(userBean.getPassword());
		userBean.setPassword(encryptedString);
		boolean resultFlag = false;
		UserBean latestData = manageUserDao.fetchUserById(userBean.getUserID());
		if (latestData.getTransactionCount() > (userBean.getTransactionCount())) {
			userBean = latestData;
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			userBean = latestData;
			return "recordDeleted";
		}

		setAuditInfo(userBean, "editFlag");
		
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, userBean.getPasswordExpiryDays().intValue());
		Date modifiedDate = cal.getTime();
		userBean.setPasswordExpiryDate(modifiedDate);
		UserEntity userEntity = mapper.map(userBean, UserEntity.class);
		boolean deleteUserAccounts = manageUserDao.deleteUserAccounts(userBean.getUserID());
		if (deleteUserAccounts) {
			List<Object[]> listOfUserAccounts = userBean.getUserRoleList();
			for (Object[] ob : listOfUserAccounts) {
				AccountUserEntity accountUserEntity = new AccountUserEntity();
				@SuppressWarnings("unchecked")
				LinkedHashMap<String, Integer> acountMap = (LinkedHashMap<String, Integer>) ob[0];
				accountUserEntity.getAccountID().setAccountID(acountMap.get("accountID"));
				LinkedHashMap<String, Integer> siteMap = (LinkedHashMap<String, Integer>) ob[1];
				accountUserEntity.getSiteID().setSiteID(Long.parseLong(siteMap.get("siteID").toString()));
				LinkedHashMap<String, Integer> roleMap = (LinkedHashMap<String, Integer>) ob[2];
				accountUserEntity.getRoleID().setRoleID(Long.parseLong(roleMap.get("roleID").toString()));
				accountUserEntity.setCreatedBy(userBean.getLastModifiedBy());
				accountUserEntity.setTransactionCount(1L);
				accountUserEntity.setRecordType('I');
				accountUserEntity.setCreatedDate(new Date());
				accountUserEntity.setUserID(userEntity);
				userEntity.getAccountUserList().add(accountUserEntity);
			}
			resultFlag = manageUserDao.updateUserInfo(userEntity);
		}
		if (resultFlag) {
			resultString = "UPDATED";
			if (userBean.isEmailCredentials()) {
				SendMailUsingAuthentication sendMailUsingAuthentication = new SendMailUsingAuthentication();
				try {
					EmailTemplate emailTemplateObj= new EmailTemplate();
					if(userBean.getLastName()!=null) {
						userBean.setFirstName(userBean.getFirstName()+" "+userBean.getLastName());
					}
					String emailTemplate=emailTemplateObj.createHtmlForEmail(userBean.getFirstName(),userName,userPassword);
					boolean emailFlag = sendMailUsingAuthentication.postMail(
							new String[] { userBean.getEmailAddress() }, "", emailTemplate, "User Password Generation Email");
					if (emailFlag) {
						resultString = "UPDATED_EMAIL";
					}
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
		return resultString;

	}

	private void setAuditInfo(UserBean userBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			userBean.setTransactionCount(TRANSACTION_BEGIN);
			userBean.setRecordType(RECORDTYPE_INSERT);
			userBean.setCreatedDate(new Date());
		} else {
			userBean.setTransactionCount(userBean.getTransactionCount() + TRANSACTION_BEGIN);
			userBean.setRecordType(RECORDTYPE_UPDATE);
			userBean.setLastModifiedDate(new Date());
		}
	}

	@Override
	@Transactional("db1Tx")
	public String deleteUser(DeleteRecords user) {

		String resultString = "";
		boolean resultFlag = false;
		TransactionData latestData = manageUserDao.fetchTransactionDataById(user.getId());
		if (latestData.getTransactionCount() > (user.getTransactionCount())) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}
		resultFlag = manageUserDao.deleteUser(user.getId(), user.getModifiedBy());
		if (resultFlag)
			return "DELETED";

		return resultString;

	}

	@Override
	@Transactional("db1Tx")
	public String addUser(UserBean userBean) {
		WebAppied Stringencryptor = new WebAppied();
		String userPassword = userBean.getPassword();
		String userName = userBean.getLoginName();
		String encryptedString = Stringencryptor.encrypt(userBean.getPassword());
		userBean.setPassword(encryptedString);
		boolean uniqueLoginUser = manageUserDao.checkUniqueLoginName(userBean.getLoginName());
		if (uniqueLoginUser) {
			return "user_exist";
		}
		String resultString = "";
		setAuditInfo(userBean, "newFlag");
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, userBean.getPasswordExpiryDays().intValue());
		Date modifiedDate = cal.getTime();
		userBean.setPasswordExpiryDate(modifiedDate);
		UserEntity userEntity = mapper.map(userBean, UserEntity.class);
		List<Object[]> listOfDrawingItems = userBean.getUserRoleList();
		for (Object[] ob : listOfDrawingItems) {
			AccountUserEntity accountUserEntity = new AccountUserEntity();
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Integer> acountMap = (LinkedHashMap<String, Integer>) ob[0];
			accountUserEntity.getAccountID().setAccountID(acountMap.get("accountID"));
			LinkedHashMap<String, Integer> siteMap = (LinkedHashMap<String, Integer>) ob[1];
			accountUserEntity.getSiteID().setSiteID(Long.parseLong(siteMap.get("siteID").toString()));
			LinkedHashMap<String, Integer> roleMap = (LinkedHashMap<String, Integer>) ob[2];
			accountUserEntity.getRoleID().setRoleID(Long.parseLong(roleMap.get("roleID").toString()));
			accountUserEntity.setRecordType('I');
			accountUserEntity.setCreatedBy(userBean.getCreatedBy());
			accountUserEntity.setTransactionCount(1L);
			accountUserEntity.setCreatedDate(new Date());
			accountUserEntity.setUserID(userEntity);
			userEntity.getAccountUserList().add(accountUserEntity);
		}
		Long pkOfPO = manageUserDao.addUser(userEntity);
		if (pkOfPO > 0) {
			resultString = "ADDED";
			if (userBean.isEmailCredentials()) {
				SendMailUsingAuthentication sendMailUsingAuthentication = new SendMailUsingAuthentication();
				try {
					EmailTemplate emailTemplateObj= new EmailTemplate();
					if(userBean.getLastName()!=null) {
						userBean.setFirstName(userBean.getFirstName()+" "+userBean.getLastName());
					}
					String emailTemplate=emailTemplateObj.createHtmlForEmail(userBean.getFirstName(),userName,userPassword);
					boolean emailFlag = sendMailUsingAuthentication.postMail(
							new String[] { userBean.getEmailAddress() }, "", emailTemplate, "User Password Generation Email");
					if (emailFlag) {
						resultString = "ADDED_EMAIL";
					}
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}

		}
		return resultString;
	}

}
