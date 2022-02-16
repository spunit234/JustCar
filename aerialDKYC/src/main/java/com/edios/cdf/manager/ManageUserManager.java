package com.edios.cdf.manager;

import java.util.List;

import com.edios.cdf.bean.security.UserBean;
import com.edios.cdf.entity.to.UserProfileTO;
import com.edios.cdf.util.DeleteRecords;

public interface ManageUserManager extends AbstractManager {
	public UserBean finduserById(Long userID);

	public List<UserProfileTO> fetchAccountUsers(Long userID);

	String updateUserInfo(UserBean userBean);

	String deleteUser(DeleteRecords user);

	String addUser(UserBean userBean);
}
