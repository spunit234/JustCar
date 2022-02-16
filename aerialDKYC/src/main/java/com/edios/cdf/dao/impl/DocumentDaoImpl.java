package com.edios.cdf.dao.impl;

import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.DocumentDao;
import com.edios.cdf.entity.security.StorageLocationEntity;
import com.edios.cdf.entity.to.StorageLocationTO;

@Repository
public class DocumentDaoImpl extends BaseDaoImpl<StorageLocationEntity> implements DocumentDao {

	

	@SuppressWarnings("deprecation")
	@Override
	public StorageLocationTO getDocumentLocationRoot(String locationName) {
		Session session = (Session) entityManager.getDelegate();
		  try {
		   return  (StorageLocationTO) session.createQuery(
		     " SELECT SLE.locationId  as locationId, SLE.locationName as locationName, SLE.locationMaxSize as locationMaxSize, "+
		     "  SLE.locationUsageAlertPercent as locationUsageAlertPercent, SLE.locationRootPath as locationRootPath ,  "+
		     "  SLE.locationFolderName as locationFolderName, SLE.subFolderPrefix as subFolderPrefix, SLE.curSubFolderName as curSubFolderName,"+
		     " SLE.curSubFolderFilesCount as curSubFolderFilesCount ,SLE.maxFilesPerSubFolder as maxFilesPerSubFolder "+
		     " from StorageLocationEntity SLE   " +
		     " where SLE.recordType <>'D' and SLE.locationName=:locationName" )
				   .setParameter("locationName", locationName)
				   .setResultTransformer(Transformers.aliasToBean(StorageLocationTO.class)).list().get(0);
		     
		  } catch (Exception e) {
		   System.out.println("the Exception is "+e);
		   return null;
		  }
	}

	@Override
	public boolean updateStorageLocation(Long locationId,String subfolderName, Long curFilesSubFolder) {
		boolean result = true;
		try {
			String hql = "update StorageLocationEntity SLE set SLE.lastModifiedDate=:lastModifiedDate"
					+ " , SLE.curSubFolderFilesCount=:curFilesSubFolder , SLE.curSubFolderName=:subfolderName "
					+ " WHERE SLE.locationId=:locationId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("locationId", locationId);
			query.setParameter("subfolderName", subfolderName);
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("curFilesSubFolder", curFilesSubFolder);
			int resultID = query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	@Override
	public StorageLocationTO getDocumentLocation(Long locationId) {
		Session session = (Session) entityManager.getDelegate();
		  try {
		   return  (StorageLocationTO) session.createQuery(
		     " SELECT SLE.locationId  as locationId, SLE.locationName as locationName, SLE.locationMaxSize as locationMaxSize, "+
		     "  SLE.locationUsageAlertPercent as locationUsageAlertPercent, SLE.locationRootPath as locationRootPath ,  "+
		     "  SLE.locationFolderName as locationFolderName, SLE.subFolderPrefix as subFolderPrefix, SLE.curSubFolderName as curSubFolderName,"+
		     " SLE.curSubFolderFilesCount as curSubFolderFilesCount ,SLE.maxFilesPerSubFolder as maxFilesPerSubFolder "+
		     " from StorageLocationEntity SLE   " +
		     " where SLE.recordType <>'D' and SLE.locationId=:locationId" )
				   .setParameter("locationId", locationId)
				   .setResultTransformer(Transformers.aliasToBean(StorageLocationTO.class)).list().get(0);
		     
		  } catch (Exception e) {
		   System.out.println("the Exception is "+e);
		   return null;
		  }
	}

}
