package com.edios.cdf.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.bean.common.ApplicationParameterListBean;
import com.edios.cdf.bean.common.ApplicationParameterValuesBean;
import com.edios.cdf.dao.DisplayDao;
import com.edios.cdf.display.ParameterDropDownTO;
import com.edios.cdf.entity.common.ApplicationParameterValuesEntity;
import com.edios.cdf.entity.to.ApplicationParameterValueTO;
import com.edios.cdf.manager.DisplayManager;

@Service("displayManager")
public class DisplayManagerImpl extends AbstractManagerImpl<ApplicationParameterValuesBean, ApplicationParameterValuesEntity> implements DisplayManager {

	@Autowired
	private DisplayDao displayDao;

	@Override
	@Transactional("db1Tx")
	public List<ParameterDropDownTO> parameterListDropdown(String parameterCode) {
		return displayDao.parameterListDropdown(parameterCode);
	}

	@Override
	@Transactional("db1Tx")
	public ApplicationParameterValueTO parameterValues(String parameterCode) {
		return displayDao.parameterValues(parameterCode);
	}

	@Override
	@Transactional("db1Tx")
	public List<ApplicationParameterListBean> fetchParameterDetails(String parameterCode) {
		return displayDao.fetchParameterDetails(parameterCode);
	}

}
