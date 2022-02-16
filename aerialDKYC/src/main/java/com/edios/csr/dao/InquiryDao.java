package com.edios.csr.dao;

import java.util.List;

import com.edios.cdf.entity.AbstractEntity;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.util.TransactionData;
import com.edios.csr.bean.CustomerAddressBean;
import com.edios.csr.bean.CustomerBean;
import com.edios.csr.bean.InquiryBean;
import com.edios.csr.bean.LoanAddressBean;
import com.edios.csr.bean.LoanBean;
import com.edios.csr.bean.LoanCoborrowerBean;
import com.edios.csr.bean.LoanCommissionBean;
import com.edios.csr.bean.LoanCrossSaleBean;
import com.edios.csr.bean.LoanMembersBean;
import com.edios.csr.bean.LoanReferencesBean;
import com.edios.csr.bean.LoanSurityChequesBean;
import com.edios.csr.bean.LoanVehicleInvoiceBean;
import com.edios.csr.bean.ManageLoansBean;
import com.edios.csr.bean.StaffBean;
import com.edios.csr.bean.VehiclesBean;
import com.edios.csr.entity.CustomerAddressEntity;
import com.edios.csr.entity.CustomerEntity;
import com.edios.csr.entity.InquiriesEntity;
import com.edios.csr.entity.InquiriesNotesEntity;
import com.edios.csr.entity.InquiryImagesEntity;
import com.edios.csr.entity.LoanAddressEntity;
import com.edios.csr.entity.LoanCommissionEntity;
import com.edios.csr.entity.LoanCrossSaleEntity;
import com.edios.csr.entity.LoanEntity;
import com.edios.csr.entity.LoanMembersEntity;
import com.edios.csr.entity.LoanNotesEntity;
import com.edios.csr.entity.LoanReferencesEntity;
import com.edios.csr.entity.LoanStatusEntity;
import com.edios.csr.entity.LoanSurityChequesEntity;
import com.edios.csr.entity.LoanTransStatusEntity;
import com.edios.csr.entity.LoanVehicleInvoiceEntity;
import com.edios.csr.entity.ProjectDocumentEntity;
import com.edios.csr.entity.VehicelsEntity;
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
import com.edios.csr.entity.to.MailDataTO;
import com.edios.csr.entity.to.StaffTo;
import com.edios.csr.entity.to.VehicleTo;
import com.edios.csr.entity.to.VendorDocumentTO;

public interface InquiryDao {

	List<CustomersTO> fetchcustomerData(CustomerAddressBean customerAddressBean);

	List<StaffTo> fetchStaffData(StaffBean staffBean);

	String addInquiry(InquiriesEntity inquiriesEntity);

	String addCustomer(CustomerEntity customerEntity);

	boolean addInquiryVehicle(VehicelsEntity inquiriesVehicelsEntity);

	List<MailDataTO> getMailData(Long id);

	List<InquiryTO> fetchInquiryData(InquiryBean inquiryBean);

	boolean inquiryNotesEntry(InquiriesNotesEntity inquiriesNotesEntity);
	
	List<InquiryNotesTO> getInquiryNotes(PayloadBean payloadBean);

	InquiriesEntity findInquiryById(PayloadBean payloadbean);

	List<CustomersTO> editCustomer(PayloadBean payloadbean);

	List<VehicleTo> editVehicle(PayloadBean payloadbean);

	TransactionData fetchTransactionDataById(Long employeeID);

	boolean updateVehicle(VehicelsEntity vehicelsEntity);
	String addLoan(LoanEntity loanEntity);
	List<LoanTo> editloan(PayloadBean payloadbean);

	boolean updateLoan(LoanEntity loanEntity);

	TransactionData fetchTransactionDataById1(Long loanId);

	List<VendorDocumentTO> fetchProjectDocumentDetails(Long projectId);

	Long uploadProjectDocument(ProjectDocumentEntity ProjectDocumentEntity);

	boolean updateProjectDocumentEntity(Long pkId, Long storageId, String fileName, Integer lastModifiedBy);

	boolean loanNotesEntry(LoanNotesEntity loanNotesEntity);

	List<LoanNotesTo> getLoanNotes(PayloadBean payloadBean);

	boolean saveLoanCrossSale(LoanCrossSaleEntity loanCrossSaleEntity);

	boolean saveLoanSurityCheques(LoanSurityChequesEntity loanSurityChequesEntity);

	List<LoanCrossSaleTo> getLoanCrossSale(PayloadBean payloadBean);

	List<LoanSurityChequesTo> getLoanSurityCheques(PayloadBean payloadBean);

	boolean saveLoanTransStatus(LoanTransStatusEntity loanTransStatusEntity);

	boolean deleteLoanCrossSell(PayloadBean payloadBean);

	TransactionData fetchTransactionDataById2(Long Id);

	TransactionData fetchTransactionDataById3(Long Id);

	boolean deleteLoanSurityCheques(PayloadBean payloadBean);

	boolean wvokupdateloanStatus(LoanStatusEntity loanstatusbean);

	boolean delloanStatus(int vid);

	List<LoanStatusTo> fetchloanstatus(int vehicleId);

	boolean updateloanStatus(LoanStatusEntity loanstatusbean);

	boolean saveTransactionNo(InquiryBean inquiryBean);

	boolean saveVehicleTransactionDetail(VehiclesBean vehiclesBean);


	List<InquiryTO> fetchInquiryNo(InquiryBean inquiryBean);


	List<InquiryTO> fetchInquiryLoanData(ManageLoansBean manageLoansBean);

	boolean updateInquiryImages(Long pkId, Long storageId, String fileName, Long long1);

	Long uploadInquiryImages(InquiryImagesEntity inquiryImagesEntity);

	List<InquiryDocumentTO> fetchInquiryDocumentDetails(Long projectId);

	boolean deleteDocument(Long deleteRecordId, Long lastModifiedBy);

	boolean isLoanNoExist(String loanNo);





	List<DealerTo2> fetchDealerName(PayloadBean payloadBean);

	List<DealerExecutiveTo2> fetchDealerExecutiveName(PayloadBean payloadBean);

	String addCustomerAddress(CustomerAddressEntity customerAddressEntity);

	boolean updateCustomerAddress(CustomerAddressBean customerAddressBean);

	boolean updateLoanCrossSell(LoanCrossSaleBean loanCrossSaleBean);

	boolean saveLoanCommission(LoanCommissionEntity loanCommissionEntity);

	boolean saveVehicleInvoice(LoanVehicleInvoiceEntity loanVehicleInvoiceEntity);

	boolean deleteLoanCommission(PayloadBean payloadBean);

	boolean deleteLoanVehicleInvoice(PayloadBean payloadBean);

	List<LoanCommissionTo> getLoanCommission(PayloadBean payloadBean);

	List<LoanVehicleInvoiceTo> getLoanVehicleInvoice(PayloadBean payloadBean);

	boolean updateLoanSurityCheques(LoanSurityChequesBean loanSurityChequesBean);

	boolean updateLoanCommission(LoanCommissionBean loanCommissionBean);

	boolean updateLoanVehicleInvoice(LoanVehicleInvoiceBean loanVehicleInvoiceBean);

	boolean addCustomersDetails(AbstractEntity customersEntity);

	List<LoanAddressBean> getLoanAddressDetails(Long loanId,Long customerId);

	List<LoanReferencesBean> getLoanReferencesDetails(Long id,Long customerId);

	List<LoanMembersBean> getLoanMembersDetails(Long custId,Long customerId);

	List<LoanCoborrowerTo> getLoanCoborrower(Long Id);

	boolean updateLoanCoborrower(LoanCoborrowerBean loanCoborrowerBean);

	boolean deleteLoanCoborrower(LoanCoborrowerBean loanCoborrowerBean);

	List<LoanSurityChequesTo> getLoanSurityChequeValidation(PayloadBean payloadBean);

	boolean primaryAccountExistorNot(Long id);

	boolean deleteLoanDocument(Long deleteRecordId, Long lastModifiedBy);

	boolean updateCustomer(CustomerBean customerBean);

	List<CustomersTO> fetchcustomerDataForMerging(CustomerBean customerBean);

	boolean updateLoanAddressDetails(LoanAddressEntity loanAddressEntity);

	boolean updateLoanReferencesDetails(LoanReferencesEntity loanReferencesEntity);

	boolean updateLoanMembersDetails(LoanMembersEntity loanMembersEntity);

	boolean updateLoanBankDetails(LoanBean loanBean);
}
