package com.edios.cdf.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

public class ClientDetails {


    public String getReferer(HttpServletRequest request) {
        final String referer = request.getHeader("referer");
        try {
			InetAddress inetAddress = InetAddress.getByName(request.getRemoteAddr());
			return inetAddress.getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return referer;
    }

	
	    public String getClientIpAddr(HttpServletRequest request) {
	        String ip = request.getHeader("X-Forwarded-For");
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("Proxy-Client-IP");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("WL-Proxy-Client-IP");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("HTTP_CLIENT_IP");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getRemoteAddr();
	        }
	        return ip;
	    }

	    
	    public String getClientOS(HttpServletRequest request) {
//	        final String browserDetails = request.getHeader("User-Agent");
//
//	        //=================OS=======================
//	        final String lowerCaseBrowser = browserDetails.toLowerCase();
//	      
//	        if (lowerCaseBrowser.contains("windows")) {
//	            return "Windows";
//	        } else if (lowerCaseBrowser.contains("mac")) {
//	            return "Mac";
//	        } else if (lowerCaseBrowser.contains("x11")) {
//	            return "Unix";
//	        } else if (lowerCaseBrowser.contains("android")) {
//	            return "Android";
//	        } else if (lowerCaseBrowser.contains("iphone")) {
//	            return "IPhone";
//	        } else {
//	            return "UnKnown, More-Info: " + browserDetails;
//	        }
	    	
	    	 UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
	          OperatingSystem agent = userAgent.getOperatingSystem();
	          return agent.toString();
	    }

	   
	    public String getClientBrowser(HttpServletRequest request) {
	        final String browserDetails = request.getHeader("User-Agent");
	        final String user = browserDetails.toLowerCase();

	        String browser = "";

	        //===============Browser===========================
	        if (user.contains("msie")) {
	            String substring = browserDetails.substring(browserDetails.indexOf("MSIE")).split(";")[0];
	            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
	        } else if (user.contains("safari") && user.contains("version")) {
	            browser = (browserDetails.substring(browserDetails.indexOf("Safari")).split(" ")[0]).split(
	                    "/")[0] + "-" + (browserDetails.substring(
	                    browserDetails.indexOf("Version")).split(" ")[0]).split("/")[1];
	        } else if (user.contains("opr") || user.contains("opera")) {
	            if (user.contains("opera"))
	                browser = (browserDetails.substring(browserDetails.indexOf("Opera")).split(" ")[0]).split(
	                        "/")[0] + "-" + (browserDetails.substring(
	                        browserDetails.indexOf("Version")).split(" ")[0]).split("/")[1];
	            else if (user.contains("opr"))
	                browser = ((browserDetails.substring(browserDetails.indexOf("OPR")).split(" ")[0]).replace("/",
	                                                                                                           "-")).replace(
	                        "OPR", "Opera");
	        } else if (user.contains("chrome")) {
	            browser = (browserDetails.substring(browserDetails.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
	        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) || (user.indexOf(
	                "mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf(
	                "mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
	            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
	            browser = "Netscape-?";

	        } else if (user.contains("firefox")) {
	            browser = (browserDetails.substring(browserDetails.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
	        } else if (user.contains("rv")) {
	         //   browser = "IE - Unknown";
		        final String userAgentString = request.getHeader("User-Agent");
		    	String userAgentBrowserList="Internet Explorer|Trident,Microsoft Edge|Edge";
		    	String[] browserList=userAgentBrowserList.split(",");
		    	
				for(String browserDetail :browserList){
					if(userAgentString.contains(browserDetail.split("\\|")[1])){
						if(browserDetail.split("\\|")[1].equalsIgnoreCase("Trident")  && userAgentString.contains("rv:")){
							browser = (browserDetail.split("\\|")[0]); 
							browser+= " - "+(userAgentString.substring(userAgentString.indexOf("rv:")+3,userAgentString.lastIndexOf(")")));
	
						}
						else if(browserDetail.split("\\|")[1].equalsIgnoreCase("Edge")){
							browser = (browserDetail.split("\\|")[0]); 
							browser+= " - "+(userAgentString.substring(userAgentString.indexOf("Edge/")+5));
							
						}
					}
				}
	        } else {
	            browser = "UnKnown - " + browserDetails;
	        }

			
			
	    	
	        return browser;
	    }
	    
	    public String  getDeviceType(HttpServletRequest request){

	    	  UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
	          OperatingSystem agent = userAgent.getOperatingSystem();
	          return agent.getDeviceType().getName();
	         
	         
	    }
	
	
}
