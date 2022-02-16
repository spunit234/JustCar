package com.edios.csr.manager;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.edios.cdf.manager.AbstractManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
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

public interface InquiryManager extends AbstractManager{

	List<CustomersTO> fetchcustomerData(CustomerAddressBean customerAddressBean);
	List<StaffTo> fetchStaffData(StaffBean staffBean);
	String addInquiry(InquiryBean inquiryBean);
	String addCustomer(CustomerBean customerBean);
	String addInquiryVehicle(VehiclesBean inquiryVehiclesBean);
	String sendMail(long id);
	List<InquiryTO> fetchInquiryData(InquiryBean inquiryBean);

	String inquiryNotesEntry(InquiryNotesBean inquiryNotesBean);	
	List<InquiryNotesTO> getInquiryNotes(PayloadBean payloadBean);
	InquiryBean findInquiryById(PayloadBean payloadBean);
	List<CustomersTO> editCustomer(PayloadBean payloadbean);
	List<VehicleTo> editVehicle(PayloadBean payloadbean);
	String updateVehicle(VehiclesBean vehiclesBean);
	String addLoan(LoanBean loanBean);	
	List<LoanTo> editloan(PayloadBean payloadbean);
	String updateLoan(LoanBean loanBean);
	List<VendorDocumentTO> fetchProjectDocumentDetails(Long projectId);
	String uploadProjectDocument(ProjectDocumentBean ProjectDocumentBean, MultipartFile file);
	List<LoanNotesTo> getLoanNotes(PayloadBean payloadBean);
	String loanNotesEntry(LoanNotesBean loanNotesBean);
	List<LoanSurityChequesTo> getLoanSurityCheques(PayloadBean payloadBean);
	List<LoanCrossSaleTo> getLoanCrossSale(PayloadBean payloadBean);
	String saveLoanCrossSale(LoanCrossSaleBean loanCrossSaleBean);
	String saveLoanSurityCheques(LoanSurityChequesBean loanSurityChequesBean);
	String saveLoanTransStatus(LoanTransStatusBean loanTransStatusBean);
	String deleteLoanCrossSell(PayloadBean payloadBean);
	String deleteLoanSurityCheques(PayloadBean payloadBean);
	String updateloanStatus(LoanStatusBean loanstatusbean);
	String wvokupdateloanStatus(LoanStatusBean loanstatusbean);
	List<LoanStatusTo> fetchloanstatus(int vehicleId);
	String delloanStatus(int vid);
	String saveTransactionNo(InquiryBean inquiryBean);
	String saveVehicleTransactionDetail(VehiclesBean vehiclesBean);
	List<InquiryTO> fetchInquiryNo(InquiryBean inquiryBean);

	List<InquiryTO> fetchInquiryLoanData(ManageLoansBean manageLoansBean);
	String uploadInquiryImages(InquiryImagesBean inquiryImagesBean, MultipartFile file);
	List<InquiryDocumentTO> fetchInquiryDocumentDetails(Long projectId);
	String deleteDocument(DeleteRecords deleteRecords);




	List<DealerTo2> fetchDealerName(PayloadBean payloadBean);
	List<DealerExecutiveTo2> fetchDealerExecutiveName(PayloadBean payloadBean);
	String addCustomerAddress(CustomerAddressBean customerAddressBean);
	String updateCustomerAddress(CustomerAddressBean customerAddressBean);
	String updateLoanCrossSell(LoanCrossSaleBean loanCrossSaleBean);
	String saveLoanCommission(LoanCommissionBean loanCommissionBean);
	String saveVehicleInvoice(LoanVehicleInvoiceBean loanVehicleInvoiceBean);
	String deleteLoanCommission(PayloadBean payloadBean);
	String deleteLoanVehicleInvoice(PayloadBean payloadBean);
	List<LoanCommissionTo> getLoanCommission(PayloadBean payloadBean);
	List<LoanVehicleInvoiceTo> getLoanVehicleInvoice(PayloadBean payloadBean);
	String updateLoanSurityCheques(LoanSurityChequesBean loanSurityChequesBean);
	String updateLoanCommission(LoanCommissionBean loanCommissionBean);
	String updateLoanVehicleInvoice(LoanVehicleInvoiceBean loanVehicleInvoiceBean);
	String addLoanAddressDetails(LoanAddressBean loanAddressBean);
	String addLoanMembersDetails(LoanMembersBean loanMembersBean);
	String addLoanReferencesDetails(LoanReferencesBean loanReferencesBean);
	List<LoanAddressBean> getLoanAddressDetails(PayloadBean payloadBean);
	List<LoanReferencesBean> getLoanReferencesDetails(PayloadBean payloadBean);
	List<LoanMembersBean> getLoanMembersDetails(PayloadBean payloadBean);
	String addLoanCoborrower(LoanCoborrowerBean loanCoborrowerBean);
	List<LoanCoborrowerTo> getLoanCoborrower(PayloadBean payloadBean);
	String updateLoanCoborrower(LoanCoborrowerBean loanCoborrowerBean);
	String deleteLoanCoborrower(LoanCoborrowerBean loanCoborrowerBean);
	List<LoanSurityChequesTo> getLoanSurityChequeValidation(PayloadBean payloadBean);
	String primaryAccountExistorNot(PayloadBean payloadBean);
	String deleteLoanDocument(DeleteRecords deleteRecords);
	String updateCustomer(CustomerBean customerBean);
	List<CustomersTO> fetchcustomerDataForMerging(CustomerBean customerBean);
	String updateLoanAddressDetails(LoanAddressBean loanAddressBean);
	String updateLoanReferencesDetails(LoanReferencesBean loanReferencesBean);
	String updateLoanMembersDetails(LoanMembersBean loanMembersBean);
	String updateLoanBankDetails(LoanBean loanBean);

}
