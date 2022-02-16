package com.edios.cdf.dao;

import java.util.List;

import com.edios.cdf.bean.security.UserBean;
import com.edios.cdf.entity.security.AccountUserEntity;
import com.edios.cdf.entity.security.UserEntity;
import com.edios.cdf.entity.to.AccountUserEntityTO;
import com.edios.cdf.entity.to.UserEntityTO;
import com.edios.cdf.entity.to.UserProfileTO;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.util.TransactionData;

public interface UserProfileDao extends BaseDao<AccountUserEntity> {

	List<UserProfileTO> fetchDefaultAccount(Long id);

	List<UserProfileTO> fetchDefaultRole(Long id);

	List<UserProfileTO> fetchDefaultSite(Long id);
	
	UserEntity fetchUserProfileInfo(PayloadBean payloadBean);
	Long addUser(UserEntity userEntity);
	boolean updateUserProfile(UserEntity userEntity);
	List<AccountUserEntityTO> fetchAccountDetails();
	 List<AccountUserEntityTO> fetchRoleDetails();		
	 List<AccountUserEntityTO> fetchSiteDetails(Integer accountID);
	 boolean checkUniqueLoginName(String userName, Long userID);
	 boolean checkUniqueLoginName(String userName);
	// AccountBean getAccountName(Long id);

	List<UserEntityTO> fetchUsers(String searchParameter);

	TransactionData fetchTransactionDataById(Long userID);


	// List<UserEntityTO> fetchUsers(String searchParameter);
	//
	// TransactionData fetchTransactionDataById(Long siteID);
	//
	// boolean deleteUser(Long id, Integer modifiedBy);
	//
	 
	//
	
}
