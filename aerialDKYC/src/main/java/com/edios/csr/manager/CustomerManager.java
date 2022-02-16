package com.edios.csr.manager;

import java.util.List;

import com.edios.cdf.manager.AbstractManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
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

public interface CustomerManager extends AbstractManager {

	List<CustomerTO> fetchcustomerData(CustomerBean customerBean);

	List<CustomersBankDetailsTO> fetchcustomerbankData(Long id);

	String addCustomer(CustomerBean customerBean);

	String addCustomerbankDetails(CustomersBankDetailsBean customerBean);

	List<CustomerTO> searchcustomerData(CustomerTO customerBean);

	CustomerBean editCustomer(PayloadBean payloadbean);

	List<CustomersBankDetailsBean> editCustomerbankDetails(PayloadBean payloadbean);

	String updateCustomer(CustomerBean customerBean);

	String deleteCustomer(DeleteRecords deleteRecords);

	String deleteCustomerbankDetails(DeleteRecords deleteRecords);

	boolean checkPrimaryAccountStatus(CustomersBankDetailsBean customersBankDetailsBean);

	String updateOrDeleteCustomerBankDetails(CustomersBankDetailsBean customersBankDetailsBean);

	String addCustomerMemberDetails(CustomerMembersBean customerMemberBean);

	List<CustomerMembersBean> getCustMembersDetails(PayloadBean payloadBean);

	String updateOrDeleteCustomerMemberDetails(CustomerMembersBean customerMembersBean);

	String addCustomerReferencesDetails(CustomerReferencesBean customerReferencesBean);

	List<CustomerReferencesBean> getCustReferencesDetails(PayloadBean payloadBean);

	String updateOrDeleteCustomerReferencesDetails(CustomerReferencesBean customerMembersBean);

	String addCustomerIncomeDetails(CustomerIncomeBean customerIncomeBean);

	List<CustomerIncomeBean> getCustIncomeDetails(PayloadBean payloadBean);

	String updateOrDeleteCustomerIncomeDetails(CustomerIncomeBean customerIncomeBean);

	String addCustomerLiabilityDetails(CustomerLiabilitiesBean customerLiabilitiesBean);

	List<CustomerLiabilitiesBean> getCustLiabilityDetails(PayloadBean payloadBean);

	String updateOrDeleteCustomerLiabilityDetails(CustomerLiabilitiesBean customerLiabilitiesBean);

	String addCustomerAddressDetails(CustomerAddressBean customerAddressBean);

	List<CustomerAddressBean> getCustAddressDetails(PayloadBean payloadBean);

	String updateOrDeleteCustomerAddressDetails(CustomerAddressBean customerAddressBean);
	
	
   List<ExportDetailsTo> fetctRTO1record(String loanNo);
   List<ExportDetailsTo> welcome(String loanNo);
	
	List<ExportDetailsTo> fetctRTO2record(String loanNo);
	
	List<ExportDetailsTo> fetctbordResolutionrecord(String loanNo);
	
	List<ExportDetailsTo> fetctauthorisationDeclarationrecord(String loanNo);
	
	List<PartnerTo> fetctPartnershiprecord(String loanNo);
	
	List<ExportDetailsTo> fetct1a_authorisationDeclarationrecord(String loanNo);
	
	List<ExportDetailsTo> usedCarVehicle(String loanNo);
	
	List<ExportDetailsTo> indemnity(String loanNo);
	void print();



}
