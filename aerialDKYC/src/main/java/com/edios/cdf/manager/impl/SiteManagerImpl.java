package com.edios.cdf.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.bean.security.SiteBean;
import com.edios.cdf.dao.SiteDao;
import com.edios.cdf.entity.security.SiteEntity;
import com.edios.cdf.entity.to.SiteEntityTO;
import com.edios.cdf.manager.SiteManager;
import com.edios.cdf.util.AppConstants;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.TransactionData;

@Service("siteManager")
public class SiteManagerImpl extends AbstractManagerImpl<SiteBean, SiteEntity> implements SiteManager {

	@Autowired
	SiteDao siteDao;

	@Override
	@Transactional("db1Tx")
	public List<SiteEntityTO> fetchSites(Integer accountId) {
		return siteDao.fetchSites(accountId);
	}
	
//	@Transactional("IBMTx")
//	public List<SiteEntityTO> fetchIBMDatabaseDetails(Integer accountId) {
//		return siteDao.fetchIBMDatabaseDetails(accountId);
//	}

	@Override
	@Transactional("db1Tx")
	public List<SiteEntityTO> fetchSitesOnCriteria(String searchParameter, Integer accountId) {
//		System.out.println("------------->"+fetchSites(accountId));
		
		return siteDao.fetchSites(searchParameter,accountId);
		
//		return fetchSites(accountId);
	}

	@Override
	@Transactional("db1Tx")
	public String addSite(SiteBean siteBean) {
		String resultString = "";
		boolean resultFlag = false;

		resultFlag = siteDao.isSiteNameExist(siteBean.getSiteName());
		if (resultFlag) {
			return "NameAlreadyExist";
		}

		resultFlag = siteDao.isSiteCodeExists(siteBean.getSiteCode());
		if (resultFlag) {
			return "CodeAlreadyExist";
		}

		setAuditInfo(siteBean, "newFlag");
		SiteEntity siteEntity = mapper.map(siteBean,SiteEntity.class);
		resultFlag = siteDao.addSite(siteEntity);
		if (resultFlag) {
			return "ADDED";
		}
		return resultString;
	}

	private void setAuditInfo(SiteBean siteBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			siteBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			siteBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			siteBean.setCreatedOn(new Date());
		} else {
			siteBean.setTransactionCount(
					siteBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			siteBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			siteBean.setLastModifiedOn(new Date());
		}
		
	}

	@Override
	@Transactional("db1Tx")
	public SiteBean findSiteById(Long id, Integer accountId) {
		SiteBean  siteBean= null;
		SiteEntity siteEntity = siteDao.findSiteById(id,accountId);
		if(siteEntity instanceof SiteEntity)
			siteBean = mapper.map(siteEntity, SiteBean.class);
		return siteBean;
	}

	@Override
	@Transactional("db1Tx")
	public synchronized String updateSite(SiteBean siteBean) {
		String resultString = "";
		boolean resultFlag = false;

		resultFlag = siteDao.isSiteNameExist(siteBean.getSiteName(),siteBean.getSiteID());
		if (resultFlag) {
			return "NameAlreadyExist";
		}

		resultFlag = siteDao.isSiteCodeExists(siteBean.getSiteCode(),siteBean.getSiteID());
		if (resultFlag) {
			return "CodeAlreadyExist";
		}
		
		// Optimistic Locking for Hibernate
		TransactionData latestData = siteDao.fetchTransactionDataById(siteBean.getSiteID());
		if (latestData.getTransactionCount() > (siteBean.getTransactionCount())) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}

		setAuditInfo(siteBean, "editFlag");
		SiteEntity siteEntity = mapper.map(siteBean,SiteEntity.class);
		resultFlag = siteDao.updateSite(siteEntity);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public synchronized String deleteSite(DeleteRecords deleteRecords) {
		String resultString = "";
		boolean resultFlag = false;
		TransactionData latestData = siteDao.fetchTransactionDataById(deleteRecords.getId());
		if (latestData.getTransactionCount() > (deleteRecords.getTransactionCount())) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}
		
		resultFlag = siteDao.isSiteExists(deleteRecords.getId());
		if (resultFlag) 
			return "SITE_EXISTS";
		
		resultFlag = siteDao.deleteSite(deleteRecords.getId(), deleteRecords.getModifiedBy());
		if (resultFlag)
			return "DELETED";

		return resultString;
	}

}
