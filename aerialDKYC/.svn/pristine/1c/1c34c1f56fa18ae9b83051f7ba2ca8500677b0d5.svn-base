package com.edios.cdf.manager;

import java.util.List;

import com.edios.cdf.bean.common.ApplicationParameterListBean;
import com.edios.cdf.entity.to.ApplicationParameterListTO;
import com.edios.cdf.util.DeleteRecords;

public interface ParameterListManager  extends AbstractManager {

	List<ApplicationParameterListTO> fetchParameterListByParameterId(Long parameterId);

	String addParameterList(ApplicationParameterListBean applicationParameterListBean);

	ApplicationParameterListBean findApplicationParameterListById(Long id);

	String updateParameterList(ApplicationParameterListBean applicationParameterListBean);

	String deleteParameterList(DeleteRecords deleteRecords);
	

}
