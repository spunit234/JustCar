package com.edios.cdf.manager;

import com.edios.cdf.bean.common.ApplicationParameterValuesBean;

public interface ParameterValueManager extends AbstractManager {

	ApplicationParameterValuesBean fetchParameterValueByParameterId(Long parameterId);

	String addParameterValue(ApplicationParameterValuesBean applicationParameterValueBean);

	String updateParameterValue(ApplicationParameterValuesBean applicationParameterValueBean);

}
