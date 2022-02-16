package com.edios.cdf.manager.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.bean.common.ApplicationParameterValuesBean;
import com.edios.cdf.dao.ParameterValueDao;
import com.edios.cdf.entity.common.ApplicationParameterValuesEntity;
import com.edios.cdf.manager.ParameterValueManager;
import com.edios.cdf.util.AppConstants;
import com.edios.cdf.util.TransactionData;

@Service("parameterValueManager")
public class ParameterValueManagerImpl
		extends AbstractManagerImpl<ApplicationParameterValuesBean, ApplicationParameterValuesEntity>
		implements ParameterValueManager {

	@Autowired
	ParameterValueDao parameterValueDao;

	@Override
	@Transactional
	public ApplicationParameterValuesBean fetchParameterValueByParameterId(Long parameterId) {
		ApplicationParameterValuesBean applicationParameterValuesBean = null;
		ApplicationParameterValuesEntity applicationParameterValuesEntity = parameterValueDao
				.fetchParameterValueByParameterId(parameterId);

		if (applicationParameterValuesEntity == null)
			return null;
		applicationParameterValuesBean = mapper.map(applicationParameterValuesEntity,
				ApplicationParameterValuesBean.class);
		return applicationParameterValuesBean;
	}

	@Override
	@Transactional
	public String addParameterValue(ApplicationParameterValuesBean applicationParameterValueBean) {
		String resultString = "";
		boolean resultFlag = false;

		// resultFlag =
		// parameterValueDao.getAppParameterListNameExists(applicationParameterValueBean.getParameterID().getParameterID(),applicationParameterValueBean.getParameterValue());
		// if (resultFlag) {
		// return "NameAlreadyExist";
		// }

		setAuditInfo(applicationParameterValueBean, "newFlag");
		ApplicationParameterValuesEntity applicationParameterValuesEntity = mapper.map(applicationParameterValueBean,
				ApplicationParameterValuesEntity.class);
		resultFlag = parameterValueDao.addParameterValue(applicationParameterValuesEntity);
		if (resultFlag) {
			return "ADDED";
		}
		return resultString;
	}

	private void setAuditInfo(ApplicationParameterValuesBean applicationParameterValuesBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			applicationParameterValuesBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			applicationParameterValuesBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			applicationParameterValuesBean.setCreatedDate(new Date());
		} else {
			applicationParameterValuesBean.setTransactionCount(
					applicationParameterValuesBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			applicationParameterValuesBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			applicationParameterValuesBean.setLastModifiedDate(new Date());
		}
	}

	@Override
	@Transactional
	public String updateParameterValue(ApplicationParameterValuesBean applicationParameterValueBean) {
		String resultString = "";
		boolean resultFlag = false;

		/*
		 * resultFlag = parameterValueDao.getAppParameterListNameExists(
		 * applicationParameterListBean.getParameterID().getParameterID(),
		 * applicationParameterListBean.getParameterListValue(),
		 * applicationParameterListBean.getParameterListID()); if (resultFlag) { return
		 * "NameAlreadyExist"; }
		 */

		TransactionData latestData = parameterValueDao
				.fetchTransactionDataById(applicationParameterValueBean.getParameterValueID());
		if (latestData.getTransactionCount() > (applicationParameterValueBean.getTransactionCount())) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}

		setAuditInfo(applicationParameterValueBean, "editFlag");
		ApplicationParameterValuesEntity applicationParameterValuesEntity = mapper.map(applicationParameterValueBean,
				ApplicationParameterValuesEntity.class);
		resultFlag = parameterValueDao.updateParameterValue(applicationParameterValuesEntity);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

}
