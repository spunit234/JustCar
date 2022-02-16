package com.edios.cdf.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.bean.common.ApplicationParameterBean;
import com.edios.cdf.dao.ApplicationParameterDao;
import com.edios.cdf.entity.common.ApplicationParameterEntity;
import com.edios.cdf.entity.to.ApplicationParameterTO;
import com.edios.cdf.manager.ApplicationParameterManager;
import com.edios.cdf.manager.DisplayManager;
import com.edios.cdf.util.AppConstants;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.TransactionData;

@Service("applicationParameterManager")
public class ApplicationParameterManagerImpl
		extends AbstractManagerImpl<ApplicationParameterBean, ApplicationParameterEntity>
		implements ApplicationParameterManager {

	@Autowired
	ApplicationParameterDao applicationParameterDao;

	@Override
	@Transactional("db1Tx")
	public List<ApplicationParameterTO> fetchAppParamDetails() {
		List<ApplicationParameterTO> applicationParameterTO = null;
		try {
			applicationParameterTO = applicationParameterDao.fetchAppParamDetails();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return applicationParameterTO;
	}

	@Override
	@Transactional("db1Tx")
	public List<ApplicationParameterTO> fetchAppParameterDetails(String searchCode) {
		List<ApplicationParameterTO> applicationParameterTO = null;
		try {
			applicationParameterTO = applicationParameterDao.fetchAppParameterDetails(searchCode);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return applicationParameterTO;

	}

	@Override
	@Transactional("db1Tx")
	public ApplicationParameterBean findApplicationParameterById(Long id) {
		ApplicationParameterBean applicationParameterBean = null;
		ApplicationParameterEntity applicationParameterEntity = applicationParameterDao.findApplicationParameterById(id);
		if (applicationParameterEntity == null) {
			return null;
		} else {
			applicationParameterBean = mapper.map(applicationParameterEntity, ApplicationParameterBean.class);
		}
		return applicationParameterBean;
	}

	@Override
	@Transactional("db1Tx")
	public String addParameter(ApplicationParameterBean applicationParameterBean) {
		String resultString = "";
		boolean resultFlag = false;

		resultFlag = isAppParameterNameExist(applicationParameterBean.getParameterName());
		if (resultFlag) {
			return "NameAlreadyExist";
		}

		resultFlag = isAppParameterCodeExists(applicationParameterBean.getParameterCode());
		if (resultFlag) {
			return "CodeAlreadyExist";
		}

		setAuditInfo(applicationParameterBean, "newFlag");
		ApplicationParameterEntity applicationParameterEntity = mapper.map(applicationParameterBean,
				ApplicationParameterEntity.class);
		resultFlag = applicationParameterDao.addParameter(applicationParameterEntity);
		if (resultFlag) {
			return "ADDED";
		}
		return resultString;
	}

	private void setAuditInfo(ApplicationParameterBean applicationParameterBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			applicationParameterBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			applicationParameterBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			applicationParameterBean.setCreatedDate(new Date());
		} else {
			applicationParameterBean.setTransactionCount(
					applicationParameterBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			applicationParameterBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			applicationParameterBean.setLastModifiedDate(new Date());
		}
	}

	@Override
	@Transactional("db1Tx")
	public synchronized String updateParameter(ApplicationParameterBean applicationParameterBean) {
		String resultString = "";
		boolean resultFlag = false;

		resultFlag = isAppParameterNameExist(applicationParameterBean.getParameterName(),
				applicationParameterBean.getParameterID());
		if (resultFlag) {
			return "NameAlreadyExist";
		}

		resultFlag = isAppParameterCodeExists(applicationParameterBean.getParameterCode(),
				applicationParameterBean.getParameterID());
		if (resultFlag) {
			return "CodeAlreadyExist";
		}
		// Optimistic Locking
		TransactionData latestData = applicationParameterDao
				.fetchTransactionDataById(applicationParameterBean.getParameterID());
		if (latestData.getTransactionCount() > (applicationParameterBean.getTransactionCount())) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}

		setAuditInfo(applicationParameterBean, "editFlag");

		ApplicationParameterEntity applicationParameterEntity = mapper.map(applicationParameterBean,
				ApplicationParameterEntity.class);
		resultFlag = applicationParameterDao.updateParameter(applicationParameterEntity);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public synchronized String deleteParameter(DeleteRecords deleteRecords) {
		String resultString = "";
		boolean resultFlag = false;
		TransactionData latestData = applicationParameterDao.fetchTransactionDataById(deleteRecords.getId());
		if (latestData.getTransactionCount() > (deleteRecords.getTransactionCount())) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}
		resultFlag = applicationParameterDao.deleteParameter(deleteRecords.getId(), deleteRecords.getModifiedBy());
		if (resultFlag)
			return "DELETED";

		return resultString;
	}

	@Override
	public boolean isAppParameterNameExist(String parameterName) {
		return applicationParameterDao.getAppParameterNameExists(parameterName);
	}

	@Override
	public boolean isAppParameterCodeExists(String parameterCode) {
		return applicationParameterDao.getAppParameterCodeExists(parameterCode);
	}

	@Override
	public boolean isAppParameterNameExist(String parameterName, Long parameterId) {
		return applicationParameterDao.getAppParameterNameExists(parameterName, parameterId);
	}

	@Override
	public boolean isAppParameterCodeExists(String parameterCode, Long parameterId) {
		return applicationParameterDao.isAppParameterCodeExists(parameterCode, parameterId);
	}

}
