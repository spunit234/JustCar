package com.edios.cdf.dao;

import com.edios.cdf.entity.common.ApplicationParameterValuesEntity;
import com.edios.cdf.util.TransactionData;

public interface ParameterValueDao extends BaseDao<ApplicationParameterValuesEntity> {

	ApplicationParameterValuesEntity fetchParameterValueByParameterId(Long parameterId);

	boolean addParameterValue(ApplicationParameterValuesEntity applicationParameterValuesEntity);

	TransactionData fetchTransactionDataById(Long parameterValueID);

	boolean updateParameterValue(ApplicationParameterValuesEntity applicationParameterValuesEntity);

	
}
