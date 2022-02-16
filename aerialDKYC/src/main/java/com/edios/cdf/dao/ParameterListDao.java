package com.edios.cdf.dao;

import java.util.List;

import com.edios.cdf.entity.common.ApplicationParameterListEntity;
import com.edios.cdf.entity.to.ApplicationParameterListTO;
import com.edios.cdf.util.TransactionData;

public interface ParameterListDao extends BaseDao<ApplicationParameterListEntity> {

	List<ApplicationParameterListTO> fetchParameterListByParameterId(Long parameterId);

	boolean addParameterList(ApplicationParameterListEntity applicationParameterListEntity);
	
    boolean getAppParameterListNameExists(Long parameterID,String parameterListValue) ;

	boolean getAppParameterListCodeExists(Long parameterID, String parameterListCode);

	ApplicationParameterListEntity findApplicationParameterListById(Long id);

	boolean getAppParameterListNameExists(Long parameterID, String parameterListValue, Long parameterListID);

	boolean updateParameterList(ApplicationParameterListEntity applicationParameterListEntity);

	boolean deleteParameterList(Long id, Integer modifiedBy);

	TransactionData fetchTransactionDataById(Long parameterListID);
	

}
