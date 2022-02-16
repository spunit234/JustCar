package com.edios.cdf.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.bean.common.ApplicationParameterListBean;
import com.edios.cdf.dao.ParameterListDao;
import com.edios.cdf.entity.common.ApplicationParameterListEntity;
import com.edios.cdf.entity.to.ApplicationParameterListTO;
import com.edios.cdf.manager.ParameterListManager;
import com.edios.cdf.util.AppConstants;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.TransactionData;

@Service("parameterListManager")
public class ParameterListManagerImpl
		extends AbstractManagerImpl<ApplicationParameterListBean, ApplicationParameterListEntity>
		implements ParameterListManager {

	@Autowired
	ParameterListDao parameterListDao;

	@Override
	@Transactional("db1Tx")
	public List<ApplicationParameterListTO> fetchParameterListByParameterId(Long parameterId) {
		List<ApplicationParameterListTO> applicationParameterListTO = null;
		try {
			applicationParameterListTO = parameterListDao.fetchParameterListByParameterId(parameterId);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return applicationParameterListTO;
	}

	@Override
	@Transactional("db1Tx")
	public String addParameterList(ApplicationParameterListBean applicationParameterListBean) {
		String resultString = "";
		boolean resultFlag = false;

		resultFlag = parameterListDao.getAppParameterListNameExists(
				applicationParameterListBean.getParameterID().getParameterID(),
				applicationParameterListBean.getParameterListValue());
		if (resultFlag) {
			return "NameAlreadyExist";
		}

		resultFlag = parameterListDao.getAppParameterListCodeExists(
				applicationParameterListBean.getParameterID().getParameterID(),
				applicationParameterListBean.getParameterListCode());
		if (resultFlag) {
			return "CodeAlreadyExist";
		}

		setAuditInfo(applicationParameterListBean, "newFlag");
		ApplicationParameterListEntity applicationParameterListEntity = mapper.map(applicationParameterListBean,
				ApplicationParameterListEntity.class);
		resultFlag = parameterListDao.addParameterList(applicationParameterListEntity);
		if (resultFlag) {
			return "ADDED";
		}
		return resultString;
	}

	private void setAuditInfo(ApplicationParameterListBean applicationParameterListBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			applicationParameterListBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			applicationParameterListBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			applicationParameterListBean.setCreatedDate(new Date());
		} else {
			applicationParameterListBean.setTransactionCount(
					applicationParameterListBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			applicationParameterListBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			applicationParameterListBean.setLastModifiedDate(new Date());
		}
	}

	@Override
	@Transactional("db1Tx")
	public ApplicationParameterListBean findApplicationParameterListById(Long id) {
		ApplicationParameterListBean applicationParameterListBean = null;
		applicationParameterListBean = mapper.map(parameterListDao.findApplicationParameterListById(id),
				ApplicationParameterListBean.class);
		return applicationParameterListBean;
	}

	@Override
	@Transactional("db1Tx")
	public synchronized String updateParameterList(ApplicationParameterListBean applicationParameterListBean) {
		String resultString = "";
		boolean resultFlag = false;

		resultFlag = parameterListDao.getAppParameterListNameExists(
				applicationParameterListBean.getParameterID().getParameterID(),
				applicationParameterListBean.getParameterListValue(),
				applicationParameterListBean.getParameterListID());
		if (resultFlag) {
			return "NameAlreadyExist";
		}

		TransactionData latestData = parameterListDao.fetchTransactionDataById(applicationParameterListBean.getParameterListID());
		if (latestData.getTransactionCount() > (applicationParameterListBean.getTransactionCount())) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}

		setAuditInfo(applicationParameterListBean, "editFlag");
		ApplicationParameterListEntity applicationParameterListEntity = mapper.map(applicationParameterListBean,
				ApplicationParameterListEntity.class);
		resultFlag = parameterListDao.updateParameterList(applicationParameterListEntity);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String deleteParameterList(DeleteRecords deleteRecords) {
		String resultString = "";
		boolean resultFlag = false;

		TransactionData latestData = parameterListDao.fetchTransactionDataById(deleteRecords.getId());
		if (latestData.getTransactionCount() > (deleteRecords.getTransactionCount())) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}
		resultFlag = parameterListDao.deleteParameterList(deleteRecords.getId(),deleteRecords.getModifiedBy());
		if(resultFlag)
			return "DELETED";
		
		return resultString;
	}

}
