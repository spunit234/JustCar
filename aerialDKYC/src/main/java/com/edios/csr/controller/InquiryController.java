package com.edios.csr.controller;

import java.util.Date;
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
import com.edios.csr.bean.CustomerAddressBean;
import com.edios.csr.bean.CustomerBean;
import com.edios.csr.bean.InquiryBean;
import com.edios.csr.bean.InquiryImagesBean;
import com.edios.csr.bean.InquiryNotesBean;
import com.edios.csr.bean.LoanAddressBean;
import com.edios.csr.bean.LoanBean;
import com.edios.csr.bean.LoanCoborrowerBean;
import com.edios.csr.bean.LoanCommissionBean;
import com.edios.csr.bean.LoanCrossSaleBean;
import com.edios.csr.bean.LoanMembersBean;
import com.edios.csr.bean.LoanNotesBean;
import com.edios.csr.bean.LoanReferencesBean;
import com.edios.csr.bean.LoanStatusBean;
import com.edios.csr.bean.LoanSurityChequesBean;
import com.edios.csr.bean.LoanTransStatusBean;
import com.edios.csr.bean.LoanVehicleInvoiceBean;
import com.edios.csr.bean.ManageLoansBean;
import com.edios.csr.bean.ProjectDocumentBean;
import com.edios.csr.bean.StaffBean;
import com.edios.csr.bean.VehiclesBean;
import com.edios.csr.entity.to.CustomersTO;
import com.edios.csr.entity.to.DealerExecutiveTo2;
import com.edios.csr.entity.to.DealerTo2;
import com.edios.csr.entity.to.InquiryDocumentTO;
import com.edios.csr.entity.to.InquiryNotesTO;
import com.edios.csr.entity.to.InquiryTO;
import com.edios.csr.entity.to.LoanCoborrowerTo;
import com.edios.csr.entity.to.LoanCommissionTo;
import com.edios.csr.entity.to.LoanCrossSaleTo;
import com.edios.csr.entity.to.LoanNotesTo;
import com.edios.csr.entity.to.LoanStatusTo;
import com.edios.csr.entity.to.LoanSurityChequesTo;
import com.edios.csr.entity.to.LoanTo;
import com.edios.csr.entity.to.LoanVehicleInvoiceTo;
import com.edios.csr.entity.to.StaffTo;
import com.edios.csr.entity.to.VehicleTo;
import com.edios.csr.entity.to.VendorDocumentTO;
import com.edios.csr.manager.InquiryManager;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class InquiryController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	InquiryManager inquiryManager;

	@PostMapping("/get-customerDetails")
	public ResponseEntity<List<CustomersTO>> fetchcustomerData(@RequestBody CustomerAddressBean customerAddressBean) {
		return new ResponseEntity<List<CustomersTO>>(inquiryManager.fetchcustomerData(customerAddressBean),
				HttpStatus.OK);
	}

	@PostMapping("/get-staffDetails")
	public ResponseEntity<List<StaffTo>> fetchStaffData(@RequestBody StaffBean staffBean) {
		return new ResponseEntity<List<StaffTo>>(inquiryManager.fetchStaffData(staffBean), HttpStatus.OK);
	}

	@PostMapping("/add-inquiry")
	public BaseResponse addInquiry(@RequestBody InquiryBean inquiryBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		inquiryBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.addInquiry(inquiryBean);
		baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
		return baseResponse;
	}

	@PostMapping("/add-customer1")
	public BaseResponse addCustomer(@RequestBody CustomerBean customerBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		customerBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.addCustomer(customerBean);
		baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
		return baseResponse;
	}

	@PostMapping("/add-inquiryVehicle")
	public BaseResponse addInquiryVehicle(@RequestBody VehiclesBean inquiryVehiclesBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		inquiryVehiclesBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.addInquiryVehicle(inquiryVehiclesBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		return baseResponse;
	}

	@PostMapping("/sendMail")
	public BaseResponse sendMail(@RequestBody PayloadBean payloadBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		inquiryManager.sendMail(Long.parseLong(payloadBean.getSearchParameter()));
		baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		return baseResponse;
	}

	@PostMapping("/get-inquiryDetails")
	public ResponseEntity<List<InquiryTO>> fetchInquiryData(@RequestBody InquiryBean inquiryBean) {
		return new ResponseEntity<List<InquiryTO>>(inquiryManager.fetchInquiryData(inquiryBean), HttpStatus.OK);
	}

	@PostMapping("/add-inquiryNotes")
	public BaseResponse inquiryNotesEntry(@RequestBody InquiryNotesBean inquiryNotesBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		inquiryNotesBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.inquiryNotesEntry(inquiryNotesBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		return baseResponse;
	}

	@PostMapping("/get-inquiryNotesDetails")
	public ResponseEntity<List<InquiryNotesTO>> getInquiryNotes(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<InquiryNotesTO>>(inquiryManager.getInquiryNotes(payloadBean), HttpStatus.OK);
	}

	@PostMapping("/edit-inquiry")
	public ResponseEntity<InquiryBean> findInquiryById(@RequestBody PayloadBean payloadBean) {
		InquiryBean inquiryBean = null;
		try {
			inquiryBean = inquiryManager.findInquiryById(payloadBean);
		} catch (Exception exception) {
			exception.printStackTrace();
			return new ResponseEntity<InquiryBean>(inquiryBean, HttpStatus.OK);
		}
		return new ResponseEntity<InquiryBean>(inquiryBean, HttpStatus.OK);
	}

	@PostMapping("/edit-customer1")
	public ResponseEntity<List<CustomersTO>> editCustomer(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<CustomersTO>>(inquiryManager.editCustomer(payloadBean), HttpStatus.OK);
	}

	@PostMapping("/edit-vehicle")
	public ResponseEntity<List<VehicleTo>> editVehicle(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<VehicleTo>>(inquiryManager.editVehicle(payloadBean), HttpStatus.OK);
	}

	@PostMapping("/update-vehicle")
	public BaseResponse updateVehicle(@RequestBody VehiclesBean vehiclesBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			vehiclesBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateVehicle(vehiclesBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/add-loan")
	public BaseResponse addLoan(@RequestBody LoanBean loanBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		loanBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.addLoan(loanBean);
		 if (resultString.equalsIgnoreCase("LoanNoAlreadyExist")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Loan No" }, "", Locale.US));
		}
		 else {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
		 }
		return baseResponse;
	}

	@PostMapping("/edit-loan")
	public ResponseEntity<List<LoanTo>> editloan(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<LoanTo>>(inquiryManager.editloan(payloadBean), HttpStatus.OK);
	}

	@PostMapping("/update-loan")
	public BaseResponse updateLoan(@RequestBody LoanBean loanBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			loanBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateLoan(loanBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/fetch-project-document")
	public ResponseEntity<List<VendorDocumentTO>> fetchProjectDocumentDetails(@RequestBody PayloadBean payloadBean) {
		List<VendorDocumentTO> hiringRequestCvTO = inquiryManager.fetchProjectDocumentDetails(payloadBean.getId());
		if (hiringRequestCvTO.size() == 0) {
			return new ResponseEntity<List<VendorDocumentTO>>(hiringRequestCvTO, HttpStatus.OK);

		} else {

			return new ResponseEntity<List<VendorDocumentTO>>(hiringRequestCvTO, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/upload-project-document", method = RequestMethod.POST)
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
			String resultString = inquiryManager.uploadProjectDocument(projectDocumentBean, file);
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

	@PostMapping("/add-loanNotes")
	public BaseResponse loanNotesEntry(@RequestBody LoanNotesBean loanNotesBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		loanNotesBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.loanNotesEntry(loanNotesBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		return baseResponse;
	}

	@PostMapping("/get-loanNotesDetails")
	public ResponseEntity<List<LoanNotesTo>> getLoanNotes(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<LoanNotesTo>>(inquiryManager.getLoanNotes(payloadBean), HttpStatus.OK);
	}

	@PostMapping("/add-loanCrossSale")
	public BaseResponse saveLoanCrossSale(@RequestBody LoanCrossSaleBean loanCrossSaleBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		loanCrossSaleBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.saveLoanCrossSale(loanCrossSaleBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		return baseResponse;
	}

	@PostMapping("/get-loanCrossSaleDetails")
	public ResponseEntity<List<LoanCrossSaleTo>> getLoanCrossSale(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<LoanCrossSaleTo>>(inquiryManager.getLoanCrossSale(payloadBean), HttpStatus.OK);
	}

	@PostMapping("/add-loanSurityCheque")
	public BaseResponse saveLoanSurityCheques(@RequestBody LoanSurityChequesBean loanSurityChequesBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		loanSurityChequesBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.saveLoanSurityCheques(loanSurityChequesBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		return baseResponse;
	}

	@PostMapping("/get-loanSurityChequeDetails")
	public ResponseEntity<List<LoanSurityChequesTo>> getLoanSurityCheques(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<LoanSurityChequesTo>>(inquiryManager.getLoanSurityCheques(payloadBean),
				HttpStatus.OK);
	}

	@PostMapping("/add-loanTransStatus")
	public BaseResponse saveLoanTransStatus(@RequestBody LoanTransStatusBean loanTransStatusBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		loanTransStatusBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.saveLoanTransStatus(loanTransStatusBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		return baseResponse;
	}

	@PostMapping("/delete-loanCrossSale")
	public BaseResponse deleteLoanCrossSell(@RequestBody PayloadBean payloadBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			payloadBean.setCustom1(request.getRemoteAddr());
			String resultString = inquiryManager.deleteLoanCrossSell(payloadBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/delete-loanSurityCheque")
	public BaseResponse deleteLoanSurityCheques(@RequestBody PayloadBean payloadBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			payloadBean.setCustom1(request.getRemoteAddr());
			String resultString = inquiryManager.deleteLoanSurityCheques(payloadBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/updateloan_Status")
	public BaseResponse updateloanStatus(@RequestBody LoanStatusBean loanstatusBean[], HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		int i = loanstatusBean.length;
		for (int j = 0; j < i; j++) {

			LoanStatusBean loanstatus = (LoanStatusBean) loanstatusBean[j];
			loanstatus.setIpAddress(request.getRemoteAddr());
			if (loanstatus.getVehopenid() == 0) {
				loanstatus.setRecordType('I');
				resultString = inquiryManager.updateloanStatus(loanstatus);
				if (resultString.equalsIgnoreCase("UPDATED"))
					baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else {
				loanstatus.setRecordType('U');
				inquiryManager.wvokupdateloanStatus(loanstatus);
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
		}

		return baseResponse;

	}

	@PostMapping("/edit-loanstatus")
	public ResponseEntity<List<LoanStatusTo>> fetchLoanStatus(@RequestBody int vehicleId, HttpServletRequest request) {
		return new ResponseEntity<List<LoanStatusTo>>(inquiryManager.fetchloanstatus(vehicleId), HttpStatus.OK);
	}

	@PostMapping("/delete-loanstatus")
	public BaseResponse delloanStatus(@RequestBody LoanStatusBean loanstatusBean, HttpServletRequest request) {
		int vid = loanstatusBean.getVehopenid();
		inquiryManager.delloanStatus(vid);
		BaseResponse baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
		;
		return baseResponse;
	}

	// code
	@PostMapping("/update-transactionDetail")
	public BaseResponse saveVehicleTransactionDetail(@RequestBody VehiclesBean vehiclesBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			vehiclesBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.saveVehicleTransactionDetail(vehiclesBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/update-transactionNo")
	public BaseResponse saveTransactionNo(@RequestBody InquiryBean inquiryBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			inquiryBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.saveTransactionNo(inquiryBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/get-inquiryNo")
	public ResponseEntity<List<InquiryTO>> fetchInquiryNo(@RequestBody InquiryBean inquiryBean) {
		return new ResponseEntity<List<InquiryTO>>(inquiryManager.fetchInquiryNo(inquiryBean), HttpStatus.OK);
	}

	@PostMapping("/get-loanDetails")
	public ResponseEntity<List<InquiryTO>> fetchInquiryLoanData(@RequestBody ManageLoansBean manageLoansBean) {
		return new ResponseEntity<List<InquiryTO>>(inquiryManager.fetchInquiryLoanData(manageLoansBean), HttpStatus.OK);
	}

	@RequestMapping(value = "/uploadInquiryImages", method = RequestMethod.POST)
	public BaseResponse uploadInquiryImages(HttpServletRequest request,
			@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam("inquiryImagesBean") String inquiryImages) {
		BaseResponse baseResponse = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			InquiryImagesBean inquiryImagesBean = mapper.readValue(inquiryImages, InquiryImagesBean.class);
			inquiryImagesBean.setIpAddress(request.getRemoteAddr());
			StorageLocationBean storageLocationBean = new StorageLocationBean();
			storageLocationBean.setLocationId(1L);
			inquiryImagesBean.setStorageLocationId(storageLocationBean);
			inquiryImagesBean.setFileName("first");// becos the m
			String resultString = inquiryManager.uploadInquiryImages(inquiryImagesBean, file);
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

	@PostMapping("/fetch-inquiry-document")
	public ResponseEntity<List<InquiryDocumentTO>> fetchInquiryDocumentDetails(@RequestBody PayloadBean payloadBean) {
		List<InquiryDocumentTO> hiringRequestCvTO = inquiryManager.fetchInquiryDocumentDetails(payloadBean.getId());
		if (hiringRequestCvTO.size() == 0) {
			return new ResponseEntity<List<InquiryDocumentTO>>(hiringRequestCvTO, HttpStatus.OK);

		} else {

			return new ResponseEntity<List<InquiryDocumentTO>>(hiringRequestCvTO, HttpStatus.OK);
		}

	}

	@PostMapping("/delete-document")
	public BaseResponse deleteDocument(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
			String resultString = inquiryManager.deleteDocument(deleteRecords);
			if (resultString.equalsIgnoreCase("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/get-dealerName")
	public ResponseEntity<List<DealerTo2>> fetchDealerName(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<DealerTo2>>(inquiryManager.fetchDealerName(payloadBean), HttpStatus.OK);
	}

	@PostMapping("/get-dealerExecutiveName")
	public ResponseEntity<List<DealerExecutiveTo2>> fetchDealerExecutiveName(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<DealerExecutiveTo2>>(inquiryManager.fetchDealerExecutiveName(payloadBean),
				HttpStatus.OK);
	}

	@PostMapping("/add-customerAddress")
	public BaseResponse addCustomerAddress(@RequestBody CustomerAddressBean customerAddressBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		customerAddressBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.addCustomerAddress(customerAddressBean);
		baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
		return baseResponse;
	}

	@PostMapping("/update-customerAddress")
	public BaseResponse updateCustomerAddress(@RequestBody CustomerAddressBean customerAddressBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			customerAddressBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateCustomerAddress(customerAddressBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/update-loanCrossSale")
	public BaseResponse updateLoanCrossSell(@RequestBody LoanCrossSaleBean loanCrossSaleBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			loanCrossSaleBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateLoanCrossSell(loanCrossSaleBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/add-loanCommission")
	public BaseResponse saveLoanCommission(@RequestBody LoanCommissionBean loanCommissionBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		loanCommissionBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.saveLoanCommission(loanCommissionBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		return baseResponse;
	}

	@PostMapping("/add-loanVehicleInvoice")
	public BaseResponse saveVehicleInvoice(@RequestBody LoanVehicleInvoiceBean loanVehicleInvoiceBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		loanVehicleInvoiceBean.setIpAddress(request.getRemoteAddr());
		String resultString = inquiryManager.saveVehicleInvoice(loanVehicleInvoiceBean);
		if (resultString.equalsIgnoreCase("Added"))
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		return baseResponse;
	}

	@PostMapping("/delete-loanCommission")
	public BaseResponse deleteLoanCommission(@RequestBody PayloadBean payloadBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			payloadBean.setCustom1(request.getRemoteAddr());
			String resultString = inquiryManager.deleteLoanCommission(payloadBean);
			if (resultString.equalsIgnoreCase("Deleted")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "Deleted", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/delete-loanVehicleInvoice")
	public BaseResponse deleteLoanVehicleInvoice(@RequestBody PayloadBean payloadBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			payloadBean.setCustom1(request.getRemoteAddr());
			String resultString = inquiryManager.deleteLoanVehicleInvoice(payloadBean);
			if (resultString.equalsIgnoreCase("Deleted")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "Deleted", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/get-loanCommission")
	public ResponseEntity<List<LoanCommissionTo>> getLoanCommission(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<LoanCommissionTo>>(inquiryManager.getLoanCommission(payloadBean), HttpStatus.OK);
	}

	@PostMapping("/get-loanVehicleInvoice")
	public ResponseEntity<List<LoanVehicleInvoiceTo>> getLoanVehicleInvoice(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<LoanVehicleInvoiceTo>>(inquiryManager.getLoanVehicleInvoice(payloadBean),
				HttpStatus.OK);
	}

	@PostMapping("/update-loanSurityCheques")
	public BaseResponse updateLoanSurityCheques(@RequestBody LoanSurityChequesBean loanSurityChequesBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			loanSurityChequesBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateLoanSurityCheques(loanSurityChequesBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/update-loanCommission")
	public BaseResponse updateLoanCommission(@RequestBody LoanCommissionBean loanCommissionBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			loanCommissionBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateLoanCommission(loanCommissionBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/update-loanVehicleInvoice")
	public BaseResponse updateLoanVehicleInvoice(@RequestBody LoanVehicleInvoiceBean loanVehicleInvoiceBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			loanVehicleInvoiceBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateLoanVehicleInvoice(loanVehicleInvoiceBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/add-loan-address-details")
	public BaseResponse addLoanAddressDetails(@RequestBody LoanAddressBean loanAddressBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			loanAddressBean.setIpAddress(request.getRemoteAddr());
			resultString = inquiryManager.addLoanAddressDetails(loanAddressBean);
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

	@PostMapping("/add-loan-member-details")
	public BaseResponse addLoanMembersDetails(@RequestBody LoanMembersBean loanMembersBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			loanMembersBean.setIpAddress(request.getRemoteAddr());
			resultString = inquiryManager.addLoanMembersDetails(loanMembersBean);
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

	@PostMapping("/add-loan-reference-details")
	public BaseResponse addLoanReferencesDetails(@RequestBody LoanReferencesBean loanReferencesBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			loanReferencesBean.setIpAddress(request.getRemoteAddr());
			resultString = inquiryManager.addLoanReferencesDetails(loanReferencesBean);
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

	@PostMapping("/get-loan-address-details")
	public ResponseEntity<List<LoanAddressBean>> getLoanAddressDetails(@RequestBody PayloadBean payloadBean) {
		List<LoanAddressBean> loanAddressBean = null;
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			loanAddressBean = inquiryManager.getLoanAddressDetails(payloadBean);
			return new ResponseEntity<List<LoanAddressBean>>(loanAddressBean, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/get-loan-references-details")
	public ResponseEntity<List<LoanReferencesBean>> getloanReferencesDetails(@RequestBody PayloadBean payloadBean) {
		List<LoanReferencesBean> loanReferencesBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			loanReferencesBeanList = inquiryManager.getLoanReferencesDetails(payloadBean);
			return new ResponseEntity<List<LoanReferencesBean>>(loanReferencesBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/get-loan-member-details")
	public ResponseEntity<List<LoanMembersBean>> getLoanMembersDetails(@RequestBody PayloadBean payloadBean) {
		List<LoanMembersBean> loanMemberBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			loanMemberBeanList = inquiryManager.getLoanMembersDetails(payloadBean);
			return new ResponseEntity<List<LoanMembersBean>>(loanMemberBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/add-loan-coborrower")
	public BaseResponse addLoanCoborrower(@RequestBody LoanCoborrowerBean loanCoborrowerBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			loanCoborrowerBean.setIpAddress(request.getRemoteAddr());
			resultString = inquiryManager.addLoanCoborrower(loanCoborrowerBean);
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

	@PostMapping("/get-loan-coborrower")
	public ResponseEntity<List<LoanCoborrowerTo>> getLoanCoborrower(@RequestBody PayloadBean payloadBean) {
		List<LoanCoborrowerTo> loanCoborrowerTo = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			loanCoborrowerTo = inquiryManager.getLoanCoborrower(payloadBean);
			return new ResponseEntity<List<LoanCoborrowerTo>>(loanCoborrowerTo, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-loan-coborrower")
	public BaseResponse updateLoanCoborrower(@RequestBody LoanCoborrowerBean loanCoborrowerBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			loanCoborrowerBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateLoanCoborrower(loanCoborrowerBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/delete-loan-coborrower")
	public BaseResponse deleteLoanCoborrower(@RequestBody LoanCoborrowerBean loanCoborrowerBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			loanCoborrowerBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.deleteLoanCoborrower(loanCoborrowerBean);
			if (resultString.equalsIgnoreCase("Deleted")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "Deleted", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/get-loan-surity-validation-details")
	public ResponseEntity<List<LoanSurityChequesTo>> getLoanSurityChequeValidation(
			@RequestBody PayloadBean payloadBean) {
		List<LoanSurityChequesTo> loanSurityChequesTo = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			loanSurityChequesTo = inquiryManager.getLoanSurityChequeValidation(payloadBean);
			return new ResponseEntity<List<LoanSurityChequesTo>>(loanSurityChequesTo, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/primary-account-exist")
	public BaseResponse primaryAccountExistorNot(@RequestBody PayloadBean payloadBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			String resultString = inquiryManager.primaryAccountExistorNot(payloadBean);
			if (resultString.equalsIgnoreCase("No Primary Account Exist")) {
				baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "No Primary Account Exist",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "No Primary Account Exist" },
								"", Locale.US));
			} else {
				baseResponse = new BaseResponse(HttpStatus.OK, "Primary Account Exist", "");

			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/delete-loan-document")
	public BaseResponse deleteLoanDocument(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
			String resultString = inquiryManager.deleteLoanDocument(deleteRecords);
			if (resultString.equalsIgnoreCase("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/update-customerInInquiry")
	public BaseResponse updateCustomer(@RequestBody CustomerBean customerBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			customerBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateCustomer(customerBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/get-customerDataForMerging")
	public ResponseEntity<List<CustomersTO>> fetchcustomerDataForMerging(@RequestBody CustomerBean customerBean) {
		return new ResponseEntity<List<CustomersTO>>(inquiryManager.fetchcustomerDataForMerging(customerBean),
				HttpStatus.OK);
	}

	@PostMapping("/update-loan-address")
	public BaseResponse updateLoanAddressDetails(@RequestBody LoanAddressBean loanAddressBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			loanAddressBean.setIpAddress(request.getRemoteAddr());
			loanAddressBean.setLastModifiedDate(new Date());
			String resultString = inquiryManager.updateLoanAddressDetails(loanAddressBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else {
				baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
						"Something went wrong! please try again later.");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/update-loan-references")
	public BaseResponse updateLoanReferencesDetails(@RequestBody LoanReferencesBean loanReferencesBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			loanReferencesBean.setIpAddress(request.getRemoteAddr());
			loanReferencesBean.setLastModifiedDate(new Date());
			String resultString = inquiryManager.updateLoanReferencesDetails(loanReferencesBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else {
				baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
						"Something went wrong! please try again later.");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/update-loan-members")
	public BaseResponse updateLoanMembersDetails(@RequestBody LoanMembersBean loanMembersBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			loanMembersBean.setIpAddress(request.getRemoteAddr());
			loanMembersBean.setLastModifiedDate(new Date());
			String resultString = inquiryManager.updateLoanMembersDetails(loanMembersBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else {
				baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
						"Something went wrong! please try again later.");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}
	
	@PostMapping("/update-loan-bank-details")
	public BaseResponse updateLoanBankDetails(@RequestBody LoanBean loanBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			loanBean.setIpAddress(request.getRemoteAddr());
			String resultString = inquiryManager.updateLoanBankDetails(loanBean);
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
