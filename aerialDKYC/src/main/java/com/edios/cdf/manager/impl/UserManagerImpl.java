package com.edios.cdf.manager.impl;

import org.springframework.stereotype.Service;

import com.edios.cdf.bean.security.UserBean;
import com.edios.cdf.entity.security.UserEntity;
import com.edios.cdf.manager.UserManager;

@Service("userManager")
public class UserManagerImpl extends AbstractManagerImpl<UserBean, UserEntity> implements UserManager {

	private static final long serialVersionUID = 1711895909885723245L;

}