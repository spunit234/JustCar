package com.edios.cdf.dao;

import java.util.List;

import com.edios.cdf.bean.common.ApplicationParameterBean;
import com.edios.cdf.entity.common.ApplicationParameterEntity;
import com.edios.cdf.entity.to.ApplicationParameterTO;
import com.edios.cdf.util.TransactionData;

public interface ApplicationParameterDao extends BaseDao<ApplicationParameterEntity> {

	List<ApplicationParameterTO> fetchAppParamDetails();

	List<ApplicationParameterTO> fetchAppParameterDetails(String searchCode);

	ApplicationParameterEntity findApplicationParameterById(Long id);

	boolean addParameter(ApplicationParameterEntity applicationParameterEntity);

	boolean updateParameter(ApplicationParameterEntity applicationParameterEntity);

	boolean getAppParameterNameExists(String parameterName);

	boolean getAppParameterCodeExists(String parameterCode);

	boolean getAppParameterNameExists(String parameterName, Long parameterId);

	boolean isAppParameterCodeExists(String parameterCode, Long parameterId);

	boolean deleteParameter(Long deleteRecords,Integer modifiedBy);

	TransactionData fetchTransactionDataById(Long id);
}
