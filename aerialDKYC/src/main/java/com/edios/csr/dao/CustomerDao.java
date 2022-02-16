package com.edios.csr.dao;

import java.util.List;

import com.edios.cdf.entity.AbstractEntity;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.CustomerAddressBean;
import com.edios.csr.bean.CustomerBean;
import com.edios.csr.bean.CustomerIncomeBean;
import com.edios.csr.bean.CustomerLiabilitiesBean;
import com.edios.csr.bean.CustomerMembersBean;
import com.edios.csr.bean.CustomerReferencesBean;
import com.edios.csr.bean.CustomersBankDetailsBean;
import com.edios.csr.entity.CustomerBankDetailEntity;
import com.edios.csr.entity.CustomerEntity;
import com.edios.csr.entity.to.CustomerTO;
import com.edios.csr.entity.to.CustomersBankDetailsTO;
import com.edios.csr.entity.to.ExportDetailsTo;
import com.edios.csr.entity.to.PartnerTo;

public interface CustomerDao {

	List<CustomerTO> fetchcustomerData(CustomerBean customerBean);

	List<CustomersBankDetailsTO> fetchcustomerbankData(Long customerId);

	String addCustomer(CustomerEntity customerEntity);

	boolean addCustomerbankDetails(CustomerBankDetailEntity customerEntity);
	
	List<CustomerTO> searchcustomerData(CustomerTO customerBean);

	CustomerEntity editCustomer(PayloadBean payloadbean);
	
	List<CustomersBankDetailsBean> editCustomerbankDetails(PayloadBean payloadbean);
	
	boolean deleteCustomer(DeleteRecords deleteRecords);
	
	boolean deleteCustomerbankDetails(DeleteRecords deleteRecords);
	
	boolean updateCustomer(CustomerEntity customerEntity);

	
	boolean checkPrimaryAccountStatus(CustomersBankDetailsBean customersBankDetailsBean);
	
//	-------------------*************************___________-----------------


	List<CustomerMembersBean> getCustMembersDetails(Long id);
	
	List<CustomerAddressBean> getCustAddressDetails(Long id);

	List<CustomerReferencesBean> getCustReferencesDetails(Long id);

	List<CustomerIncomeBean> getCustIncomeDetails(Long id);

	List<CustomerLiabilitiesBean> getCustLiabilityDetails(Long id);

	boolean updateOrDeleteCustomersDetails(AbstractEntity customerMembersEntity);

	boolean addCustomersDetails(AbstractEntity customersEntity);

	AbstractEntity fetchCustomersDetailsByID(String entityName, Long customerEntityId);

	boolean isLoanNoExist(String loanNo);

	boolean isLoanNoExistWhileUpdate(String loanNo, Long customerLiabId);

	String isCustomerDuplicateDataExist(String gstNo, String aadharNo, String panNo, String passportNumber);

	String isCustomerDuplicateDataExistWhileUpdate(String gstNo, String aadharNo, String panNo, String passportNumber,
			Long customerId);
	
    List<ExportDetailsTo> fetctRTO1record(String loanNo);
    
    List<ExportDetailsTo> welcome(String loanNo);
	
	List<ExportDetailsTo> fetctRTO2record(String loanNo);
	
	List<ExportDetailsTo> fetctbordResolutionrecord(String loanNo);

	List<ExportDetailsTo> fetctauthorisationDeclarationrecord(String loanNo);
	
	List<PartnerTo> fetctPartnershiprecord(String loanNo);
	
	List<ExportDetailsTo> fetct1a_authorisationDeclarationrecord(String loanNo);
	
	List<ExportDetailsTo> usedCarVehicle(String loanNo);
	
	List<ExportDetailsTo> indemnity(String loanNo);

	


	
	
	
}
