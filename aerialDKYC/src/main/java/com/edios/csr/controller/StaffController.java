package com.edios.csr.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edios.cdf.controller.AbstractController;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.validator.BaseResponse;
import com.edios.csr.bean.StaffBean;
import com.edios.csr.entity.to.StaffTo;
import com.edios.csr.manager.StaffManager;

@RestController
public class StaffController extends AbstractController {

	@Autowired
	StaffManager staffManager;

	@Autowired
	MessageSource messageSource;

	@PostMapping("/fetch-staffDetails")
	public ResponseEntity<List<StaffTo>> fetchSearchStaff(@RequestBody PayloadBean payloadBean) {
		System.out.println(
				"/search staff 8***************************************************************************************");
		System.out.println(payloadBean.getAccountId() + " " + payloadBean.getStatus() + " " + payloadBean.getSiteId());
		List<StaffTo> staffTO = staffManager.fetchSearchStaff(payloadBean);
		if (staffTO.size() == 0) {
			return new ResponseEntity<List<StaffTo>>(staffTO, HttpStatus.OK);
		} else {

			return new ResponseEntity<List<StaffTo>>(staffTO, HttpStatus.OK);
		}
	}

	@PostMapping("/get-team-leaders-list-for-staff")
	public ResponseEntity<List<StaffTo>> fetchStaff(@RequestBody PayloadBean payloadBean, HttpServletRequest request) {

		List<StaffTo> staffTO = staffManager.fetchLeadStaff(payloadBean);
		if (staffTO.size() == 0) {
			return new ResponseEntity<List<StaffTo>>(staffTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<StaffTo>>(staffTO, HttpStatus.OK);
		}
	}

	@PostMapping("/add-staff")
	public BaseResponse saveStaff(@RequestBody StaffBean staffBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		staffBean.setIpAddress(request.getRemoteAddr());
		String resultString = staffManager.addStaff(staffBean);
		if (resultString.equalsIgnoreCase("ADDED")) {
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		} else if (resultString.equalsIgnoreCase("ContactNoAlreadyExist")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED", messageSource
					.getMessage("duplicateFieldMessage", new Object[] { "Contact Number" }, "", Locale.US));
		} else if (resultString.equalsIgnoreCase("StaffCodeMappingNotFound")) {
			baseResponse = new BaseResponse(HttpStatus.OK, "StaffCodeMappingNotFound",
					"Staff Code is not able to map to given Staff Type");
		} else if (resultString.equalsIgnoreCase("CodeAlreadyExist")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED", messageSource
					.getMessage("duplicateFieldMessage", new Object[] { "Staff Code" }, "", Locale.US));
		}
		return baseResponse;
	}

	@PostMapping("/edit-staff")
	public ResponseEntity<StaffBean> findStaffById(@RequestBody PayloadBean payloadBean) {
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			StaffBean siteBean = staffManager.findStaffById(payloadBean.getId());
			return new ResponseEntity<StaffBean>(siteBean, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/update-staff")
	public BaseResponse updateSite(@RequestBody StaffBean siteBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		System.out.println("*********************************************************************************");
		try {
			siteBean.setIpAddress(request.getRemoteAddr());
			String resultString = staffManager.updateStaff(siteBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("ContactNoAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED", messageSource
						.getMessage("duplicateFieldMessage", new Object[] { "Contact Number" }, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("NameAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.CONFLICT, "CONFLICT",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "Site Name" }, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("CodeAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.CONFLICT, "CONFLICT",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "Staff Code" }, "", Locale.US));
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

	@PostMapping("/delete-staff")
	public BaseResponse deleteStaff(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
			String resultString = staffManager.deleteStaff(deleteRecords);
			if (resultString.equalsIgnoreCase("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
			} else if (resultString.equalsIgnoreCase("TransactionFailed")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("recordDeleted")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedDeleteMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("Used")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "Used", "");
			}

			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

}
