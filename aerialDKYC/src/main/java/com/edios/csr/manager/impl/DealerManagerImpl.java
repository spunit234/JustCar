package com.edios.csr.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.manager.impl.AbstractManagerImpl;
import com.edios.cdf.util.AppConstants;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.DealerBean;
import com.edios.csr.bean.DealerExecutiveBean;
import com.edios.csr.dao.DealerDao;
import com.edios.csr.entity.DealerEntity;
import com.edios.csr.entity.DealerExecutiveEntity;
import com.edios.csr.entity.to.DealerExecutiveTO;
import com.edios.csr.entity.to.DealerTO;
import com.edios.csr.manager.DealerManager;

@Service("deqlerManager")
public class DealerManagerImpl extends AbstractManagerImpl<DealerBean, DealerEntity> implements DealerManager {

	@Autowired
	DealerDao dealerDao;

	@Autowired
	MessageSource messageSource;

	@Override
	@Transactional("db1Tx")
	public String addDealer(DealerBean customerBean) {
		String resultString = "";
		setAuditInfo(customerBean, "newFlag");
		try {
			DealerEntity dealerEntity = mapper.map(customerBean, DealerEntity.class);
			resultString = dealerDao.addDealer(dealerEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	
	@Override
	@Transactional("db1Tx")
	public String addDealerExecutive(DealerExecutiveBean customerBean) {
		String resultString = "";
		boolean resultFlag = false;
		if(!customerBean.isDuplicateFlag()) {
		resultFlag = dealerDao.isDealerExecutiveNumberExist(customerBean.getContactNumber());
		if (resultFlag) {
			return "Duplicate";
		}
		}
		
		setAuditInfo(customerBean, "newFlag");
		try {
			DealerExecutiveEntity dealerEntity = mapper.map(customerBean, DealerExecutiveEntity.class);
			
			resultString = dealerDao.addDealerExecutive(dealerEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	private void setAuditInfo(DealerExecutiveBean inquiryBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			inquiryBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			inquiryBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			inquiryBean.setCreatedDate(new Date());
		} else {
			inquiryBean.setTransactionCount(inquiryBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			inquiryBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			inquiryBean.setLastModifiedDate(new Date());
		}
	}
	
	private void setAuditInfo(DealerBean inquiryBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			inquiryBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			inquiryBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			inquiryBean.setCreatedDate(new Date());
		} else {
			inquiryBean.setTransactionCount(inquiryBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			inquiryBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			inquiryBean.setLastModifiedDate(new Date());
		}
	}
	
	@Override
	@Transactional("db1Tx")
	public List<DealerTO> fetchdealerData(DealerBean customerBean) {

		return dealerDao.fetchdealerData(customerBean);
	}
	@Override
	@Transactional("db1Tx")
	public List<DealerExecutiveBean> fetchdealerExecutiveData(PayloadBean customerBean) {

		return dealerDao.fetchdealerExecutiveData(customerBean);
	}
	
	@Override
	@Transactional("db1Tx")
	public List<DealerExecutiveTO> searchdealerExecutiveData(PayloadBean payloadBean) {
		return dealerDao.searchdealerExecutiveData(payloadBean);
	}
	@Override
	@Transactional("db1Tx")
	public List<DealerExecutiveTO> searchdealerExecutiveData1(String  payloadBean) {
		return dealerDao.searchdealerExecutiveData1(payloadBean);
	}
	

	
	@Override
	@Transactional("db1Tx")
	public int checkContactNumber(String contactNumber) {
		int a;
	  a  =	dealerDao.checkContactNumber(contactNumber);
		return a;
	}
	
	@Override
	@Transactional("db1Tx")
	public int checkContactNumberUpdate(String contactNumber,Long dealerId) {
		int a;
	  a  =	dealerDao.checkContactNumberUpdate(contactNumber, dealerId);
		return a;
	}
	@Override
	@Transactional("db1Tx")
	public int checkContactNumberExecutive(String contactNumber) {
		int a;
	  a  =	dealerDao.checkContactNumber(contactNumber);
		return a;
	}
	@Override
	@Transactional("db1Tx")
	public int checkContactNumberUpdateExecutive(String contactNumber,Long dealerId) {
		int a;
	  a  =	dealerDao.checkContactNumberUpdate(contactNumber, dealerId);
		return a;
	}
	@Override
	@Transactional("db1Tx")
	public String updateDealer( DealerBean customerBean) {	
		
		String resultString = "";
		boolean resultFlag = false;
		customerBean.setLastModifiedDate(new Date());
		
		DealerEntity customerEntity = mapper.map(customerBean,DealerEntity.class);
		resultFlag = dealerDao.updateDealer(customerEntity);
		if (resultFlag) {
			return "UPDATED";
		}
		
		
		return resultString;
	}
	
	@Override
	@Transactional("db1Tx")
	public String updateDealerExecutive( DealerExecutiveBean customerBean) {	
		
		String resultString = "";
		boolean resultFlag = false;
		
		boolean resultFlag1 = false;
		if(!customerBean.isDuplicateFlag()) {
		resultFlag1 = dealerDao.isDealerExecutiveNumberExistWhileUpdate(customerBean.getContactNumber(),customerBean.getDealerExecutiveId());
		if (resultFlag1) {
			return "Duplicate";
		}
		}
		customerBean.setLastModifiedDate(new Date());
		
		DealerExecutiveEntity customerEntity = mapper.map(customerBean,DealerExecutiveEntity.class);
		resultFlag = dealerDao.updateDealerExecutive(customerEntity);
		if (resultFlag) {
			return "UPDATED";
		}
		
		
		return resultString;
	}
	@Override
	@Transactional("db1Tx")
	public List<DealerTO> searchdealerData(DealerBean payloadBean) {
		return dealerDao.searchdealerData(payloadBean);
	}
	
	@Override
	@Transactional("db1Tx")
	public List<DealerBean> editDealer(PayloadBean payloadbean) {
		return dealerDao.editDealer(payloadbean);
	}
	@Override
	@Transactional("db1Tx")
	public String deleteDealer(DeleteRecords deleteRecords) {
		String resultflag = null;
		boolean flag = dealerDao.deleteDealerExecutive(deleteRecords);
		if(flag) {
			
			return resultflag = "DELETED";
		}
		return resultflag;
		
	}
	@Override
	@Transactional("db1Tx")
	
	public String deleteDealerExecutive(DeleteRecords deleteRecords) {
		String resultflag = null;
		boolean flag = dealerDao.deleteDealerExecutive(deleteRecords);
		if(flag) {
			
			return resultflag = "DELETED";
		}
		return resultflag;
		
	}
}
