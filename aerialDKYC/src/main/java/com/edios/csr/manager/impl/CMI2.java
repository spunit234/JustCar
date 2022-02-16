package com.edios.csr.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
import com.edios.csr.manager.CustomerManager;

@Service("cmi2")
public class CMI2 implements CustomerManager{

	@Override
	public List<CustomerTO> fetchcustomerData(CustomerBean customerBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomersBankDetailsTO> fetchcustomerbankData(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCustomer(CustomerBean customerBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCustomerbankDetails(CustomersBankDetailsBean customerBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerTO> searchcustomerData(CustomerTO customerBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerBean editCustomer(PayloadBean payloadbean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomersBankDetailsBean> editCustomerbankDetails(PayloadBean payloadbean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateCustomer(CustomerBean customerBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteCustomer(DeleteRecords deleteRecords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteCustomerbankDetails(DeleteRecords deleteRecords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkPrimaryAccountStatus(CustomersBankDetailsBean customersBankDetailsBean) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String updateOrDeleteCustomerBankDetails(CustomersBankDetailsBean customersBankDetailsBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCustomerMemberDetails(CustomerMembersBean customerMemberBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerMembersBean> getCustMembersDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateOrDeleteCustomerMemberDetails(CustomerMembersBean customerMembersBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCustomerReferencesDetails(CustomerReferencesBean customerReferencesBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerReferencesBean> getCustReferencesDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateOrDeleteCustomerReferencesDetails(CustomerReferencesBean customerMembersBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCustomerIncomeDetails(CustomerIncomeBean customerIncomeBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerIncomeBean> getCustIncomeDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateOrDeleteCustomerIncomeDetails(CustomerIncomeBean customerIncomeBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCustomerLiabilityDetails(CustomerLiabilitiesBean customerLiabilitiesBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerLiabilitiesBean> getCustLiabilityDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateOrDeleteCustomerLiabilityDetails(CustomerLiabilitiesBean customerLiabilitiesBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCustomerAddressDetails(CustomerAddressBean customerAddressBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerAddressBean> getCustAddressDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateOrDeleteCustomerAddressDetails(CustomerAddressBean customerAddressBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExportDetailsTo> fetctRTO1record(String loanNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExportDetailsTo> fetctRTO2record(String loanNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExportDetailsTo> fetctbordResolutionrecord(String loanNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExportDetailsTo> fetctauthorisationDeclarationrecord(String loanNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PartnerTo> fetctPartnershiprecord(String loanNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExportDetailsTo> fetct1a_authorisationDeclarationrecord(String loanNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExportDetailsTo> usedCarVehicle(String loanNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExportDetailsTo> indemnity(String loanNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println("printed in CM2");
		
	}

}
