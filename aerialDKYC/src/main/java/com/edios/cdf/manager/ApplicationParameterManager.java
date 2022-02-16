package com.edios.cdf.manager;

import java.util.List;

import com.edios.cdf.bean.common.ApplicationParameterBean;
import com.edios.cdf.entity.to.ApplicationParameterTO;
import com.edios.cdf.util.DeleteRecords;

public interface ApplicationParameterManager extends AbstractManager {

	List<ApplicationParameterTO> fetchAppParamDetails();

	List<ApplicationParameterTO> fetchAppParameterDetails(String searchCode);

	ApplicationParameterBean findApplicationParameterById(Long id);

	String addParameter(ApplicationParameterBean applicationParameterBean);

	String updateParameter(ApplicationParameterBean applicationParameterBean);

	boolean isAppParameterNameExist(String parameterName);

	boolean isAppParameterCodeExists(String parameterCode);

	boolean isAppParameterNameExist(String parameterName, Long parameterId);

	boolean isAppParameterCodeExists(String parameterCode, Long parameterId);

	String deleteParameter(DeleteRecords deleteRecords);

}
