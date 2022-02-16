package com.edios.cdf.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.bean.security.StorageLocationBean;
import com.edios.cdf.dao.DocumentDao;
import com.edios.cdf.entity.security.StorageLocationEntity;
import com.edios.cdf.entity.to.StorageLocationTO;
import com.edios.cdf.manager.DocumentManager;

@Service("documentManager")
public class DocumentManagerImpl extends AbstractManagerImpl<StorageLocationBean, StorageLocationEntity>
		implements DocumentManager {

	@Autowired
	DocumentDao documentDao;

	@Override
	@Transactional("db1Tx")
	public StorageLocationTO getDocumentLocationRoot(String locationName) {

		return documentDao.getDocumentLocationRoot(locationName);
	}

	@Override
	@Transactional("db1Tx")
	public boolean updateStorageLocation(Long locationId,String subfolderName, Long curFilesSubFolder) {
		return documentDao.updateStorageLocation(locationId,subfolderName,curFilesSubFolder);
		
	}

	@Override
	@Transactional("db1Tx")
	public StorageLocationTO getDocumentLocation(Long locationId) {
		return documentDao.getDocumentLocation(locationId);
	}

}
