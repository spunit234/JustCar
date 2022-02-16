package com.edios.cdf.controller;

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

import com.edios.cdf.bean.common.ApplicationParameterListBean;
import com.edios.cdf.entity.to.ApplicationParameterListTO;
import com.edios.cdf.manager.ParameterListManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.validator.BaseResponse;

@RestController
public class ParameterListController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	ParameterListManager parameterListManager;

	@PostMapping("/fetch-parameter-list")
	public ResponseEntity<List<ApplicationParameterListTO>> fetchParameterListByParameterId(@RequestBody PayloadBean payloadBean) {
		List<ApplicationParameterListTO> applicationParameterListTO = null;
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			applicationParameterListTO = parameterListManager.fetchParameterListByParameterId(payloadBean.getId());
			 return new ResponseEntity<List<ApplicationParameterListTO>>(applicationParameterListTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/add-parameter-list")
	public BaseResponse addParameterList(@RequestBody ApplicationParameterListBean applicationParameterListBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			applicationParameterListBean.setIpAddress(request.getRemoteAddr());
			String resultString = parameterListManager.addParameterList(applicationParameterListBean);
			if (resultString.equalsIgnoreCase("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
			} else if (resultString.equalsIgnoreCase("NameAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED", messageSource
						.getMessage("duplicateFieldMessage", new Object[] { "List Value Name" }, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("CodeAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED", messageSource
						.getMessage("duplicateFieldMessage", new Object[] { "List Value Code" }, "", Locale.US));
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/edit-parameter-list")
	public ResponseEntity<ApplicationParameterListBean> findApplicationParameterListById(@RequestBody PayloadBean payloadBean) {
		ApplicationParameterListBean applicationParameterListBean=null;
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			applicationParameterListBean = parameterListManager.findApplicationParameterListById(payloadBean.getId());
			return new ResponseEntity<ApplicationParameterListBean>(applicationParameterListBean, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/update-parameter-list")
	public BaseResponse updateParameterList(@RequestBody ApplicationParameterListBean applicationParameterListBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			applicationParameterListBean.setIpAddress(request.getRemoteAddr());
			String resultString = parameterListManager.updateParameterList(applicationParameterListBean);
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

	@PostMapping("/delete-parameter-list")
	public BaseResponse deleteParameterList(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
			String resultString = parameterListManager.deleteParameterList(deleteRecords);
			if (resultString.equalsIgnoreCase("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
			} else if (resultString.equalsIgnoreCase("TransactionFailed")) {
				baseResponse = new BaseResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST",
						messageSource.getMessage("transactionFailedUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("recordDeleted")) {
				baseResponse = new BaseResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST",
						messageSource.getMessage("transactionFailedDeleteMessage", null, "", Locale.US));
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

}
