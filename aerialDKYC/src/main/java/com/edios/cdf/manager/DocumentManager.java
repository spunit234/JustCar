package com.edios.cdf.manager;

import com.edios.cdf.entity.to.StorageLocationTO;

public interface DocumentManager extends AbstractManager {
	
	StorageLocationTO getDocumentLocationRoot(String locationName);

	boolean updateStorageLocation(Long locationId,String subfolderName, Long curFilesSubFolder);

	StorageLocationTO getDocumentLocation(Long locationId);
	

}
