package com.edios.cdf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.edios.cdf.manager.impl.StorageLocationUtil;
@RestController
public class StorageController {
	
	@Autowired
	StorageLocationUtil storageLocationUtil;
	
	@GetMapping("/view-document/{fileName}/{locationId}")
	public void viewDocument( @PathVariable String fileName,@PathVariable Long locationId,
			HttpServletRequest request, HttpServletResponse response) {
		 InputStream inputStream = null;
		System.out.println("fileName=="+fileName);
		System.out.println("locationId=="+locationId);
		String locationPath=    storageLocationUtil.getDocumentLocation(locationId);
		fileName=fileName.replace("~", "\\");
		String filePath=locationPath+"\\"+fileName;
		System.out.println("********** " + filePath);
		ServletContext context = request.getServletContext();
		
		 try {
			 File file = new File(filePath);
		              inputStream = new FileInputStream(file);
		             response.setContentType(context.getMimeType(filePath));
		             response.setHeader("Content-Disposition",  String.format("attachment; filename=\"%s\"", fileName));
		             response.setHeader("Content-Length", String.valueOf(file.length()));
		             IOUtils.copy(inputStream, response.getOutputStream());
		             response.flushBuffer();
		             inputStream.close();
		             
		             
		             
		 }catch (Exception e) {
			e.printStackTrace();
			
            try {
				inputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}
