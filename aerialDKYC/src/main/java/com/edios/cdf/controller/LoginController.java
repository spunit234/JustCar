package com.edios.cdf.controller;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edios.cdf.entity.to.AccountUserEntityTO;
import com.edios.cdf.entity.to.MenuEntityTO;
import com.edios.cdf.entity.to.UserDetailTO;
import com.edios.cdf.entity.to.UserEntityTO;
import com.edios.cdf.manager.SecurityManager;
import com.edios.cdf.util.ClientDetails;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.util.SessionManager;
import com.edios.cdf.validator.BaseResponse;



@RestController
public class LoginController extends AbstractController {

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	SecurityManager securityManager;
	
	
	ClientDetails clientDetails = new ClientDetails();

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = { "text/html" })
	public String getWelcomeFile() {
		return "<html> " + "<title>" + "ediosCDF API" + "</title>" + "<body></br></br></br>" + "</br></br></br>" + ""
				+ "<center><h1>" + "       Welcome to ediosPMS API." + "</br></br>  RESTful API is Working!"
				+ "</center></h1> </body>" + "</html> ";
	}

	@GetMapping("/login")
	public ResponseEntity<UserDetailTO> fetchUserDetails(Principal user,HttpServletRequest request) {
		try {
			System.out.println("hello");
		UserDetailTO userDetailTO = securityManager.getCurrentUser(user.getName());
		if (userDetailTO == null) {
			return new ResponseEntity<UserDetailTO>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<UserDetailTO>(userDetailTO, HttpStatus.OK);
		}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping("/fetch-accounts")
	public ResponseEntity<List<AccountUserEntityTO>> fetchAccounts(@RequestBody PayloadBean payloadBean) {
		List<AccountUserEntityTO> AccountUserEntityTOList=null;
		if(payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			AccountUserEntityTOList = securityManager.fetchAccounts(payloadBean.getId());
		}
		return new ResponseEntity<List<AccountUserEntityTO>>(AccountUserEntityTOList, HttpStatus.OK);
	}
	
	@PostMapping("/menus")
	public ResponseEntity<List<MenuEntityTO>> fetchAccountMenus(@RequestBody PayloadBean payloadBean) {
		List<MenuEntityTO> menuEntityTOList=null;
		if(payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			menuEntityTOList = securityManager.fetchAccountMenus(payloadBean);
		}
		return new ResponseEntity<List<MenuEntityTO>>(menuEntityTOList, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/update-user-default-theme", method = RequestMethod.POST)
	public BaseResponse updateDefaultThemeForUser(HttpServletRequest request,@RequestBody PayloadBean payloadBean)
			{
		BaseResponse baseResponse = null;
		String resultString="";
		try {
			payloadBean.setCustomParameter(request.getRemoteAddr());
			resultString=securityManager.updateDefaultThemeForUser(payloadBean);
			if(resultString.equals("UPDATED"))
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
		}catch(Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION", "Some Thing went wrong! please try later.");
		}
		return baseResponse;
	}
	
	
	@PostMapping("/insert-user-login-details")
	public UserEntityTO insertUserLoginDetails(@RequestBody PayloadBean payloadBean,HttpServletRequest request) {
		try {
			
			 String referer = clientDetails.getReferer(request);
	        // String clientIpAddr = clientDetails.getClientIpAddr(request);
			 String clientIpAddr = request.getRemoteAddr();
	         String clientOS = clientDetails.getClientOS(request);
	         String clientBrowser = clientDetails.getClientBrowser(request);
	         int index = clientBrowser.indexOf("-");
	         String browserName = clientBrowser.substring(0, index);
	         String browserVersion = clientBrowser.substring( index+1);
	         String deviceType = clientDetails.getDeviceType(request);

	        return securityManager.insertUserLoginDetails(payloadBean,referer,clientIpAddr,clientOS,browserName,browserVersion,deviceType,request);
	        
		}catch(Exception e) {
			e.printStackTrace();
			return null;
	}
	}
	
	@RequestMapping(value = "/update-user-login-details", method = RequestMethod.POST)
	public BaseResponse updateUserLoginDetails(@RequestBody PayloadBean payloadBean){
		BaseResponse baseResponse = null;
		String resultString="";
		try {
			resultString=securityManager.updateUserLoginDetails(payloadBean);
			if(resultString.equals("UPDATED")) {
				 SessionManager.invalidate(payloadBean.getSessionID());
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
		}catch(Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION", "Something went wrong! please try later.");
		}
		return baseResponse;
	}
	
	@RequestMapping(value = "/reset-user-password", method = RequestMethod.POST)
	public BaseResponse resetUserPassword(@RequestBody UserEntityTO  userEntityTO,HttpServletRequest request){
		BaseResponse baseResponse = null;
		String resultString="";
		String ipAddress = request.getRemoteAddr();
		try {
			resultString=securityManager.resetUserPassword(userEntityTO,ipAddress);
			if(resultString.equals("UPDATED"))
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
		}catch(Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION", "Something went wrong! please try later.");
		}
		return baseResponse;
	}
	
	@PostMapping("/reset-user-session")
	public BaseResponse resetUserSession(@RequestBody UserEntityTO  userEntityTO,HttpServletRequest request) {
		//BaseResponse baseResponse  =null;
		try {
		
			PayloadBean payloadBean = new PayloadBean();
			
			 HttpSession session = SessionManager.sessions.get(userEntityTO.getSessionID());
			 if (session!=null ) {
				 try {
				 payloadBean.setLoginDetailId((Long) session.getAttribute("userLoginId"));
				 payloadBean.setUserId(userEntityTO.getUserID());
				 payloadBean.setLogoutReason("FORCED");
				 securityManager.updateUserLoginDetails(payloadBean);
				 SessionManager.invalidate(userEntityTO.getSessionID());
				 }
				 catch (Exception e) {
					//System.out.println("Session Already Timed Out. Browser Closed Case");
				}	
			}
			
	        return new BaseResponse(HttpStatus.OK, "UPDATED", "");
		}catch(Exception e) {
			e.printStackTrace();
			 return new BaseResponse(HttpStatus.OK, "ERROR", "");
			
	}
	}
	
	@PostMapping("/reset_server_time")
	public BaseResponse resetServerTime(@RequestBody PayloadBean payloadBean,HttpServletRequest request) {
		try {	
	        return new BaseResponse(HttpStatus.OK, "UPDATED", "");
		}catch(Exception e) {
			e.printStackTrace();
			 return new BaseResponse(HttpStatus.OK, "ERROR", "");
			
	}
	}
	
	@PostMapping("/siteName")
	public ResponseEntity<UserDetailTO> fetchUserDetails(@RequestBody PayloadBean payloadBean) {
		try {
		UserDetailTO userDetailTO = securityManager.getSiteName(payloadBean.getId());
		if (userDetailTO == null) {
			return new ResponseEntity<UserDetailTO>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<UserDetailTO>(userDetailTO, HttpStatus.OK);
		}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
