package com.edios.cdf.manager.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edios.cdf.entity.to.StorageLocationTO;
import com.edios.cdf.manager.DocumentManager;
import com.edios.cdf.util.StorageData;

@Service
public class StorageLocationUtil {

	@Autowired
	DocumentManager documentManager;

	public StorageData addDocument(Long pk, String locationName, MultipartFile file) {
		boolean result = false;
		StorageData storageData = null;
		try {
			StorageLocationTO storageLocationTO = documentManager.getDocumentLocationRoot(locationName);
			if (storageLocationTO != null) {

				// Initializing Fields
				String rootPath = storageLocationTO.getLocationRootPath();
				String locationFolderName = storageLocationTO.getLocationFolderName();
				String subFolderprefix = storageLocationTO.getSubFolderPrefix();
				Long maxFilesSubFolder = storageLocationTO.getMaxFilesPerSubFolder();
				String subfolderName = storageLocationTO.getCurSubFolderName();
				Long curFilesSubFolder = storageLocationTO.getCurSubFolderFilesCount();

				// Logic for Document
				if (subfolderName == null && curFilesSubFolder == null) {
					subfolderName = storageLocationTO.getSubFolderPrefix() + 1;
					curFilesSubFolder = 1L;
				} else if (subfolderName != null && curFilesSubFolder >= maxFilesSubFolder) {
					int count = Integer.parseInt(subfolderName.substring(subFolderprefix.length())) + 1;
					subfolderName = subFolderprefix + count;
					curFilesSubFolder = 1L;
				} else if (subfolderName != null && curFilesSubFolder < maxFilesSubFolder) {
					curFilesSubFolder = curFilesSubFolder + 1;
				}

				String rootFileDirectory = rootPath + "\\" + locationFolderName;
				String fileDirectory = rootFileDirectory + "\\" + subfolderName;

				createFolderIfNotExists(rootFileDirectory);
				createFolderIfNotExists(fileDirectory); 
				String fileName = pk+"_"+System.currentTimeMillis()+ "." + getFileExtension(file);
				String targetFileName = fileDirectory + "\\" + fileName;
			
				saveToFile(file.getInputStream(), targetFileName);

				// update Storage Location
				result = documentManager.updateStorageLocation(storageLocationTO.getLocationId(), subfolderName,
						curFilesSubFolder);
				if (result) {
					storageData = new StorageData();
					storageData.setStorageId(storageLocationTO.getLocationId());
					storageData.setFileName(subfolderName+"/"+fileName);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return storageData;
	}

	private void saveToFile(InputStream inStream, String target) throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}

	private void createFolderIfNotExists(String dirName) throws IOException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}

	// Case for Update Document
	public StorageData updateDocument(Long pk, String locationName, MultipartFile file, String oldFileName) {
		boolean result = false;
		StorageData storageData = null;
		try {
			StorageLocationTO storageLocationTO = documentManager.getDocumentLocationRoot(locationName);
			if (storageLocationTO != null) {;

				// Initializing Fields
				String rootPath = storageLocationTO.getLocationRootPath();
				String locationFolderName = storageLocationTO.getLocationFolderName();
				//String subfolderName = storageLocationTO.getCurSubFolderName();

				String fileDirectory = rootPath + "\\" + locationFolderName;
			//	String fileDirectory = rootFileDirectory + "\\" + subfolderName;

				String oldFilePath = fileDirectory + "\\" + oldFileName;
				if (deleteFile(oldFilePath)) {
					String newFileName =null;
						if(oldFileName.contains("_"))	
							newFileName=oldFileName.substring(0, oldFileName.indexOf("_"))+"_" + System.currentTimeMillis()+ "."+ getFileExtension(file) ;
						else 
							newFileName=oldFileName.substring(0, oldFileName.lastIndexOf("."))+"_" + System.currentTimeMillis()+ "."+ getFileExtension(file) ;
						
					String newFilePath = fileDirectory + "\\" + newFileName;
					saveToFile(file.getInputStream(), newFilePath);
					storageData = new StorageData();
					storageData.setStorageId(storageLocationTO.getLocationId());
					storageData.setFileName(newFileName);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return storageData;
		}
		return storageData;
	}

	private boolean deleteFile(String oldFile) {
		File file = new File(oldFile);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	private String getFileExtension(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

	public String getDocumentLocation(Long locationId) {
		String locationPath=null;
		StorageLocationTO storageLocationTO=documentManager.getDocumentLocation(locationId);
		if (storageLocationTO != null) {
			locationPath=storageLocationTO.getLocationRootPath()+"\\"+storageLocationTO.getLocationFolderName();
		}
		
	    return locationPath;   
		
		
	}

}
