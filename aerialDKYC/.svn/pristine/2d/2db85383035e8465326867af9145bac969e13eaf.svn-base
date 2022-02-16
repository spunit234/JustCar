package com.edios.cdf.dao;

import java.util.List;

import com.edios.cdf.bean.common.ApplicationParameterListBean;
import com.edios.cdf.display.ParameterDropDownTO;
import com.edios.cdf.entity.common.ApplicationParameterValuesEntity;
import com.edios.cdf.entity.to.ApplicationParameterValueTO;

public interface DisplayDao extends BaseDao<ApplicationParameterValuesEntity> {

	List<ParameterDropDownTO> parameterListDropdown(String listType);

	ApplicationParameterValueTO parameterValues(String parameterCode);
	
	List<ApplicationParameterListBean> fetchParameterDetails(String listType);

}
