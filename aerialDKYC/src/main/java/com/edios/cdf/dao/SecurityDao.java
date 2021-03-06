package com.edios.cdf.dao;

import java.util.List;

import com.edios.cdf.entity.security.UserEntity;
import com.edios.cdf.entity.security.UserLoginDetailsEntity;
import com.edios.cdf.entity.to.AccountUserEntityTO;
import com.edios.cdf.entity.to.MenuEntityTO;
import com.edios.cdf.entity.to.RoleRightsEntityTO;
import com.edios.cdf.entity.to.UserDetailTO;
import com.edios.cdf.entity.to.UserEntityTO;
import com.edios.cdf.util.PayloadBean;

public interface SecurityDao extends BaseDao<UserEntity> {

	public UserDetailTO fetchUserDetails(String userName);

	public boolean updateLoginFailedTries(Long userID, int loginFlag);

	public boolean updateUserStatusLocked(Long userID, String string);
	
	public List<MenuEntityTO> fetchAccountMenus(Long roleID);
	
	List<MenuEntityTO> fetchRoleRightsMenus(Integer integer);

	public UserDetailTO getCurrentUser(String userName);

	public List<AccountUserEntityTO> fetchAccounts(Long id);

	public RoleRightsEntityTO fetchRoleRights(RoleRightsEntityTO roleRightsEntityTO);

	public Integer getMenuId(RoleRightsEntityTO roleRightsEntityTO);
	
	public boolean updateDefaultThemeForUser(PayloadBean payloadBean);

	public Long insertUserLoginDetails(UserLoginDetailsEntity userLoginDetailsEntity);

	public Long updateUserLoginDetails(PayloadBean payloadBean);

	//public void updateUserSessionId(Long userId, String id);

	public Long resetUserPassword(UserEntityTO userEntityTO, String ipAddress);

	public void updateUserSessionId(Long userId, String id);

	public void updateSessionToNull(PayloadBean payloadBean);

	public UserEntityTO validateUsernameEmailAddress(UserEntityTO payloadBean);
	
	public Long updatEmailPassword(UserEntityTO userEntityTO, String ipAddress);
	
	public Long updateForgotPassword(UserEntityTO userEntityTO, String ipAddress);
	
	public UserDetailTO getSiteName(Long id);
}
