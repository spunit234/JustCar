package com.edios.cdf.manager;

import java.util.List;

import com.edios.cdf.bean.security.UserBean;
import com.edios.cdf.entity.to.AccountUserEntityTO;
import com.edios.cdf.entity.to.UserEntityTO;
import com.edios.cdf.entity.to.UserProfileTO;
import com.edios.cdf.util.PayloadBean;

public interface UserProfileManager extends AbstractManager {

	List<UserProfileTO> fetchDefaultAccount(Long id);

	List<UserProfileTO> fetchDefaultRole(Long id);

	List<UserProfileTO> fetchDefaultSite(Long id);

	UserBean fetchUserProfileInfo(PayloadBean payloadBean);

	String updateUserProfile(UserBean userBean); 
	
//	List<UserEntityTO> fetchUsersOnCriteria(String searchParameter);
//	
//	String deleteUser(DeleteRecords deleteRecords);
//	
	List<AccountUserEntityTO> fetchAccountDetails();
//	
	List<AccountUserEntityTO> fetchRoleDetails();
	
	List<AccountUserEntityTO> fetchSiteDetails(Integer accountID);
	String addUser(UserBean userBean);
	// AccountBean getAccountName(Long id);

	List<UserEntityTO> fetchUsersOnCriteria(String searchParameter);
	
}
