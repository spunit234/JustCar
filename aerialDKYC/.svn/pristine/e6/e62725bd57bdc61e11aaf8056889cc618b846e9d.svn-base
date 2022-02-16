package com.edios.cdf.dao;

import com.edios.cdf.entity.security.StorageLocationEntity;
import com.edios.cdf.entity.to.StorageLocationTO;

public interface DocumentDao extends BaseDao<StorageLocationEntity> {

	StorageLocationTO getDocumentLocationRoot(String locationName);

	boolean updateStorageLocation(Long locationId,String subfolderName, Long curFilesSubFolder);

	StorageLocationTO getDocumentLocation(Long locationId);

}
