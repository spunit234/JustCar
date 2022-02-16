package com.edios.cdf.manager;

import java.util.List;

import com.edios.cdf.bean.security.SiteBean;
import com.edios.cdf.entity.to.SiteEntityTO;
import com.edios.cdf.util.DeleteRecords;

public interface SiteManager extends AbstractManager {

	List<SiteEntityTO>  fetchSites(Integer accountId);

	List<SiteEntityTO> fetchSitesOnCriteria(String searchParameter, Integer accountId);

	String addSite(SiteBean siteBean);

	SiteBean findSiteById(Long id, Integer accountId);

	String updateSite(SiteBean siteBean);

	String deleteSite(DeleteRecords deleteRecords);
}
