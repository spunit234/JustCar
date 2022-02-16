package com.edios.cdf.controller;

import java.security.Principal;
import java.util.List;
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

import com.edios.cdf.bean.common.ApplicationParameterBean;
import com.edios.cdf.entity.to.ApplicationParameterTO;
import com.edios.cdf.manager.ApplicationParameterManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.validator.BaseResponse;

@RestController
public class ApplicationParameterController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	ApplicationParameterManager applicationParameterManager;

	@PostMapping("/search-parameters")
	public ResponseEntity<List<ApplicationParameterTO>> fetchAppParamDetails(@RequestBody PayloadBean payloadBean) {
		List<ApplicationParameterTO> applicationParameterTO=null;
		if(payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			applicationParameterTO = applicationParameterManager.fetchAppParamDetails();
			return new ResponseEntity<List<ApplicationParameterTO>>(applicationParameterTO, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/search-parameters-on-criteria")
	public ResponseEntity<List<ApplicationParameterTO>> fetchAppParamDetail(@RequestBody PayloadBean payloadBean,Principal principal) {
		List<ApplicationParameterTO> applicationParameterTO=null;
		if(payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			applicationParameterTO= applicationParameterManager.fetchAppParameterDetails(payloadBean.getSearchParameter());
			return new ResponseEntity<List<ApplicationParameterTO>>(applicationParameterTO, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}	
	}

	@PostMapping(path = { "edit-parameter" })
	public ResponseEntity<ApplicationParameterBean> findApplicationParameterById( @RequestBody PayloadBean payloadBean) {
		if(payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			ApplicationParameterBean applicationParameterBean = applicationParameterManager
					.findApplicationParameterById(payloadBean.getId());
			return new ResponseEntity<ApplicationParameterBean>(applicationParameterBean, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}
	

	@PostMapping("/add-parameter")
	public BaseResponse addParameter(@RequestBody ApplicationParameterBean applicationParameterBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			applicationParameterBean.setIpAddress(request.getRemoteAddr());
			String resultString = applicationParameterManager.addParameter(applicationParameterBean);
			if (resultString.equalsIgnoreCase("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
			} else if (resultString.equalsIgnoreCase("NameAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED", messageSource
						.getMessage("duplicateFieldMessage", new Object[] { "Parameter Name" }, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("CodeAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED", messageSource
						.getMessage("duplicateFieldMessage", new Object[] { "Parameter Code" }, "", Locale.US));
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PutMapping("/update-parameter")
	public BaseResponse updateParameter(@RequestBody ApplicationParameterBean applicationParameterBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			applicationParameterBean.setIpAddress(request.getRemoteAddr());
			String resultString = applicationParameterManager.updateParameter(applicationParameterBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("NameAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.CONFLICT, "CONFLICT", messageSource
						.getMessage("duplicateFieldMessage", new Object[] { "Parameter Name" }, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("CodeAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.CONFLICT, "CONFLICT", messageSource
						.getMessage("duplicateFieldMessage", new Object[] { "Parameter Code" }, "", Locale.US));
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
	@PostMapping("/delete-parameter")
	public BaseResponse deleteParameter(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
		    String resultString = applicationParameterManager.deleteParameter(deleteRecords);
			if (resultString.equalsIgnoreCase("DELETED")) {
			   baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", ""); 
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
