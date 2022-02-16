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
import com.edios.csr.bean.DealerBean;
import com.edios.csr.bean.DealerExecutiveBean;
import com.edios.csr.entity.to.DealerExecutiveTO;
import com.edios.csr.entity.to.DealerTO;
import com.edios.csr.manager.DealerManager;

@RestController
public class DealerController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	DealerManager dealerManager;

	@PostMapping("/add-dealer")
	public BaseResponse addDealer(@RequestBody DealerBean dealerBean, HttpServletRequest request) {
		
		BaseResponse baseResponse = null;
		dealerBean.setIpAddress(request.getRemoteAddr());
		String contactNumber = dealerBean.getContactNumber();
		
		int result =dealerManager.checkContactNumber(contactNumber);
		if(result == 0) {
		String resultString = dealerManager.addDealer(dealerBean);
		baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
		return baseResponse;
		}
		else {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Contact No." }, "", Locale.US));
			return baseResponse;
		}
	}
	@PostMapping("/add-dealerExecutive")
	public BaseResponse addDealerExecutive(@RequestBody DealerExecutiveBean dealerBean, HttpServletRequest request) {
		
		BaseResponse baseResponse = null;
		dealerBean.setIpAddress(request.getRemoteAddr());
		
		String resultString = dealerManager.addDealerExecutive(dealerBean);
		if(resultString.equalsIgnoreCase("Duplicate")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Contact No." }, "", Locale.US));
			return baseResponse;
		}
		baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
		return baseResponse;
	}

	@PostMapping("/get-dealerDetails")
	public ResponseEntity<List<DealerTO>> fetchdealerData(@RequestBody DealerBean customerBean) {
		return new ResponseEntity<List<DealerTO>>(dealerManager.fetchdealerData(customerBean), HttpStatus.OK);
	}
	
	@PostMapping("/delete-dealer")
	public BaseResponse deleteDealer(@RequestBody DeleteRecords deleteRecords, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		
		String resultString = dealerManager.deleteDealer(deleteRecords);
		baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", resultString);
		return baseResponse;
	}
	
	@PostMapping("/delete-dealerExecutive")
	public BaseResponse deleteDealerExecutive(@RequestBody DeleteRecords deleteRecords, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		
		String resultString = dealerManager.deleteDealerExecutive(deleteRecords);
		baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", resultString);
		return baseResponse;
	}
	@PostMapping("/update-dealer")
	public BaseResponse updateDealer(@RequestBody DealerBean customerBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		
		customerBean.setRecordType('U');
		String contactNumber = customerBean.getContactNumber();
		
		int result =dealerManager.checkContactNumberUpdate(contactNumber ,customerBean.getDealerId() );
		
			if(result == 0) {
		String resultString = dealerManager.updateDealer(customerBean);
		baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", resultString);
		return baseResponse;
		}
		else {
			baseResponse = new BaseResponse(HttpStatus.OK, "DUPLICATENUMBER","DUPLICATENUMBER");
			return baseResponse;
		}
	}
	
	@PostMapping("/update-dealerExecutive")
	public BaseResponse updateDealerExecutive(@RequestBody DealerExecutiveBean dealerExecutiveBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		
		dealerExecutiveBean.setRecordType('U');
		
		String resultString = dealerManager.updateDealerExecutive(dealerExecutiveBean);
		if(resultString.equalsIgnoreCase("Duplicate")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Contact No." }, "", Locale.US));
			return baseResponse;
		}
		baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", resultString);
		return baseResponse;
	}
	@PostMapping("/edit-dealer")
	public ResponseEntity<List<DealerBean>> editDealer(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<DealerBean>>(dealerManager.editDealer(payloadBean), HttpStatus.OK);
	}
	
	@PostMapping("/edit-dealerExecutive")
	public ResponseEntity<List<DealerExecutiveBean>> fetchdealerExecutiveData(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<DealerExecutiveBean>>(dealerManager.fetchdealerExecutiveData(payloadBean), HttpStatus.OK);
	}
	@PostMapping("/search-dealrs")
	public ResponseEntity<List<DealerTO>> searchdealerData(@RequestBody DealerBean payloadBean) {
		return new ResponseEntity<List<DealerTO>>(dealerManager.searchdealerData(payloadBean), HttpStatus.OK);
	}
	
	@PostMapping("/search-dealrExecutive")
	public ResponseEntity<List<DealerExecutiveTO>> searchdealerExecutiveData(@RequestBody PayloadBean executiveStatus) {
		return new ResponseEntity<List<DealerExecutiveTO>>(dealerManager.searchdealerExecutiveData(executiveStatus), HttpStatus.OK);
	}
	
}
