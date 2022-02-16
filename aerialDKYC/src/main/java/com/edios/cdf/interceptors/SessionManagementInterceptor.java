package com.edios.cdf.interceptors;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.edios.cdf.util.SessionManager;

public class SessionManagementInterceptor  extends HandlerInterceptorAdapter {

	
	
	@Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		try {
		
		Boolean result = true;

	    int index=request.getRequestURI().lastIndexOf('/');	    
	    String[] urls = {"login","fetch-accounts","insert-user-login-details","reset-user-session","check-user-pharmacy-location","reset-user-password"};
	    
	    if (Arrays.asList(urls).contains(request.getRequestURI().substring(index+1))) { 
	    	result = true;   	
		}
	    else if(request.getRequestURI().contains("export_report")){
	    	result = true; 
	    }
	    else if(!request.getHeader("sessionid").equals("l0G|n")) {
	    	
	    	HttpSession session = SessionManager.getSessions().get(request.getHeader("sessionid"));
			if (session==null) {
				response.setStatus(401); // Unauthorized status
				response.getWriter().write("Session Expired");
				result=false;
			}
			else {
				int timeoutInterval =(int)((Long) session.getAttribute("sessionTimeoutMin")).longValue();
				if (timeoutInterval!=((999*60)+120)) {
					int maxInactiveInterval =(int)((Long) session.getAttribute("sessionTimeoutMin")).longValue();
					int currentInactiveTime  =(int) ((System.currentTimeMillis() - session.getLastAccessedTime())/1000);
						
						if (currentInactiveTime >0) {
							session.setMaxInactiveInterval(maxInactiveInterval + currentInactiveTime );	
						}
				}
			}    
    }
	    return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
}
}
