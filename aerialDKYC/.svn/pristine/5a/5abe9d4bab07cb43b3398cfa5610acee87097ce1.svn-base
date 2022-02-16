package com.edios.cdf.manager;

import java.util.List;

import com.edios.cdf.bean.common.ApplicationParameterListBean;
import com.edios.cdf.display.ParameterDropDownTO;
import com.edios.cdf.entity.to.ApplicationParameterValueTO;

public interface DisplayManager extends AbstractManager {
	
	List<ParameterDropDownTO> parameterListDropdown(String listType);

	ApplicationParameterValueTO parameterValues(String parameterCode);
	
	List<ApplicationParameterListBean> fetchParameterDetails(String listType);
}
