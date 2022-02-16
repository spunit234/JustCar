
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
import com.edios.csr.bean.VehicleMakeBean;
import com.edios.csr.bean.VehicleModelBean;
import com.edios.csr.bean.VehicleModelPricesBean;
import com.edios.csr.bean.VehicleModelSpecsBean;
import com.edios.csr.entity.to.VehicleMakeTo;
import com.edios.csr.entity.to.VehicleModelTo;
import com.edios.csr.entity.to.VehiclePricesTo;
import com.edios.csr.manager.VehicleMakeModeManager;

@RestController
public class VehicleMakeModelController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	VehicleMakeModeManager inquiryManager;

	@PostMapping("/get-vehicleMake")
	public ResponseEntity<List<VehicleMakeTo>> fetchVehicleMake(@RequestBody VehicleMakeBean vehicleMakeBean) {
		return new ResponseEntity<List<VehicleMakeTo>>(inquiryManager.fetchVehicleMake(vehicleMakeBean),
				HttpStatus.OK);
	}

	@PostMapping("/get-vehicleModel")
	public ResponseEntity<List<VehicleModelTo>> fetchVehicleModel(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<VehicleModelTo>>(inquiryManager.fetchVehicleModel(payloadBean), HttpStatus.OK);
	}
	
	@PostMapping("/get-vehicle-model-price")
	public ResponseEntity<List<VehicleModelSpecsBean>> fetchVehicleModelPrice(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<VehicleModelSpecsBean>>(inquiryManager.fetchVehicleModelPrice(payloadBean), HttpStatus.OK);
	}


	@PostMapping("/delete-make")
	public BaseResponse deleteMake(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
			String resultString = inquiryManager.deleteMake(deleteRecords);
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

	@PostMapping("/delete-model")
	public BaseResponse deleteModel(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
			String resultString = inquiryManager.deleteModel(deleteRecords);
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

	@PostMapping("/add-make")
	public BaseResponse addMake(@RequestBody VehicleMakeBean vehicleMakeBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		vehicleMakeBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.addMake(vehicleMakeBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		else if (resultString.equalsIgnoreCase("MakeNameAlreadyExist")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Make Name" }, "", Locale.US));
		}
		return baseResponse;
	}

	@PostMapping("/add-model")
	public BaseResponse addModel(@RequestBody VehicleModelBean vehicleModelBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		vehicleModelBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.addModel(vehicleModelBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		else if (resultString.equalsIgnoreCase("ModelNameAlreadyExist")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Model Name" }, "", Locale.US));
		}
		return baseResponse;
	}

	@PostMapping("/update-make")
	public BaseResponse updateMake(@RequestBody VehicleMakeBean vehicleMakeBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			vehicleMakeBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateMake(vehicleMakeBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("MakeNameAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.CONFLICT, "CONFLICT",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "Make Name" }, "", Locale.US));
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/update-model")
	public BaseResponse updateModel(@RequestBody VehicleModelBean vehicleModelBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			vehicleModelBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateModel(vehicleModelBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("ModelNameAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.CONFLICT, "CONFLICT", messageSource
						.getMessage("duplicateFieldMessage", new Object[] { "Model Name" }, "", Locale.US));
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/edit-make")
	public ResponseEntity<VehicleMakeBean> findMakeById(@RequestBody PayloadBean payloadBean) {
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			VehicleMakeBean vehicleMakeBean = inquiryManager.findMakeById(payloadBean.getId());
			return new ResponseEntity<VehicleMakeBean>(vehicleMakeBean, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/edit-model")
	public ResponseEntity<VehicleModelBean> findModelById(@RequestBody PayloadBean payloadBean) {
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			VehicleModelBean vehicleModelBean = inquiryManager.findModelById(payloadBean.getId());
			return new ResponseEntity<VehicleModelBean>(vehicleModelBean, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/edit-model-price-details")
	public ResponseEntity<List<VehicleModelPricesBean>> findModelPriceDetailsBySpecsId(@RequestBody PayloadBean payloadBean) {
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			List<VehicleModelPricesBean> vehicleModelPricesBeanList = inquiryManager.findModelPriceDetailsBySpecsId(payloadBean.getId());
			return new ResponseEntity<List<VehicleModelPricesBean>>(vehicleModelPricesBeanList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	
	@PostMapping("/add-vehicle-model-price")
	public BaseResponse addVehicleModelPrices(@RequestBody  VehicleModelSpecsBean vehicleModelSpecsBean, HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;
		
		try {
			vehicleModelSpecsBean.setIpAddress(request.getRemoteAddr());			
			resultString = inquiryManager.addVehicleModelPrices(vehicleModelSpecsBean); 
			if (resultString.contains("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED",resultString);
			}
			else if (resultString.contains("RecordAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "RecordAlreadyExist",resultString);
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

	@PostMapping("/update-or-delete-vehicle-model-price")
	public BaseResponse updateVehiclePrices(@RequestBody VehicleModelSpecsBean vehicleModelSpecsBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			vehicleModelSpecsBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateVehiclePrices(vehicleModelSpecsBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			else if (resultString.contains("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED",resultString);
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}
	@PostMapping("/fetch-vehiclePrices")
	public ResponseEntity<List<VehiclePricesTo>> fetchVehiclePriceDetails(@RequestBody PayloadBean payloadBean) {
		List<VehiclePricesTo> vehiclePricesTo = inquiryManager.fetchVehiclePriceDetails(payloadBean);
		if (vehiclePricesTo.size() == 0) {
			return new ResponseEntity<List<VehiclePricesTo>>(vehiclePricesTo, HttpStatus.OK);

		} else {

			return new ResponseEntity<List<VehiclePricesTo>>(vehiclePricesTo, HttpStatus.OK);
		}

	}
	@PostMapping("/getModelReport")
	public ResponseEntity<List<VehiclePricesTo>> getModelReport(@RequestBody PayloadBean payloadBean) {
		List<VehiclePricesTo> vehiclePricesTo = inquiryManager.getModelReport(payloadBean);
		if (vehiclePricesTo.size() == 0) {
			return new ResponseEntity<List<VehiclePricesTo>>(vehiclePricesTo, HttpStatus.OK);

		} else {

			return new ResponseEntity<List<VehiclePricesTo>>(vehiclePricesTo, HttpStatus.OK);
		}

	}
	@PostMapping("/getMakeReport")
	public ResponseEntity<List<VehiclePricesTo>> getMakeReport(@RequestBody PayloadBean payloadBean) {
		List<VehiclePricesTo> vehiclePricesTo = inquiryManager.getMakeReport(payloadBean);
		if (vehiclePricesTo.size() == 0) {
			return new ResponseEntity<List<VehiclePricesTo>>(vehiclePricesTo, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<VehiclePricesTo>>(vehiclePricesTo, HttpStatus.OK);
		}
	}
	
}
