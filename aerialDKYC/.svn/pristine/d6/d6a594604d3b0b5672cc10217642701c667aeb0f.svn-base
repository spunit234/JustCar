package com.edios.cdf.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edios.cdf.bean.common.ApplicationParameterValuesBean;
import com.edios.cdf.manager.ParameterValueManager;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.validator.BaseResponse;

@RestController
public class ParamerterValueController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	ParameterValueManager parameterValueManager;

	@PostMapping("/fetch-parameter-value")
	public ResponseEntity<ApplicationParameterValuesBean> fetchParameterValueByParameterId(
			@RequestBody PayloadBean payloadBean) {
		ApplicationParameterValuesBean applicationParameterValueBean=null;
		if(payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			applicationParameterValueBean = parameterValueManager.fetchParameterValueByParameterId(payloadBean.getId());
			return new ResponseEntity<ApplicationParameterValuesBean>(applicationParameterValueBean, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/add-parameter-value")
	public BaseResponse addParameterValue(@RequestBody ApplicationParameterValuesBean applicationParameterValueBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			applicationParameterValueBean.setUserIPAddress(request.getRemoteAddr());
			String resultString = parameterValueManager.addParameterValue(applicationParameterValueBean);
			if (resultString.equalsIgnoreCase("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
			} 
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PutMapping("/update-parameter-value")
	public BaseResponse updateParameterValue(@RequestBody ApplicationParameterValuesBean applicationParameterValueBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			applicationParameterValueBean.setUserIPAddress(request.getRemoteAddr());
			String resultString = parameterValueManager.updateParameterValue(applicationParameterValueBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("TransactionFailed")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("recordDeleted")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedDeleteMessage", null, "", Locale.US));
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

}
