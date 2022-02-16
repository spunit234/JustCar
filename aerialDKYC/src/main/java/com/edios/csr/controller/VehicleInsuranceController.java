
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

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.bean.security.StorageLocationBean;
import com.edios.cdf.controller.AbstractController;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.validator.BaseResponse;
import com.edios.csr.bean.InsuranceCoversBean;
import com.edios.csr.bean.InsuranceNomineesBean;
import com.edios.csr.bean.InsurancePaymentsBean;
import com.edios.csr.bean.ProjectDocumentBean;
import com.edios.csr.bean.VehicleInsuranceBean;
import com.edios.csr.entity.to.VehicleInsuranceManageTO;
import com.edios.csr.entity.to.VendorDocumentTO;
import com.edios.csr.manager.VehicleInsuranceManager;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class VehicleInsuranceController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	VehicleInsuranceManager vehicleInsuranceManager;

	@PostMapping("/get-manage-insurance-details")
	public ResponseEntity<List<VehicleInsuranceManageTO>> getManageInsuranceDetails(
			@RequestBody VehicleInsuranceManageTO vehicleInsuranceManage) {
		List<VehicleInsuranceManageTO> vehicleInsuranceManageTOList = null;

		if (vehicleInsuranceManage.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			vehicleInsuranceManageTOList = vehicleInsuranceManager.getManageInsuranceDetails(vehicleInsuranceManage);
			return new ResponseEntity<List<VehicleInsuranceManageTO>>(vehicleInsuranceManageTOList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}
	
	@PostMapping("/get-existing-insurance-details")
	public ResponseEntity<List<VehicleInsuranceManageTO>> getExistingInsuranceDetails(
			@RequestBody PayloadBean payloadbean) {
		List<VehicleInsuranceManageTO> vehicleInsuranceBeanList = null;

		if (payloadbean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			vehicleInsuranceBeanList = vehicleInsuranceManager.getExistingInsuranceDetails(payloadbean);
			return new ResponseEntity<List<VehicleInsuranceManageTO>>(vehicleInsuranceBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}


	@PostMapping("/add-vehicle-insurance-details")
	public BaseResponse addVehicleInsuranceDetails(@RequestBody VehicleInsuranceBean vehicleInsuranceBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			vehicleInsuranceBean.setIpAddress(request.getRemoteAddr());

			resultString = vehicleInsuranceManager.addVehicleInsuranceDetails(vehicleInsuranceBean);
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

	@PostMapping("/get-vehicle-insurance-details-by-vehicleId")
	public ResponseEntity<VehicleInsuranceManageTO> getInsuranceDetailsByVehicleId(
			@RequestBody PayloadBean payloadBean) {
		VehicleInsuranceManageTO vehicleInsuranceBean = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			vehicleInsuranceBean = vehicleInsuranceManager.getInsuranceDetailsByVehicleId(payloadBean);
			return new ResponseEntity<VehicleInsuranceManageTO>(vehicleInsuranceBean, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-or-delete-vehicle-insurance-details")
	public BaseResponse updateOrDeleteInsuranceDetails(@RequestBody VehicleInsuranceBean vehicleInsuranceBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			vehicleInsuranceBean.setIpAddress(request.getRemoteAddr());
			resultString = vehicleInsuranceManager.updateOrDeleteInsuranceDetails(vehicleInsuranceBean);
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

	@PostMapping("/get-insurance-payment-details")
	public ResponseEntity<List<InsurancePaymentsBean>> getInsurancePaymentDetails(
			@RequestBody PayloadBean payloadBean) {
		List<InsurancePaymentsBean> insurancePaymentsBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			insurancePaymentsBeanList = vehicleInsuranceManager.getInsurancePaymentDetails(payloadBean);
			return new ResponseEntity<List<InsurancePaymentsBean>>(insurancePaymentsBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/add-insurance-payment-details")
	public BaseResponse addInsurancePaymentDetails(@RequestBody InsurancePaymentsBean insurancePaymentsBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			insurancePaymentsBean.setIpAddress(request.getRemoteAddr());
			resultString = vehicleInsuranceManager.addVehicleInsuranceDetails((AbstractBean) insurancePaymentsBean,
					"InsurancePaymentsBean");
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

	@PostMapping("/update-or-delete-insurance-payment-details")
	public BaseResponse updateOrDeletePaymentDetails(@RequestBody InsurancePaymentsBean insurancePaymentsBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			insurancePaymentsBean.setIpAddress(request.getRemoteAddr());
			resultString = vehicleInsuranceManager.updateOrDeletePaymentDetails(insurancePaymentsBean);
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
//

	@PostMapping("/get-insurance-new-cover-details")
	public ResponseEntity<List<InsuranceCoversBean>> getInsuranceNewCoverDetails(@RequestBody PayloadBean payloadBean) {
		List<InsuranceCoversBean> insuranceNewCoversBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			insuranceNewCoversBeanList = vehicleInsuranceManager.getInsuranceNewCoverDetails(payloadBean);
			return new ResponseEntity<List<InsuranceCoversBean>>(insuranceNewCoversBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/add-insurance-new-cover-details")
	public BaseResponse addInsuranceNewCoversDetails(@RequestBody InsuranceCoversBean insuranceNewCoversBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			insuranceNewCoversBean.setIpAddress(request.getRemoteAddr());
			resultString = vehicleInsuranceManager.addInsuranceNewCoversDetails(insuranceNewCoversBean);
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

	@PostMapping("/update-or-delete-insurance-new-cover-details")
	public BaseResponse updateOrDeleteNewCoversDetails(@RequestBody InsuranceCoversBean insuranceNewCoversBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			insuranceNewCoversBean.setIpAddress(request.getRemoteAddr());
			resultString = vehicleInsuranceManager.updateOrDeleteNewCoversDetails(insuranceNewCoversBean);
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

//

	@PostMapping("/get-insurance-prv-cover-details")
	public ResponseEntity<List<InsuranceCoversBean>> getInsurancePRVCoverDetails(@RequestBody PayloadBean payloadBean) {
		List<InsuranceCoversBean> insurancePrvCoversBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			insurancePrvCoversBeanList = vehicleInsuranceManager.getInsurancePRVCoverDetails(payloadBean);
			return new ResponseEntity<List<InsuranceCoversBean>>(insurancePrvCoversBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/add-insurance-prv-cover-details")
	public BaseResponse addInsurancePrvCoversDetails(@RequestBody InsuranceCoversBean insurancePrvCoversBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			insurancePrvCoversBean.setIpAddress(request.getRemoteAddr());
			resultString = vehicleInsuranceManager.addInsurancePrvCoversDetails(insurancePrvCoversBean);
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

	@PostMapping("/update-or-delete-insurance-prv-cover-details")
	public BaseResponse updateOrDeletePrvCoversDetails(@RequestBody InsuranceCoversBean insurancePrvCoversBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			insurancePrvCoversBean.setIpAddress(request.getRemoteAddr());
			resultString = vehicleInsuranceManager.updateOrDeletePrvCoversDetails(insurancePrvCoversBean);
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

	@PostMapping("/get-insurance-nominees-details")
	public ResponseEntity<List<InsuranceNomineesBean>> getInsuranceNomineeDetails(
			@RequestBody PayloadBean payloadBean) {
		List<InsuranceNomineesBean> insuranceNomineesBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			insuranceNomineesBeanList = vehicleInsuranceManager.getInsuranceNomineeDetails(payloadBean);
			return new ResponseEntity<List<InsuranceNomineesBean>>(insuranceNomineesBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/add-insurance-nominee-details")
	public BaseResponse addInsuranceNomineeDetails(@RequestBody InsuranceNomineesBean insuranceNomineesBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			insuranceNomineesBean.setIpAddress(request.getRemoteAddr());
			resultString = vehicleInsuranceManager.addInsuranceNomineeDetails(insuranceNomineesBean);
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

	@PostMapping("/update-or-delete-insurance-nominee-details")
	public BaseResponse updateOrDeleteNomineeDetails(@RequestBody InsuranceNomineesBean insuranceNomineesBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			insuranceNomineesBean.setIpAddress(request.getRemoteAddr());
			resultString = vehicleInsuranceManager.updateOrDeleteNomineeDetails(insuranceNomineesBean);
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
	
	
	
	
	
	@RequestMapping(value = "/upload-insurance-document", method = RequestMethod.POST)
	public BaseResponse uploadInsuranceDocument(HttpServletRequest request,
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
			String resultString = vehicleInsuranceManager.uploadInsuranceDocument(projectDocumentBean, file);
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
	
	@PostMapping("/fetch-insurance-document")
	public ResponseEntity<List<VendorDocumentTO>> fetchInsuranceDocumentDetails(@RequestBody PayloadBean payloadBean) {
		List<VendorDocumentTO> hiringRequestCvTO = vehicleInsuranceManager.fetchInsuranceDocumentDetails(payloadBean.getId());
		if (hiringRequestCvTO.size() == 0) {
			return new ResponseEntity<List<VendorDocumentTO>>(hiringRequestCvTO, HttpStatus.OK);

		} else {

			return new ResponseEntity<List<VendorDocumentTO>>(hiringRequestCvTO, HttpStatus.OK);
		}

	}
	
	@PostMapping("/delete-insurance-document")
	public BaseResponse deleteInsuranceDocument(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
			String resultString = vehicleInsuranceManager.deleteInsuranceDocument(deleteRecords);
			if (resultString.equalsIgnoreCase("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}


	
	
	
	
}
