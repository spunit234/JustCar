package com.edios.cdf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edios.cdf.bean.common.ApplicationParameterListBean;
import com.edios.cdf.display.ParameterDropDownTO;
import com.edios.cdf.entity.to.ApplicationParameterValueTO;
import com.edios.cdf.entity.to.UserEntityTO;
import com.edios.cdf.manager.DisplayManager;
import com.edios.cdf.manager.SecurityManager;
import com.edios.cdf.validator.BaseResponse;

@RestController
public class EmsDisplayController {


	@Autowired
	private DisplayManager displayManager;
	
	@Autowired
	SecurityManager securityManager;


	@GetMapping(path = { "/parameter-list-drop-down/{parameterCode}" })
	public ResponseEntity<List<ParameterDropDownTO>> parameterListDropdown(
			@PathVariable("parameterCode") String parameterCode) {
		List<ParameterDropDownTO> applicationParameterListTO = displayManager.parameterListDropdown(parameterCode);
		return new ResponseEntity<List<ParameterDropDownTO>>(applicationParameterListTO, HttpStatus.OK);
	}

	@GetMapping(path = { "/parameter-values/{parameterCode}" })
	public ResponseEntity<ApplicationParameterValueTO> parameterValues(
			@PathVariable("parameterCode") String parameterCode) {
		ApplicationParameterValueTO parameterValueTO = displayManager.parameterValues(parameterCode);
		return new ResponseEntity<ApplicationParameterValueTO>(parameterValueTO, HttpStatus.OK);
	}

	@GetMapping(path = { "/fetch-Parameter-details/{parameterCode}" })
	public ResponseEntity<List<ApplicationParameterListBean>> fetchParameterDetails(
			@PathVariable("parameterCode") String parameterCode) {
		List<ApplicationParameterListBean> applicationParameterListTO = displayManager
				.fetchParameterDetails(parameterCode);
		return new ResponseEntity<List<ApplicationParameterListBean>>(applicationParameterListTO, HttpStatus.OK);
	}
	@RequestMapping(value = "/send-password-mail", method = RequestMethod.POST)
	public BaseResponse sendPasswordMail(@RequestBody UserEntityTO  userEntityTO,HttpServletRequest request){
		BaseResponse baseResponse = null;
		String resultString="";
		try {
			resultString=securityManager.sendPasswordMail(userEntityTO,request.getRemoteAddr());
			System.out.println(resultString);
			if(resultString.equals("MailSent"))
				baseResponse = new BaseResponse(HttpStatus.OK, "MailSent", "Mail is sent successfully.");
			else if(resultString.equals("MailNotSent")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "MailNotSent", "Something went wrong! please try later.");
			}
			else if(resultString.equals("InvalidCredentials")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "InvalidCredentials", "Username or Email address is incorrect.");
			}
		}catch(Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION", "Something went wrong! please try later.");
		}
		return baseResponse;
	}
	
	@RequestMapping(value = "/reset-forgot-user-password", method = RequestMethod.POST)
	public BaseResponse resetUserPassword(@RequestBody UserEntityTO  userEntityTO,HttpServletRequest request){
		BaseResponse baseResponse = null;
		String resultString="";
		String ipAddress = request.getRemoteAddr();
		try {
			resultString=securityManager.updateForgotPassword(userEntityTO,ipAddress);
			if(resultString.equals("UPDATED"))
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
		}catch(Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION", "Something went wrong! please try later.");
		}
		return baseResponse;
	}
}
