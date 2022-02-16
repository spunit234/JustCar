package com.edios.cdf.dao;

import java.util.List;

import com.edios.cdf.bean.security.SiteBean;
import com.edios.cdf.entity.security.SiteEntity;
import com.edios.cdf.entity.to.SiteEntityTO;
import com.edios.cdf.util.TransactionData;

public interface SiteDao extends BaseDao<SiteEntity> {

	
	List<SiteEntityTO>  fetchSites(Integer accountId);
	
	List<SiteEntityTO>  fetchIBMDatabaseDetails(Integer accountId);

	List<SiteEntityTO> fetchSites(String searchParameter, Integer accountId);

	boolean isSiteNameExist(String siteName);

	boolean isSiteCodeExists(String siteCode);
	
	boolean isSiteNameExist(String siteName, Long siteID);

	boolean isSiteCodeExists(String siteCode, Long siteID);

	boolean addSite(SiteEntity siteEntity);

	SiteEntity findSiteById(Long id, Integer accountId);

	TransactionData fetchTransactionDataById(Long siteID);

	boolean updateSite(SiteEntity siteEntity);

	boolean deleteSite(Long id, Integer modifiedBy);
	
	 boolean isSiteExists(Long id);

	
}
