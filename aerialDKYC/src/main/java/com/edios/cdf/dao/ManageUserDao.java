package com.edios.cdf.dao;

import java.util.List;

import com.edios.cdf.bean.security.UserBean;
import com.edios.cdf.entity.security.UserEntity;
import com.edios.cdf.entity.to.UserProfileTO;
import com.edios.cdf.util.TransactionData;

public interface ManageUserDao extends BaseDao<UserEntity> {
	UserEntity findUserById(Long userID);

	List<UserProfileTO> fetchAccountUserDetails(Long userID);

	UserBean fetchUserById(Long userID);

	boolean updateUserInfo(UserEntity userBean);

	boolean deleteUserAccounts(Long userID);

	TransactionData fetchTransactionDataById(Long id);

	boolean deleteUser(Long id, Integer modifiedBy);

	Long addUser(UserEntity userEntity);

	boolean checkUniqueLoginName(String userName);
}
