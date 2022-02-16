package com.edios.cdf.util;

public class EmailTemplate {
	
	public String createHtmlForEmail(String userName,String loginName,String userPassword) {
		  StringBuilder tableBuilder=new StringBuilder();
		  try {  
		
		  tableBuilder.append("<body> Dear "+userName+", <br><br>"
		  		                   + "  As per your request, your password has been successfully set "
		  		                   + " and please find below the details:<br><br>Username: "+loginName+"<br><br>Password: "+userPassword+"<br>"
		  		                   		+ "<br><br>Thanks<br><br>Application Support Team<br> "
		  		                   		+ "<p align='justify'>NOTE : This email transmission and any documents, files or previous email messages attached to it may contain information that is confidential or legally privileged. If you are not the intended recipient or a person responsible for delivering this transmission to the intended recipient you are hereby notified that you must not read this transmission and that any disclosure, copying, printing, distribution or use of this transmission is strictly prohibited. If you have received this transmission in error, please immediately notify the sender by telephone or return email and delete the original transmission and its attachments without reading or saving in any manner. </p> </body>");
		    
		  }catch(Exception e) {
		   e.printStackTrace();
		  }   
		  return tableBuilder.toString();
		 }
}
