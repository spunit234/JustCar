package com.edios.csr.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.edios.csr.bean.CustomerAddressBean;
import com.edios.csr.bean.CustomerBean;
import com.edios.csr.bean.CustomerIncomeBean;
import com.edios.csr.bean.CustomerLiabilitiesBean;
import com.edios.csr.bean.CustomerMembersBean;
import com.edios.csr.bean.CustomerReferencesBean;
import com.edios.csr.bean.CustomersBankDetailsBean;
import com.edios.csr.entity.to.CustomerTO;
import com.edios.csr.entity.to.CustomersBankDetailsTO;
import com.edios.csr.entity.to.ExportDetailsTo;
import com.edios.csr.entity.to.PartnerTo;
import com.edios.csr.manager.CustomerManager;
import com.ibm.icu.text.DecimalFormat;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
public class CustomerController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	@Qualifier("customerManager")
	CustomerManager customerManager;
	
	@Autowired
	@Qualifier("cmi2")
	CustomerManager customerManager1;

//	------------------------------------------*************************************------------------------------------------

	@PostMapping("/update-customer")
	public BaseResponse updateCustomer(@RequestBody CustomerBean customerBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		if (customerBean.getPermanentAddress() != null) {
			customerBean.setIsPermanentAddressSame(1);
		}
		customerBean.setRecordType('U');

		String resultString = customerManager.updateCustomer(customerBean);
		if (resultString.contains("gstNo")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, resultString,
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "GST No." }, "", Locale.US));
		} else if (resultString.contains("aadharNo")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, resultString,
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Aadhar No." }, "", Locale.US));
		} else if (resultString.contains("panNo")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, resultString,
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Pan No." }, "", Locale.US));
		} else if (resultString.contains("passportNumber")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, resultString,
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Passport No." }, "", Locale.US));
		} else if (resultString.contains("UPDATED")) {
			baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", resultString);
		} else {
			baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}
		return baseResponse;
	}

	@PostMapping("/delete-customer")
	public BaseResponse deleteCustomer(@RequestBody DeleteRecords deleteRecords, HttpServletRequest request) {
		BaseResponse baseResponse = null;

		String resultString = customerManager.deleteCustomer(deleteRecords);
		baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", resultString);
		return baseResponse;
	}

	@PostMapping("/edit-customer")
	public ResponseEntity<CustomerBean> editCustomer(@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<CustomerBean>(customerManager.editCustomer(payloadBean), HttpStatus.OK);
	}

	@PostMapping("/edit-customerbankDetails")
	public ResponseEntity<List<CustomersBankDetailsBean>> editCustomerbankDetails(
			@RequestBody PayloadBean payloadBean) {
		return new ResponseEntity<List<CustomersBankDetailsBean>>(customerManager.editCustomerbankDetails(payloadBean),
				HttpStatus.OK);
	}

	@PostMapping("/add-customer")
	public BaseResponse addCustomer(@RequestBody CustomerBean customerBean, HttpServletRequest request) {
		if (customerBean.getPermanentAddress() != null) {
			customerBean.setIsPermanentAddressSame(1);
		}
		BaseResponse baseResponse = null;
		customerBean.setIpAddress(request.getRemoteAddr());
		String resultString = customerManager.addCustomer(customerBean);
		if (resultString.contains("gstNo")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, resultString,
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "GST No." }, "", Locale.US));
		} else if (resultString.contains("aadharNo")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, resultString,
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Aadhar No." }, "", Locale.US));
		} else if (resultString.contains("panNo")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, resultString,
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Pan No." }, "", Locale.US));
		} else if (resultString.contains("passportNumber")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, resultString,
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Passport No." }, "", Locale.US));
		} else if (Long.parseLong(resultString) > 0) {
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
		} else {
			baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}
		return baseResponse;
	}

	@PostMapping("/search-customer")
	public ResponseEntity<List<CustomerTO>> searchcustomerData(@RequestBody CustomerTO customerBean) {

		return new ResponseEntity<List<CustomerTO>>(customerManager.searchcustomerData(customerBean), HttpStatus.OK);
	}

	@PostMapping("/add-customerbankDetails")
	public BaseResponse addCustomerbankDetails(@RequestBody CustomersBankDetailsBean customerBankDetailsBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			customerBankDetailsBean.setIpAddress(request.getRemoteAddr());

			resultString = customerManager.addCustomerbankDetails(customerBankDetailsBean);

			if (resultString.contains("ADDED"))
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
			else if (resultString.contains("PrimaryAccountExist")) {
				resultString = "Primary account already exists";
				baseResponse = new BaseResponse(HttpStatus.OK, "Primary account already exists", resultString);
			} else
				baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
						"Something went wrong! please try again later.");

			return baseResponse;
		} catch (Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}

	}

	@PostMapping("/get-customerbankDetails")
	public ResponseEntity<List<CustomersBankDetailsTO>> fetchcustomerbankData(@RequestBody PayloadBean payloadBean) {

		List<CustomersBankDetailsTO> customerBankDetailsList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			customerBankDetailsList = customerManager.fetchcustomerbankData(payloadBean.getId());
			return new ResponseEntity<List<CustomersBankDetailsTO>>(customerBankDetailsList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-or-delete-customer-bank-details")
	public BaseResponse updateOrDeleteCustomerBankDetails(
			@RequestBody CustomersBankDetailsBean customersBankDetailsBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			customersBankDetailsBean.setIpAddress(request.getRemoteAddr());
			resultString = customerManager.updateOrDeleteCustomerBankDetails(customersBankDetailsBean);

			if (resultString.equalsIgnoreCase("UPDATED"))
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			else if (resultString.contains("PrimaryAccountExist")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "PrimaryAccountExist",
						"More than one Primary Account can not exist.");

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

	@PostMapping("/add-customer-member-details")
	public BaseResponse addCustomerMemberDetails(@RequestBody CustomerMembersBean customerMemberBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			customerMemberBean.setIpAddress(request.getRemoteAddr());

			resultString = customerManager.addCustomerMemberDetails(customerMemberBean);
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

	@PostMapping("/get-customer-member-details")
	public ResponseEntity<List<CustomerMembersBean>> getCustMembersDetails(@RequestBody PayloadBean payloadBean) {
		List<CustomerMembersBean> customerMemberBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			customerMemberBeanList = customerManager.getCustMembersDetails(payloadBean);
			return new ResponseEntity<List<CustomerMembersBean>>(customerMemberBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-or-delete-customer-member-details")
	public BaseResponse updateOrDeleteCustomerMemberDetails(@RequestBody CustomerMembersBean customerMembersBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			customerMembersBean.setIpAddress(request.getRemoteAddr());
			resultString = customerManager.updateOrDeleteCustomerMemberDetails(customerMembersBean);
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

	@PostMapping("/add-customer-address-details")
	public BaseResponse addCustomerAddressDetails(@RequestBody CustomerAddressBean customerAddressBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;

		try {
			customerAddressBean.setIpAddress(request.getRemoteAddr());

			resultString = customerManager.addCustomerAddressDetails(customerAddressBean);
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

	@PostMapping("/get-customer-address-details")
	public ResponseEntity<List<CustomerAddressBean>> getCustAddressDetails(@RequestBody PayloadBean payloadBean) {
		List<CustomerAddressBean> customerAddressBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			customerAddressBeanList = customerManager.getCustAddressDetails(payloadBean);
			return new ResponseEntity<List<CustomerAddressBean>>(customerAddressBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-or-delete-customer-address-details")
	public BaseResponse updateOrDeleteCustomerAddressDetails(@RequestBody CustomerAddressBean customerAddressBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			customerAddressBean.setIpAddress(request.getRemoteAddr());
			resultString = customerManager.updateOrDeleteCustomerAddressDetails(customerAddressBean);
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

	@PostMapping("/add-customer-references-details")
	public BaseResponse addCustomerReferencesDetails(@RequestBody CustomerReferencesBean customerReferencesBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;
		try {
			customerReferencesBean.setIpAddress(request.getRemoteAddr());

			resultString = customerManager.addCustomerReferencesDetails(customerReferencesBean);
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

	@PostMapping("/get-customer-references-details")
	public ResponseEntity<List<CustomerReferencesBean>> getCustReferencesDetails(@RequestBody PayloadBean payloadBean) {
		List<CustomerReferencesBean> customerReferencesBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			customerReferencesBeanList = customerManager.getCustReferencesDetails(payloadBean);
			return new ResponseEntity<List<CustomerReferencesBean>>(customerReferencesBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-or-delete-customer-references-details")
	public BaseResponse updateOrDeleteCustomerReferencesDetails(
			@RequestBody CustomerReferencesBean customerReferencesBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			customerReferencesBean.setIpAddress(request.getRemoteAddr());
			resultString = customerManager.updateOrDeleteCustomerReferencesDetails(customerReferencesBean);
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

	@PostMapping("/add-customer-income-details")
	public BaseResponse addCustomerIncomeDetails(@RequestBody CustomerIncomeBean customerIncomeBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;
		try {
			customerIncomeBean.setIpAddress(request.getRemoteAddr());

			resultString = customerManager.addCustomerIncomeDetails(customerIncomeBean);
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

	@PostMapping("/get-customer-income-details")
	public ResponseEntity<List<CustomerIncomeBean>> getCustIncomeDetails(@RequestBody PayloadBean payloadBean) {
		List<CustomerIncomeBean> customerIncomeBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			customerIncomeBeanList = customerManager.getCustIncomeDetails(payloadBean);
			return new ResponseEntity<List<CustomerIncomeBean>>(customerIncomeBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-or-delete-customer-income-details")
	public BaseResponse updateOrDeleteCustomerIncomeDetails(@RequestBody CustomerIncomeBean customerIncomeBean,
			HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			customerIncomeBean.setIpAddress(request.getRemoteAddr());
			resultString = customerManager.updateOrDeleteCustomerIncomeDetails(customerIncomeBean);
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

	@PostMapping("/add-customer-liability-details")
	public BaseResponse addCustomerLiabilityDetails(@RequestBody CustomerLiabilitiesBean customerLiabilitiesBean,
			HttpServletRequest request) {

		BaseResponse baseResponse = null;
		String resultString;
		try {
			customerLiabilitiesBean.setIpAddress(request.getRemoteAddr());

			resultString = customerManager.addCustomerLiabilityDetails(customerLiabilitiesBean);
			if (resultString.contains("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", resultString);
			} else if (resultString.equalsIgnoreCase("Duplicate")) {
				baseResponse = new BaseResponse(HttpStatus.CONFLICT, "CONFLICT",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "Loan No." }, "", Locale.US));
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

	@PostMapping("/get-customer-liability-details")
	public ResponseEntity<List<CustomerLiabilitiesBean>> getCustLiabilityDetails(@RequestBody PayloadBean payloadBean) {
		List<CustomerLiabilitiesBean> customerLiabilitiesBeanList = null;

		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			customerLiabilitiesBeanList = customerManager.getCustLiabilityDetails(payloadBean);
			return new ResponseEntity<List<CustomerLiabilitiesBean>>(customerLiabilitiesBeanList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	@PostMapping("/update-or-delete-customer-liability-details")
	public BaseResponse updateOrDeleteCustomerLiabilityDetails(
			@RequestBody CustomerLiabilitiesBean customerLiabilitiesBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		String resultString;
		try {
			customerLiabilitiesBean.setIpAddress(request.getRemoteAddr());
			resultString = customerManager.updateOrDeleteCustomerLiabilityDetails(customerLiabilitiesBean);
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
			} else if (resultString.equalsIgnoreCase("Duplicate")) {
				baseResponse = new BaseResponse(HttpStatus.CONFLICT, "CONFLICT",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "Loan No." }, "", Locale.US));
			}
			return baseResponse;
		} catch (Exception exception) {
			exception.printStackTrace();
			return baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION",
					"Something went wrong! please try again later.");
		}
	}
//	@PostMapping("/RTO-Report2")
//	public void printPartialBoxLabel2(@RequestBody String loanNo, HttpServletRequest httpServletRequest, 
//			HttpServletResponse httpServletResponse) 
//	{
//		try {
////			List<PrintPartialBoxLabelTO> objPrintPartialBoxLabelTO = new ArrayList<PrintPartialBoxLabelTO>();
////			objPrintPartialBoxLabelTO = (List<PrintPartialBoxLabelTO>) printPartialBoxLabelTO;
////			List<PrintPartialBoxLabelTO> objPrintPartialBoxLabelTO = (List<PrintPartialBoxLabelTO>) convertObjectToList(printPartialBoxLabelTO);
//			List<ExportDetailsTo> ar = customerManager.fetctRTO2record(loanNo);
//			//ExportDetailsTo ar1;
////			qrDriverMethod(printPartialBoxLabelTO);
//			for(ExportDetailsTo ar1 : ar) {
//			String cName= ar1.getFirstName();
//			if(ar1.getLastName()!=null) {
//			cName  +=" "+ ar1.getLastName();
//			}
//			ar1.setCustomerName(cName);
//			ar1.setMakeandmodel(ar1.getMakeName()+" "+ar1.getModelName());
//			}
//			List list = Arrays.asList(ar.get(0));
//			System.out.println("printPartialBoxLabelTO=="+ar+"list==="+list);
//			HashMap<String, Object> parametersPDF = new HashMap<String, Object>();
//			JasperPrint jasperPrint = null;
//			JRBeanCollectionDataSource beanCollectionDataSource = null;
//			beanCollectionDataSource = new JRBeanCollectionDataSource(list);
//			String reportPath = httpServletRequest.getServletContext().getRealPath("//WEB-INF//JasperReports//RTOletter2.jasper");
//			jasperPrint = JasperFillManager.fillReport(reportPath, parametersPDF, beanCollectionDataSource);
//			httpServletResponse.addHeader("Content-disposition","attachment; filename=RTOletter2.pdf");
//			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//			}
//	}
	
	
	@PostMapping("/Welcome")
	public void welcome(@RequestBody String loanNo, HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) 
	{
		try {
//			List<PrintPartialBoxLabelTO> objPrintPartialBoxLabelTO = new ArrayList<PrintPartialBoxLabelTO>();
//			objPrintPartialBoxLabelTO = (List<PrintPartialBoxLabelTO>) printPartialBoxLabelTO;
//			List<PrintPartialBoxLabelTO> objPrintPartialBoxLabelTO = (List<PrintPartialBoxLabelTO>) convertObjectToList(printPartialBoxLabelTO);
			List<ExportDetailsTo> ar = customerManager.welcome(loanNo);
			//ExportDetailsTo ar1;
//			qrDriverMethod(printPartialBoxLabelTO);
			for(ExportDetailsTo ar1 : ar) {
			String cName= ar1.getFirstName();
			if(ar1.getLastName()!=null) {
			cName  +=" "+ ar1.getLastName();
			}
			ar1.setCustomerName(cName);
		
			}
			List list = Arrays.asList(ar.get(0));
			System.out.println("printPartialBoxLabelTO=="+ar+"list==="+list);
			HashMap<String, Object> parametersPDF = new HashMap<String, Object>();
			JasperPrint jasperPrint = null;
			JRBeanCollectionDataSource beanCollectionDataSource = null;
			beanCollectionDataSource = new JRBeanCollectionDataSource(list);
			String reportPath = httpServletRequest.getServletContext().getRealPath("//WEB-INF//JasperReports//WelcomeReport.jasper");
			jasperPrint = JasperFillManager.fillReport(reportPath, parametersPDF, beanCollectionDataSource);
			httpServletResponse.addHeader("Content-disposition","attachment; filename=WelcomeReport.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		}
		catch(Exception e) {
			e.printStackTrace();
			}
	}

	@PostMapping("/RTO-Report1")
	public void printPartialBoxLabel(@RequestBody String loanNo, HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) 
	{
		try {
//			List<PrintPartialBoxLabelTO> objPrintPartialBoxLabelTO = new ArrayList<PrintPartialBoxLabelTO>();
//			objPrintPartialBoxLabelTO = (List<PrintPartialBoxLabelTO>) printPartialBoxLabelTO;
//			List<PrintPartialBoxLabelTO> objPrintPartialBoxLabelTO = (List<PrintPartialBoxLabelTO>) convertObjectToList(printPartialBoxLabelTO);
			List<ExportDetailsTo> ar = customerManager.fetctRTO1record(loanNo);
			//ExportDetailsTo ar1;
//			qrDriverMethod(printPartialBoxLabelTO);
			for(ExportDetailsTo ar1 : ar) {
			String cName= ar1.getFirstName();
			if(ar1.getLastName()!=null) {
			cName  +=" "+ ar1.getLastName();
			}
			ar1.setCustomerName(cName);
			ar1.setMakeandmodel(ar1.getMakeName()+" "+ar1.getModelName());
			}
			List list = Arrays.asList(ar.get(0));
			System.out.println("printPartialBoxLabelTO=="+ar+"list==="+list);
			HashMap<String, Object> parametersPDF = new HashMap<String, Object>();
			JasperPrint jasperPrint = null;
			JRBeanCollectionDataSource beanCollectionDataSource = null;
			beanCollectionDataSource = new JRBeanCollectionDataSource(list);
			String reportPath = httpServletRequest.getServletContext().getRealPath("//WEB-INF//JasperReports//RTOletter1.jasper");
			jasperPrint = JasperFillManager.fillReport(reportPath, parametersPDF, beanCollectionDataSource);
			httpServletResponse.addHeader("Content-disposition","attachment; filename=RTOletter1.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		}
		catch(Exception e) {
			e.printStackTrace();
			}
	}
	

	@PostMapping("/RTO-Report2")
	public void printPartialBoxLabel2(@RequestBody String loanNo, HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) 
	{
		try {
//			List<PrintPartialBoxLabelTO> objPrintPartialBoxLabelTO = new ArrayList<PrintPartialBoxLabelTO>();
//			objPrintPartialBoxLabelTO = (List<PrintPartialBoxLabelTO>) printPartialBoxLabelTO;
//			List<PrintPartialBoxLabelTO> objPrintPartialBoxLabelTO = (List<PrintPartialBoxLabelTO>) convertObjectToList(printPartialBoxLabelTO);
			List<ExportDetailsTo> ar = customerManager.fetctRTO2record(loanNo);
			//ExportDetailsTo ar1;
//			qrDriverMethod(printPartialBoxLabelTO);
			for(ExportDetailsTo ar1 : ar) {
			String cName= ar1.getFirstName();
			if(ar1.getLastName()!=null) {
			cName  +=" "+ ar1.getLastName();
			}
			ar1.setCustomerName(cName);
			ar1.setMakeandmodel(ar1.getMakeName()+" "+ar1.getModelName());
			}
			List list = Arrays.asList(ar.get(0));
			System.out.println("printPartialBoxLabelTO=="+ar+"list==="+list);
			HashMap<String, Object> parametersPDF = new HashMap<String, Object>();
			JasperPrint jasperPrint = null;
			JRBeanCollectionDataSource beanCollectionDataSource = null;
			beanCollectionDataSource = new JRBeanCollectionDataSource(list);
			String reportPath = httpServletRequest.getServletContext().getRealPath("//WEB-INF//JasperReports//RTOletter2.jasper");
			jasperPrint = JasperFillManager.fillReport(reportPath, parametersPDF, beanCollectionDataSource);
			httpServletResponse.addHeader("Content-disposition","attachment; filename=RTOletter2.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		}
		catch(Exception e) {
			e.printStackTrace();
			}
	}
	
	@PostMapping("/BOARD_RESOLUTION(For Private Ltd Firm")
	public void printPartialBoxLabel3(@RequestBody String loanNo, HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) 
	{
		try {
//			List<PrintPartialBoxLabelTO> objPrintPartialBoxLabelTO = new ArrayList<PrintPartialBoxLabelTO>();
//			objPrintPartialBoxLabelTO = (List<PrintPartialBoxLabelTO>) printPartialBoxLabelTO;
//			List<PrintPartialBoxLabelTO> objPrintPartialBoxLabelTO = (List<PrintPartialBoxLabelTO>) convertObjectToList(printPartialBoxLabelTO);
			customerManager1.print();
			List<ExportDetailsTo> ar = customerManager.fetctbordResolutionrecord(loanNo);
			
			//ExportDetailsTo ar1;
//			qrDriverMethod(printPartialBoxLabelTO);
			for(ExportDetailsTo ar1 : ar) {
			String cName= ar1.getFirstName();
			if(ar1.getLastName()!=null) {
			cName  +=" "+ ar1.getLastName();
			}
			ar1.setCustomerName(cName);
			ar1.setMakeandmodel(ar1.getMakeName()+" "+ar1.getModelName());
			
			ar1.setAddress(ar1.getAddress() + " " + ar1.getCity());
			//ar1.setAddress(ar1.getAddress());
			if(ar1.getAmount()!=null) {
			Double d1=ar1.getAmount();
			Double d = new Double(d1);
			int number =  d.intValue();
			
			String wordAmount=convert(number);
			
			ar1.setLoanAmountinWords(wordAmount);
			}
			}
			
			
			List list = Arrays.asList(ar.get(0));
			System.out.println("printPartialBoxLabelTO=="+ar+"list==="+list);
			HashMap<String, Object> parametersPDF = new HashMap<String, Object>();
			JasperPrint jasperPrint = null;
			JRBeanCollectionDataSource beanCollectionDataSource = null;
			beanCollectionDataSource = new JRBeanCollectionDataSource(list);
			String reportPath = httpServletRequest.getServletContext().getRealPath("//WEB-INF//JasperReports//LoanScreen2.jasper");
			jasperPrint = JasperFillManager.fillReport(reportPath, parametersPDF, beanCollectionDataSource);
			httpServletResponse.addHeader("Content-disposition","attachment; filename=LoanScreen2.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		}
		catch(Exception e) {
			e.printStackTrace();
			}
	}
	
	
	
	@PostMapping("/1a-Authorisation&Declaration")
	public void printPartialBoxLabel5(@RequestBody String loanNo, HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) 
	{
		try {
		
			List<ExportDetailsTo> ar = customerManager.fetct1a_authorisationDeclarationrecord(loanNo);
			List<PartnerTo> partnershiList = customerManager.fetctPartnershiprecord(loanNo);
			//ExportDetailsTo ar1;
			
			for(ExportDetailsTo ar1 : ar) {
				
				ar1.setPartnerList(partnershiList);
				String cName= ar1.getFirstName();
				if(ar1.getLastName()!=null) {
				cName  +=" "+ ar1.getLastName();
				}
				ar1.setCustomerName(cName);
				ar1.setMakeandmodel(ar1.getMakeName()+" "+ar1.getModelName());
				
				ar1.setAddress(ar1.getAddress() + " " + ar1.getCity());
				
				if(ar1.getAmount()!=null) {
				Double d1=ar1.getAmount();
				Double d = new Double(d1);
				int number =  d.intValue();
				//long l = d.longValue();
				String wordAmount=convert(number);
				
							
				ar1.setLoanAmountinWords(wordAmount);
				}
				}
				

			
			HashMap<String, Object> parametersPDF = new HashMap<String, Object>();
			JasperPrint jasperPrint = null;
			JRBeanCollectionDataSource beanCollectionDataSource = null;
			beanCollectionDataSource = new JRBeanCollectionDataSource(ar);
						
			String reportPath = httpServletRequest.getServletContext().getRealPath("//WEB-INF//JasperReports//Authorisation&Declaration2.jasper");
			jasperPrint = JasperFillManager.fillReport(reportPath, parametersPDF, beanCollectionDataSource);
			httpServletResponse.addHeader("Content-disposition","attachment; filename=Authorisation&Declaration2.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			}
	}
	
	
	
	// for converting number into words
	private static final String[] twodigits = {"", " Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"};  
	//string type array for two digits numbers   
	private static final String[] onedigit = {"", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen"};
	private static String convertUptoThousand(int number)   
	{  
	String soFar;  
	if (number % 100 < 20)  
	{  
	soFar = onedigit[number % 100];  
	number = number/ 100;  
	}  
	else   
	{  
	soFar = onedigit[number % 10];  
	number = number/ 10;  
	soFar = twodigits[number % 10] + soFar;  
	number = number/ 10;  
	}  
	if (number == 0)   
	return soFar;  
	return onedigit[number] + " Hundred " + soFar;  
	}  
	//user-defined method that converts a long number (0 to 999999999) to string    
	public static String convertNumberToWord(long number)   
	{  
	//checks whether the number is zero or not  
	if (number == 0)   
	{   
	//if the given number is zero it returns zero  
	return "zero";   
	}  
	//the toString() method returns a String object that represents the specified long  
	String num = Long.toString(number);  
	//for creating a mask padding with "0"   
	String pattern = "000000000000";  
	//creates a DecimalFormat using the specified pattern and also provides the symbols for the default locale  
	DecimalFormat decimalFormat = new DecimalFormat(pattern);  
	//format a number of the DecimalFormat instance  
	num = decimalFormat.format(number);  
	//format: XXXnnnnnnnnn  
	//the subString() method returns a new string that is a substring of this string  
	//the substring begins at the specified beginIndex and extends to the character at index endIndex - 1  
	//the parseInt() method converts the string into integer  
	int billions = Integer.parseInt(num.substring(0,3));  
	//format: nnnXXXnnnnnn  
	int millions  = Integer.parseInt(num.substring(3,6));  
	//format: nnnnnnXXXnnn  
	int hundredThousands = Integer.parseInt(num.substring(6,9));  
	//format: nnnnnnnnnXXX  
	int thousands = Integer.parseInt(num.substring(9,12));  
	String tradBillions;  
	switch (billions)   
	{  
	case 0:  
	tradBillions = "";  
	break;  
	case 1 :  
	tradBillions = convertUptoThousand(billions)+ " Billion ";  
	break;  
	default :  
	tradBillions = convertUptoThousand(billions)+ " Billion ";  
	}  
	String result =  tradBillions;  
	String tradMillions;  
	switch (millions)   
	{  
	case 0:  
	tradMillions = "";  
	break;  
	case 1 :  
	tradMillions = convertUptoThousand(millions)+ " Million ";  
	break;  
	default :  
	tradMillions = convertUptoThousand(millions)+ " Million ";  
	}  
	result =  result + tradMillions;  
	String tradHundredThousands;  
	switch (hundredThousands)   
	{  
	case 0:  
	tradHundredThousands = "";  
	break;  
	case 1 :  
	tradHundredThousands = "One Thousand ";  
	break;  
	default :  
	tradHundredThousands = convertUptoThousand(hundredThousands)+ " Thousand ";  
	}  
	result =  result + tradHundredThousands;  
	String tradThousand;  
	tradThousand = convertUptoThousand(thousands);  
	result =  result + tradThousand;  
	//removing extra space if any  
	return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");  
	}  
	
	
	@PostMapping("/Authorisation&Declaration")
	public void printPartialBoxLabel4(@RequestBody String loanNo, HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) 
	
	
	{
		List<PartnerTo> cm = null;
		String dir = httpServletRequest.getRealPath("/");
		try {

		
			List<ExportDetailsTo> ar = customerManager.fetctauthorisationDeclarationrecord(loanNo);
			List<PartnerTo> partnershiList = customerManager.fetctPartnershiprecord(loanNo);
		
			
			//ExportDetailsTo ar1;			
			for(ExportDetailsTo ar1 : ar) {
				
			ar1.setPartnerList(partnershiList);
		//	ar1.setAuthorisation1subPath(dir + "/WEB-INF/JasperReports/Authorisation1_sub.jasper");
			String cName= ar1.getFirstName();
			if(ar1.getLastName()!=null) {
			cName  +=" "+ ar1.getLastName();
			}
			ar1.setCustomerName(cName);
			ar1.setMakeandmodel(ar1.getMakeName()+" "+ar1.getModelName());
			
			ar1.setAddress(ar1.getAddress() + " " + ar1.getCity() + " " +ar1.getDistrict() + " " + ar1.getPostalCode());
			
			if(ar1.getAmount()!=null) {
			Double d1=ar1.getAmount();
			Double d = new Double(d1);
			
			int number =  d.intValue();
			//long l = d.longValue();
			String wordAmount=convert(number);
			//String wordAmount = convertUptoThousand(number);
			//String wordAmount = convertNumberToWord(l);
			ar1.setLoanAmountinWords(wordAmount);
			}
			}
						
			//JasperCompileManager.compileReportToFile(dir+"/WEB-INF/JasperReports/Authorisation1_sub.jrxml",dir+ "/WEB-INF/JasperReports/Authorisation1_sub.jasper");
			
		
			HashMap<String, Object> parametersPDF = new HashMap<String, Object>();
			JasperPrint jasperPrint = null;
			JRBeanCollectionDataSource beanCollectionDataSource = null;
			beanCollectionDataSource = new JRBeanCollectionDataSource(ar);
			String reportPath = httpServletRequest.getServletContext()
					.getRealPath("//WEB-INF//JasperReports//Authorisation&Declaration1.jasper");
			jasperPrint = JasperFillManager.fillReport(reportPath, parametersPDF, beanCollectionDataSource);

			httpServletResponse.addHeader("Content-disposition", "attachment; filename=Authorisation&Declaration1" + ".pdf");

			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		
			
		}
		catch(Exception e) {
			e.printStackTrace();
			}
	}
	
	@PostMapping("/UsedVehicel-Loan")
	public void printPartialBoxLabel6(@RequestBody String loanNo, HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) 
	{
		try {
			
			List<ExportDetailsTo> ar = customerManager.usedCarVehicle(loanNo);
			
			for(ExportDetailsTo ar1 : ar) {
			String cName= ar1.getFirstName();
			if(ar1.getLastName()!=null) {
			cName  +=" "+ ar1.getLastName();
			}
			ar1.setCustomerName(cName);
			ar1.setMakeandmodel(ar1.getMakeName()+" "+ar1.getModelName());
			}
			List list = Arrays.asList(ar.get(0));
			System.out.println("printPartialBoxLabelTO=="+ar+"list==="+list);
			HashMap<String, Object> parametersPDF = new HashMap<String, Object>();
			JasperPrint jasperPrint = null;
			JRBeanCollectionDataSource beanCollectionDataSource = null;
			beanCollectionDataSource = new JRBeanCollectionDataSource(list);
			String reportPath = httpServletRequest.getServletContext().getRealPath("//WEB-INF//JasperReports//UsedVehicleLoan.jasper");
			jasperPrint = JasperFillManager.fillReport(reportPath, parametersPDF, beanCollectionDataSource);
			httpServletResponse.addHeader("Content-disposition","attachment; filename=UsedVehicleLoan.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		}
		catch(Exception e) {
			e.printStackTrace();
			}
	}
	
	@PostMapping("/Indemnity")
	public void printPartialBoxLabel7(@RequestBody String loanNo, HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) 
	{
try {
			
			List<ExportDetailsTo> ar = customerManager.indemnity(loanNo);
			
			for(ExportDetailsTo ar1 : ar) {
			String cName= ar1.getFirstName();
			if(ar1.getLastName()!=null) {
			cName  +=" "+ ar1.getLastName();
			}
			ar1.setCustomerName(cName);
			ar1.setMakeandmodel(ar1.getMakeName()+" "+ar1.getModelName());
			
			if(ar1.getInsurence() == null) {
				int i = 0;
				ar1.setInsurencei("1) RS "+i+" to new india insurance company on my behaf from my disbursmnet amount and"); 
				
			}
			if(ar1.getRtoCharges() == null) {
				int j = 0;
				ar1.setRtoChargesi("2) Reimbursement of rto charges "+j+"-in banks favour I hereby further confirm that I have taken the delivery of the vehicle with particulars as detailed above. ");
				
			}
			
			if(ar1.getInsurence()!=null) {
				int i = ar1.getInsurence().intValue();
				ar1.setInsurencei("1) RS "+i+" to new india insurance company on my behaf from my disbursmnet amount and"); 
				
			}
			if(ar1.getRtoCharges()!=null) {
				int j = ar1.getRtoCharges().intValue();
				ar1.setRtoChargesi("2)  Reimbursement of rto charges "+j+"-in banks favour I hereby further confirm that I have taken the delivery of the vehicle with particulars as detailed above. ");
				
			}
			
			}
			List list = Arrays.asList(ar.get(0));
			System.out.println("printPartialBoxLabelTO=="+ar+"list==="+list);
			HashMap<String, Object> parametersPDF = new HashMap<String, Object>();
			JasperPrint jasperPrint = null;
			JRBeanCollectionDataSource beanCollectionDataSource = null;
			beanCollectionDataSource = new JRBeanCollectionDataSource(list);
			String reportPath = httpServletRequest.getServletContext().getRealPath("//WEB-INF//JasperReports//Indemnity.jasper");
			jasperPrint = JasperFillManager.fillReport(reportPath, parametersPDF, beanCollectionDataSource);
			httpServletResponse.addHeader("Content-disposition","attachment; filename=Indemnity.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		}
		catch(Exception e) {
			e.printStackTrace();
			}
}
	
	public static final String[] units = { "", "One", "Two", "Three", "Four",
			"Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
			"Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
			"Eighteen", "Nineteen" };

	public static final String[] tens = { 
			"", 		// 0
			"",		// 1
			"Twenty", 	// 2
			"Thirty", 	// 3
			"Forty", 	// 4
			"Fifty", 	// 5
			"Sixty", 	// 6
			"Seventy",	// 7
			"Eighty", 	// 8
			"Ninety" 	// 9
	};

	public static String convert(final int n) {
		if (n < 0) {
			return "Minus " + convert(-n);
		}

		if (n < 20) {
			return units[n];
		}

		if (n < 100) {
			return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
		}

		if (n < 1000) {
			return units[n / 100] + " Hundred" + ((n % 100 != 0) ? " " : "") + convert(n % 100);
		}

		if (n < 100000) {
			return convert(n / 1000) + " Thousand" + ((n % 10000 != 0) ? " " : "") + convert(n % 1000);
		}

		if (n < 10000000) {
			return convert(n / 100000) + " Lakh" + ((n % 100000 != 0) ? " " : "") + convert(n % 100000);
		}

		return convert(n / 10000000) + " Crore" + ((n % 10000000 != 0) ? " " : "") + convert(n % 10000000);
	}
}
