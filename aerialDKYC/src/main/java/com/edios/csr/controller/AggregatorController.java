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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.edios.cdf.bean.security.StorageLocationBean;
import com.edios.cdf.controller.AbstractController;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.validator.BaseResponse;
import com.edios.csr.bean.AggregatorLoanBean;
import com.edios.csr.bean.AggregatorPaymentReceivedBean;
import com.edios.csr.bean.AggregatorPaymentsBean;
import com.edios.csr.bean.AggregatorRCMovementBean;
import com.edios.csr.bean.AggregatorTasksBean;
import com.edios.csr.bean.AggregatorsBean;
import com.edios.csr.bean.ProjectDocumentBean;
import com.edios.csr.entity.to.AggregatorTO;
import com.edios.csr.entity.to.VendorDocumentTO;
import com.edios.csr.manager.AggregatorManager;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class AggregatorController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	AggregatorManager aggregatorManager;

	@PostMapping("/get-manage-aggregator-details")
	public ResponseEntity<List<AggregatorTO>> getManageAggregatorDetails(
			@RequestBody AggregatorTO aggregatorManage) {
		List<AggregatorTO> vehicleInsuranceManageTOList = null;

		if (aggregatorManage.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			vehicleInsuranceManageTOList = aggregatorManager.getManageAggregatorDetails(aggregatorManage);
			return new ResponseEntity<List<AggregatorTO>>(vehicleInsuranceManageTOList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/add-aggregator-details")
	public BaseResponse addAggregatorDetails(@RequestBody AggregatorsBean aggregatorsBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			aggregatorsBean.setIpAddress(request.getRemoteAddr());

			resultString = aggregatorManager.addAggregatorDetails(aggregatorsBean);
			if (resultString.contains("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
			} else {
				baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
						"Something went wrong! please try again later.");
			}

			return baseResponse;
		} catch (Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}

	}

	@PostMapping("/get-aggregator-details-by-InquiryId")
	public ResponseEntity<AggregatorsBean> getAggregatorDetailsByInquiryId(
			@RequestBody PayloadBean payloadBean) {
		AggregatorsBean aggregatorsBean = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			aggregatorsBean = aggregatorManager.getAggregatorDetailsByInquiryId(payloadBean);
			return new ResponseEntity<AggregatorsBean>(aggregatorsBean, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-or-delete-aggregator-details")
	public BaseResponse updateOrDeleteAggregatorDetails(@RequestBody AggregatorsBean aggregatorsBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			aggregatorsBean.setIpAddress(request.getRemoteAddr());
			resultString = aggregatorManager.updateOrDeleteAggregatorDetails(aggregatorsBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("TransactionFailed")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("RecordDoesNotExist")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "RECORD_DOES_NOT_EXIST",
						messageSource.getMessage("RecordDoesNotExistUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
			}
			return baseResponse;
		} catch (Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}
	}
	
	@PostMapping("/get-customer-details-for-payments-by-InquiryId")
	public ResponseEntity<AggregatorPaymentsBean> getCustomerDetailsForPaymentsByInquiryId(
			@RequestBody PayloadBean payloadBean) {
		AggregatorPaymentsBean aggregatorPaymentsBean = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			aggregatorPaymentsBean = aggregatorManager.getCustomerDetailsForPaymentsByInquiryId(payloadBean);
			return new ResponseEntity<AggregatorPaymentsBean>(aggregatorPaymentsBean, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}



	@PostMapping("/add-aggregator-payments-details")
	public BaseResponse addAggregatorPaymentsDetails(@RequestBody AggregatorPaymentsBean aggregatorPaymentsBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			aggregatorPaymentsBean.setIpAddress(request.getRemoteAddr());

			resultString = aggregatorManager.addAggregatorPaymentsDetails(aggregatorPaymentsBean);
			if (resultString.contains("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
			} else {
				baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
						"Something went wrong! please try again later.");
			}

			return baseResponse;
		} catch (Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}

	}

	@PostMapping("/get-aggregator-payments-details")
	public ResponseEntity<List<AggregatorPaymentsBean>> getAggregatorPaymentsDetails(
			@RequestBody PayloadBean payloadBean) {
		List<AggregatorPaymentsBean> aggregatorPaymentsBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			aggregatorPaymentsBeanList = aggregatorManager.getAggregatorPaymentsDetails(payloadBean);
			return new ResponseEntity<List<AggregatorPaymentsBean>>(aggregatorPaymentsBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-or-delete-aggregator-payments-details")
	public BaseResponse updateOrDeleteAggregatorPaymentsDetails(@RequestBody AggregatorPaymentsBean aggregatorPaymentsBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			aggregatorPaymentsBean.setIpAddress(request.getRemoteAddr());
			resultString = aggregatorManager.updateOrDeleteAggregatorPaymentsDetails(aggregatorPaymentsBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("TransactionFailed")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("RecordDoesNotExist")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "RECORD_DOES_NOT_EXIST",
						messageSource.getMessage("RecordDoesNotExistUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
			}
			return baseResponse;
		} catch (Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}
	}

	

	@PostMapping("/add-aggregator-rcmovement-details")
	public BaseResponse addAggregatorRCMovementDetails(@RequestBody AggregatorRCMovementBean aggregatorRCMovementBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			aggregatorRCMovementBean.setIpAddress(request.getRemoteAddr());

			resultString = aggregatorManager.addAggregatorRCMovementDetails(aggregatorRCMovementBean);
			if (resultString.contains("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
			} else {
				baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
						"Something went wrong! please try again later.");
			}

			return baseResponse;
		} catch (Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}

	}

	@PostMapping("/get-aggregator-rcmovement-details")
	public ResponseEntity<List<AggregatorRCMovementBean>> getAggregatorRCMovementDetails(
			@RequestBody PayloadBean payloadBean) {
		List<AggregatorRCMovementBean> aggregatorRCMovementBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			aggregatorRCMovementBeanList = aggregatorManager.getAggregatorRCMovementDetails(payloadBean);
			return new ResponseEntity<List<AggregatorRCMovementBean>>(aggregatorRCMovementBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-or-delete-aggregator-rcmovement-details")
	public BaseResponse updateOrDeleteAggregatorRCMovementDetails(@RequestBody AggregatorRCMovementBean aggregatorRCMovementBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			aggregatorRCMovementBean.setIpAddress(request.getRemoteAddr());
			resultString = aggregatorManager.updateOrDeleteAggregatorRCMovementDetails(aggregatorRCMovementBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("TransactionFailed")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("RecordDoesNotExist")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "RECORD_DOES_NOT_EXIST",
						messageSource.getMessage("RecordDoesNotExistUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
			}
			return baseResponse;
		} catch (Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}
	}

	
	@PostMapping("/get-staff-details-for-tasks")
	public ResponseEntity<List<AggregatorTasksBean>> getStaffDetailsForRCTasks(
			@RequestBody PayloadBean payloadBean) {
		List<AggregatorTasksBean> aggregatorTasksBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			aggregatorTasksBeanList = aggregatorManager.getStaffDetailsForRCTasks();
			return new ResponseEntity<List<AggregatorTasksBean>>(aggregatorTasksBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	

	@PostMapping("/add-aggregator-tasks-details")
	public BaseResponse addAggregatorTasksDetails(@RequestBody AggregatorTasksBean aggregatorTasksBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			aggregatorTasksBean.setIpAddress(request.getRemoteAddr());

			resultString = aggregatorManager.addAggregatorTasksDetails(aggregatorTasksBean);
			if (resultString.contains("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
			} else {
				baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
						"Something went wrong! please try again later.");
			}

			return baseResponse;
		} catch (Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}

	}

	@PostMapping("/get-aggregator-tasks-details")
	public ResponseEntity<List<AggregatorTasksBean>> getAggregatorTasksDetails(
			@RequestBody PayloadBean payloadBean) {
		List<AggregatorTasksBean> AggregatorTasksBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			AggregatorTasksBeanList = aggregatorManager.getAggregatorTasksDetails(payloadBean);
			return new ResponseEntity<List<AggregatorTasksBean>>(AggregatorTasksBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-or-delete-tasks-aggregator-details")
	public BaseResponse updateOrDeleteAggregatorTasksDetails(@RequestBody AggregatorTasksBean aggregatorTasksBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			aggregatorTasksBean.setIpAddress(request.getRemoteAddr());
			resultString = aggregatorManager.updateOrDeleteAggregatorTasksDetails(aggregatorTasksBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("TransactionFailed")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("RecordDoesNotExist")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "RECORD_DOES_NOT_EXIST",
						messageSource.getMessage("RecordDoesNotExistUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
			}
			return baseResponse;
		} catch (Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}
	}
	
	
	@RequestMapping(value = "/upload-aggregator-document", method = RequestMethod.POST)
	public BaseResponse uploadProjectDocument(HttpServletRequest request,
			@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam("objProjectDocumentBean") String projectDocumentBeanData) {
		BaseResponse baseResponse = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			ProjectDocumentBean projectDocumentBean = mapper.readValue(projectDocumentBeanData,
					ProjectDocumentBean.class);
			projectDocumentBean.setIpAddress(request.getRemoteAddr());
			StorageLocationBean storageLocationBean = new StorageLocationBean();
			storageLocationBean.setLocationId(1L);
			projectDocumentBean.setStorageLocation(storageLocationBean);
			projectDocumentBean.setFileName("first");// becos the m
			String resultString = aggregatorManager.uploadAggregatorDocument(projectDocumentBean, file);
			if (resultString.equalsIgnoreCase("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Some Thing went wrong! please try later.");
		}
		return baseResponse;
	}
	
	@PostMapping("/fetch-aggregator-document")
	public ResponseEntity<List<VendorDocumentTO>> fetchProjectDocumentDetails(@RequestBody PayloadBean payloadBean) {
		List<VendorDocumentTO> hiringRequestCvTO = aggregatorManager.fetchAggregatorDocumentDetails(payloadBean.getId());
		if (hiringRequestCvTO.size() == 0) {
			return new ResponseEntity<List<VendorDocumentTO>>(hiringRequestCvTO, HttpStatus.OK);

		} else {

			return new ResponseEntity<List<VendorDocumentTO>>(hiringRequestCvTO, HttpStatus.OK);
		}

	}
	
	@PostMapping("/delete-aggregator-document")
	public BaseResponse deleteAggregatorDocument(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
			String resultString = aggregatorManager.deleteAggregatorDocument(deleteRecords);
			if (resultString.equalsIgnoreCase("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/add-aggregatorLoan")
	public BaseResponse addAggregatorLoan(@RequestBody AggregatorLoanBean aggregatorLoanBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		aggregatorLoanBean.setIpAddress(request.getRemoteAddr());
		String resultString = aggregatorManager.addAggregatorLoan(aggregatorLoanBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		else if (resultString.equalsIgnoreCase("MakeNameAlreadyExist")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Make Name" }, "", Locale.US));
		}
		return baseResponse;
	}
	
	@PostMapping("/get-aggregator-loan-details")
	public ResponseEntity<AggregatorLoanBean> getAggregatorLoanDetails(
			@RequestBody PayloadBean payloadBean) {
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			return new ResponseEntity<AggregatorLoanBean>(aggregatorManager.getAggregatorLoanDetails(payloadBean), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@PostMapping("/update-aggregatorLoan")
	public BaseResponse updateAggregatorLoan(@RequestBody AggregatorLoanBean aggregatorLoanBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			aggregatorLoanBean.setIpAddress(request.getRemoteAddr());
			String resultString = aggregatorManager.updateAggregatorLoan(aggregatorLoanBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}
	@PostMapping("/update-all-rc-task")
	public BaseResponse updateAllRCTask(@RequestBody AggregatorTasksBean aggregatorTasksBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			aggregatorTasksBean.setIpAddress(request.getRemoteAddr());
			String resultString = aggregatorManager.updateAllRCTask(aggregatorTasksBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}
	@PostMapping("/add-aggregatorPaymentReceived")
	public BaseResponse addAggregatorPaymentReceived(@RequestBody AggregatorPaymentReceivedBean aggregatorPaymentReceivedBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		aggregatorPaymentReceivedBean.setIpAddress(request.getRemoteAddr());
		String resultString = aggregatorManager.addAggregatorPaymentReceived(aggregatorPaymentReceivedBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		else if (resultString.equalsIgnoreCase("MakeNameAlreadyExist")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Make Name" }, "", Locale.US));
		}
		return baseResponse;
	}
	
	@PostMapping("/get-aggregator-payment-received")
	public ResponseEntity<List<AggregatorPaymentReceivedBean>>  getAggregatorPaymentReceivedDetails(
			@RequestBody PayloadBean payloadBean) {
		List<AggregatorPaymentReceivedBean> aggregatorPaymentReceivedList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			aggregatorPaymentReceivedList = aggregatorManager.getAggregatorPaymentReceivedDetails(payloadBean);
			return new ResponseEntity<List<AggregatorPaymentReceivedBean>>(aggregatorPaymentReceivedList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	@PostMapping("/update-delete-aggregator-payment-received")
	public BaseResponse updateOrDeleteAggregatorPaymentReceived(@RequestBody AggregatorPaymentReceivedBean aggregatorPaymentReceivedBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			aggregatorPaymentReceivedBean.setIpAddress(request.getRemoteAddr());
			String resultString = aggregatorManager.updateOrDeleteAggregatorPaymentReceived(aggregatorPaymentReceivedBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}
}
