package com.edios.csr.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.manager.impl.AbstractManagerImpl;
import com.edios.cdf.util.AppConstants;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.CustomerAddressBean;
import com.edios.csr.bean.CustomerBean;
import com.edios.csr.bean.CustomerIncomeBean;
import com.edios.csr.bean.CustomerLiabilitiesBean;
import com.edios.csr.bean.CustomerMembersBean;
import com.edios.csr.bean.CustomerReferencesBean;
import com.edios.csr.bean.CustomersBankDetailsBean;
import com.edios.csr.dao.CustomerDao;
import com.edios.csr.entity.CustomerAddressEntity;
import com.edios.csr.entity.CustomerBankDetailEntity;
import com.edios.csr.entity.CustomerEntity;
import com.edios.csr.entity.CustomerIncomeEntity;
import com.edios.csr.entity.CustomerLiabilitiesEntity;
import com.edios.csr.entity.CustomerMembersEntity;
import com.edios.csr.entity.CustomerReferencesEntity;
import com.edios.csr.entity.to.CustomerTO;
import com.edios.csr.entity.to.CustomersBankDetailsTO;
import com.edios.csr.entity.to.ExportDetailsTo;
import com.edios.csr.entity.to.PartnerTo;
import com.edios.csr.manager.CustomerManager;

@Service("customerManager")
public class CustomerManagerImpl extends AbstractManagerImpl<CustomerBean, CustomerEntity> implements CustomerManager {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	MessageSource messageSource;

	@Override
	@Transactional("db1Tx")
	public List<CustomerTO> fetchcustomerData(CustomerBean customerBean) {

		return customerDao.fetchcustomerData(customerBean);
	}

	@Override
	@Transactional("db1Tx")
	public List<CustomersBankDetailsTO> fetchcustomerbankData(Long customerId) {

		return customerDao.fetchcustomerbankData(customerId);
	}

	@Override
	@Transactional("db1Tx")
	public String addCustomer(CustomerBean customerBean) {
		String resultString = "";
		if (!customerBean.isDuplicateFlag()) {
			String duplicateFieldName = customerDao.isCustomerDuplicateDataExist(customerBean.getGstNo(),
					customerBean.getAadharNo(), customerBean.getPanNo(), customerBean.getPassportNumber());
			if (duplicateFieldName.equals("")) {

			} else {
				return duplicateFieldName;
			}
		}
		setAuditInfo(customerBean, "newFlag");
		try {
			CustomerEntity customerEntity = mapper.map(customerBean, CustomerEntity.class);
			resultString = customerDao.addCustomer(customerEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String updateCustomer(CustomerBean customerBean) {
		if (!customerBean.isDuplicateFlag()) {
			String duplicateFieldName = customerDao.isCustomerDuplicateDataExistWhileUpdate(customerBean.getGstNo(),
					customerBean.getAadharNo(), customerBean.getPanNo(), customerBean.getPassportNumber(),
					customerBean.getCustomerId());
			if (duplicateFieldName.equals("")) {

			} else {
				return duplicateFieldName;
			}
		}
		String resultString = "";
		boolean resultFlag = false;
		customerBean.setLastModifiedDate(new Date());

		CustomerEntity customerEntity = mapper.map(customerBean, CustomerEntity.class);
		resultFlag = customerDao.updateCustomer(customerEntity);
		if (resultFlag) {
			return "UPDATED";
		}

		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public boolean checkPrimaryAccountStatus(CustomersBankDetailsBean customersBankDetailsBean) {
		return customerDao.checkPrimaryAccountStatus(customersBankDetailsBean);
	}

	@Override
	@Transactional("db1Tx")
	public List<CustomerTO> searchcustomerData(CustomerTO customerBean) {
		return customerDao.searchcustomerData(customerBean);
	}

	private void setAuditInfo(CustomerBean customerBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			customerBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			customerBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			customerBean.setCreatedDate(new Date());
		} else {
			customerBean.setTransactionCount(customerBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			customerBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			customerBean.setLastModifiedDate(new Date());
		}
	}

	private void setAuditInfo1(CustomersBankDetailsBean customerBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			customerBean.setTransactionCount(1);
			customerBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			customerBean.setCreatedDate(new Date());
		} else {
			customerBean.setTransactionCount(customerBean.getTransactionCount() + 1);
			customerBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			customerBean.setLastModifiedDate(new Date());
		}
	}

	@Override
	@Transactional("db1Tx")
	public CustomerBean editCustomer(PayloadBean payloadbean) {
		CustomerEntity customerEntity = customerDao.editCustomer(payloadbean);
		CustomerBean customerBean = mapper.map(customerEntity, CustomerBean.class);
		return customerBean;
	}

	@Override
	@Transactional("db1Tx")
	public List<CustomersBankDetailsBean> editCustomerbankDetails(PayloadBean payloadbean) {
		return customerDao.editCustomerbankDetails(payloadbean);
	}

	@Override
	@Transactional("db1Tx")
	public String deleteCustomer(DeleteRecords deleteRecords) {
		String resultflag = null;
		boolean flag = customerDao.deleteCustomer(deleteRecords);
		if (flag) {

			return resultflag = "DELETED";
		}
		return resultflag;

	}

	@Override
	@Transactional("db1Tx")
	public String deleteCustomerbankDetails(DeleteRecords deleteRecords) {
		String resultflag = null;
		boolean flag = customerDao.deleteCustomerbankDetails(deleteRecords);
		if (flag) {

			return resultflag = "DELETED";
		}
		return resultflag;

	}

//	-----------------------------------------_******************************************___________-------------------------

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteCustomerBankDetails(CustomersBankDetailsBean customersBankDetailsBean) {

		String resultString = "DELETED";

		CustomerBankDetailEntity latestData = (CustomerBankDetailEntity) customerDao
				.fetchCustomersDetailsByID("CustomerBankDetailEntity", customersBankDetailsBean.getCustomerBankId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (customersBankDetailsBean.getTransactionCount()))
			return "TransactionFailed";

		if (customersBankDetailsBean.getRecordType() != 'D') {

			if (!(customersBankDetailsBean.getPrimaryAccount().equals("No"))) {
				if (!(customerDao.checkPrimaryAccountStatus(customersBankDetailsBean)))
					return "PrimaryAccountExist";
			}

			latestData.setBankListId(customersBankDetailsBean.getBankListId());
			latestData.setIfscCode(customersBankDetailsBean.getIfscCode());
			latestData.setAccountNo(customersBankDetailsBean.getAccountNo());
			latestData.setAccountType(customersBankDetailsBean.getAccountType());
			latestData.setPrimaryAccount(customersBankDetailsBean.getPrimaryAccount());
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		latestData.setTransactionCount(customersBankDetailsBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(customersBankDetailsBean.getLastModifiedBy());

		boolean resultFlag = customerDao.updateOrDeleteCustomersDetails(latestData);

		if (resultFlag) {
			return resultString;
		}
		return null;
	}

	@Override
	@Transactional("db1Tx")
	public String addCustomerbankDetails(CustomersBankDetailsBean customerBankDetailsBean) {
		boolean resultFlag = false;

		if (!(customerBankDetailsBean.getPrimaryAccount().equals("No"))) {
			if (!(customerDao.checkPrimaryAccountStatus(customerBankDetailsBean)))
				return "PrimaryAccountExist";
		}

		setAuditInfo1(customerBankDetailsBean, "newFlag");
		CustomerBankDetailEntity customerEntity = mapper.map(customerBankDetailsBean, CustomerBankDetailEntity.class);
		resultFlag = customerDao.addCustomerbankDetails(customerEntity);

		if (resultFlag)
			return "ADDED";
		else
			return "";
	}

	@Override
	@Transactional("db1Tx")
	public String addCustomerMemberDetails(CustomerMembersBean customerMemberBean) {
		boolean resultFlag = false;

		customerMemberBean.setTransactionCount(1);
		customerMemberBean.setCreatedDate(new Date());
		customerMemberBean.setRecordType('I');

		CustomerMembersEntity customerMembersEntity = mapper.map(customerMemberBean, CustomerMembersEntity.class);
		resultFlag = customerDao.addCustomersDetails(customerMembersEntity);
		if (resultFlag) {
			return "ADDED";
		} else
			return "";
	}

	@Override
	@Transactional("db1Tx")
	public List<CustomerMembersBean> getCustMembersDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return customerDao.getCustMembersDetails(payloadBean.getId());
	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteCustomerMemberDetails(CustomerMembersBean customerMembersBean) {

		String resultString = "DELETED";

		CustomerMembersEntity latestData = (CustomerMembersEntity) customerDao
				.fetchCustomersDetailsByID("CustomerMembersEntity", customerMembersBean.getCustomerMemberId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (customerMembersBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(customerMembersBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(customerMembersBean.getLastModifiedBy());

		if (customerMembersBean.getRecordType() != 'D') {
			latestData.setMembershipDate(customerMembersBean.getMembershipDate());
			latestData.setMemberName(customerMembersBean.getMemberName());
			latestData.setMemberStatus(customerMembersBean.getMemberStatus());
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		boolean resultFlag = customerDao.updateOrDeleteCustomersDetails(latestData);

		if (resultFlag) {
			return resultString;
		}
		return null;
	}

	@Override
	@Transactional("db1Tx")
	public String addCustomerAddressDetails(CustomerAddressBean customerAddressBean) {
		boolean resultFlag = false;

		customerAddressBean.setTransactionCount(1L);
		customerAddressBean.setCreatedDate(new Date());
		customerAddressBean.setRecordType('I');

		CustomerAddressEntity customerAddressEntity = mapper.map(customerAddressBean, CustomerAddressEntity.class);
		resultFlag = customerDao.addCustomersDetails(customerAddressEntity);
		if (resultFlag) {
			return "ADDED";
		} else
			return "";
	}

	@Override
	@Transactional("db1Tx")
	public List<CustomerAddressBean> getCustAddressDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return customerDao.getCustAddressDetails(payloadBean.getId());
	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteCustomerAddressDetails(CustomerAddressBean customerAddressBean) {

		String resultString = "DELETED";

		CustomerAddressEntity latestData = (CustomerAddressEntity) customerDao
				.fetchCustomersDetailsByID("CustomerAddressEntity", customerAddressBean.getCustomerAddId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (customerAddressBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(customerAddressBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(customerAddressBean.getLastModifiedBy());

		if (customerAddressBean.getRecordType() == null || customerAddressBean.getRecordType() != 'D') {

			latestData.setAddressType(customerAddressBean.getAddressType());
			latestData.setHouseType(customerAddressBean.getHouseType());
			latestData.setContactNumber(customerAddressBean.getContactNumber());
			latestData.setAltContactNumber(customerAddressBean.getAltContactNumber());
			latestData.setEmailAddress(customerAddressBean.getEmailAddress());
			latestData.setAddress(customerAddressBean.getAddress());
			latestData.setTehsil(customerAddressBean.getTehsil());
			latestData.setCity(customerAddressBean.getCity());
			latestData.setPostalCode(customerAddressBean.getPostalCode());
			latestData.setDistrict(customerAddressBean.getDistrict());
			latestData.setStateId(customerAddressBean.getStateId());

			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		boolean resultFlag = customerDao.updateOrDeleteCustomersDetails(latestData);

		if (resultFlag) {
			return resultString;
		}
		return null;
	}

	@Override
	@Transactional("db1Tx")
	public String addCustomerReferencesDetails(CustomerReferencesBean customerReferencesBean) {
		boolean resultFlag = false;

		customerReferencesBean.setTransactionCount(1);
		customerReferencesBean.setCreatedDate(new Date());
		customerReferencesBean.setRecordType('I');

		CustomerReferencesEntity customerReferencesEntity = mapper.map(customerReferencesBean,
				CustomerReferencesEntity.class);
		resultFlag = customerDao.addCustomersDetails(customerReferencesEntity);
		if (resultFlag) {
			return "ADDED";
		} else
			return null;
	}

	@Override
	@Transactional("db1Tx")
	public List<CustomerReferencesBean> getCustReferencesDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return customerDao.getCustReferencesDetails(payloadBean.getId());
	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteCustomerReferencesDetails(CustomerReferencesBean customerReferencesBean) {

		String resultString = "DELETED";

		CustomerReferencesEntity latestData = (CustomerReferencesEntity) customerDao
				.fetchCustomersDetailsByID("CustomerReferencesEntity", customerReferencesBean.getCustomerRefId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (customerReferencesBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(customerReferencesBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(customerReferencesBean.getLastModifiedBy());

		if (customerReferencesBean.getRecordType() != 'D') {

			latestData.setReferenceName(customerReferencesBean.getReferenceName());
			latestData.setContactNo(customerReferencesBean.getContactNo());
			latestData.setAltContactNo(customerReferencesBean.getAltContactNo());
			latestData.setEmail(customerReferencesBean.getEmail());
			latestData.setAddress(customerReferencesBean.getAddress());
			latestData.setTehsil(customerReferencesBean.getTehsil());
			latestData.setCity(customerReferencesBean.getCity());
			latestData.setDistrict(customerReferencesBean.getDistrict());
			latestData.setPostalCode(customerReferencesBean.getPostalCode());
			latestData.setStateId(customerReferencesBean.getStateId());
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		boolean resultFlag = customerDao.updateOrDeleteCustomersDetails(latestData);

		if (resultFlag) {
			return resultString;
		}
		return null;
	}

	@Override
	@Transactional("db1Tx")
	public String addCustomerIncomeDetails(CustomerIncomeBean customerIncomeBean) {
		boolean resultFlag = false;

		customerIncomeBean.setTransactionCount(1);
		customerIncomeBean.setCreatedDate(new Date());
		customerIncomeBean.setRecordType('I');

		CustomerIncomeEntity customerIncomeEntity = mapper.map(customerIncomeBean, CustomerIncomeEntity.class);
		resultFlag = customerDao.addCustomersDetails(customerIncomeEntity);
		if (resultFlag) {
			return "ADDED";
		} else
			return null;
	}

	@Override
	@Transactional("db1Tx")
	public List<CustomerIncomeBean> getCustIncomeDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return customerDao.getCustIncomeDetails(payloadBean.getId());
	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteCustomerIncomeDetails(CustomerIncomeBean customerIncomeBean) {

		String resultString = "DELETED";

		CustomerIncomeEntity latestData = (CustomerIncomeEntity) customerDao
				.fetchCustomersDetailsByID("CustomerIncomeEntity", customerIncomeBean.getCustomerIncId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (customerIncomeBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(customerIncomeBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(customerIncomeBean.getLastModifiedBy());

		if (customerIncomeBean.getRecordType() != 'D') {

			latestData.setFinancialYear(customerIncomeBean.getFinancialYear());
			latestData.setIncome(customerIncomeBean.getIncome());
			latestData.setItrFillingDate(customerIncomeBean.getItrFillingDate());
			latestData.setItrAckNo(customerIncomeBean.getItrAckNo());
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		boolean resultFlag = customerDao.updateOrDeleteCustomersDetails(latestData);

		if (resultFlag) {
			return resultString;
		}
		return null;
	}

	@Override
	@Transactional("db1Tx")
	public String addCustomerLiabilityDetails(CustomerLiabilitiesBean customerLiabilitiesBean) {
		boolean resultFlag = false;
		resultFlag = customerDao.isLoanNoExist(customerLiabilitiesBean.getLoanNo());
		if (resultFlag) {
			return "Duplicate";
		}
		customerLiabilitiesBean.setTransactionCount(1);
		customerLiabilitiesBean.setCreatedDate(new Date());
		customerLiabilitiesBean.setRecordType('I');

		CustomerLiabilitiesEntity customerLiabilitiesEntity = mapper.map(customerLiabilitiesBean,
				CustomerLiabilitiesEntity.class);
		resultFlag = customerDao.addCustomersDetails(customerLiabilitiesEntity);
		if (resultFlag) {
			return "ADDED";
		} else
			return null;
	}

	@Override
	@Transactional("db1Tx")
	public List<CustomerLiabilitiesBean> getCustLiabilityDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return customerDao.getCustLiabilityDetails(payloadBean.getId());
	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteCustomerLiabilityDetails(CustomerLiabilitiesBean customerLiabilitiesBean) {

		String resultString = "DELETED";
		boolean resultFlag1 = false;
		CustomerLiabilitiesEntity latestData = (CustomerLiabilitiesEntity) customerDao
				.fetchCustomersDetailsByID("CustomerLiabilitiesEntity", customerLiabilitiesBean.getCustomerLiabId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (customerLiabilitiesBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(customerLiabilitiesBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(customerLiabilitiesBean.getLastModifiedBy());

		if (customerLiabilitiesBean.getRecordType() != 'D') {
			resultFlag1 = customerDao.isLoanNoExistWhileUpdate(customerLiabilitiesBean.getLoanNo(),
					customerLiabilitiesBean.getCustomerLiabId());
			if (resultFlag1) {
				return "Duplicate";
			}

			latestData.setLoanAmount(customerLiabilitiesBean.getLoanAmount());
			latestData.setBankNameId(customerLiabilitiesBean.getBankNameId());
			latestData.setStartDate(customerLiabilitiesBean.getStartDate());
			latestData.setEndDate(customerLiabilitiesBean.getEndDate());
			latestData.setLoanNo(customerLiabilitiesBean.getLoanNo());
			latestData.setForeclosureAmount(customerLiabilitiesBean.getForeclosureAmount());
			latestData.setRecordType('U');
			resultString = "UPDATED";

		} else
			latestData.setRecordType('D');

		boolean resultFlag = customerDao.updateOrDeleteCustomersDetails(latestData);

		if (resultFlag) {
			return resultString;
		}
		return null;
	}
	
	@Override
	@Transactional("db1Tx")
	public List<ExportDetailsTo> fetctRTO1record(String loanNo) {
		// TODO Auto-generated method stub
		return customerDao.fetctRTO1record(loanNo);
	}
	
	@Override
	@Transactional("db1Tx")
	public List<ExportDetailsTo> welcome(String loanNo) {
		// TODO Auto-generated method stub
		return customerDao.welcome(loanNo);
	}
	@Override
	@Transactional("db1Tx")
	public List<ExportDetailsTo> fetctRTO2record(String loanNo) {
		// TODO Auto-generated method stub
		return customerDao.fetctRTO2record(loanNo);
	}
	
	@Override
	@Transactional("db1Tx")
	public List<ExportDetailsTo> fetctbordResolutionrecord(String loanNo) {
		// TODO Auto-generated method stub
		return customerDao.fetctbordResolutionrecord(loanNo);
	}
	public void print() {
		System.out.println("Worked");
	}
	
	@Override
	@Transactional("db1Tx")
	public List<ExportDetailsTo> fetctauthorisationDeclarationrecord(String loanNo) {
		// TODO Auto-generated method stub
		return customerDao.fetctauthorisationDeclarationrecord(loanNo);
	}
	@Override
	@Transactional("db1Tx")
	public List<PartnerTo> fetctPartnershiprecord(String loanNo) {
		// TODO Auto-generated method stub
		return customerDao.fetctPartnershiprecord(loanNo);
	}
	@Override
	@Transactional("db1Tx")
	public List<ExportDetailsTo> fetct1a_authorisationDeclarationrecord(String loanNo) {
		// TODO Auto-generated method stub
		return customerDao.fetct1a_authorisationDeclarationrecord(loanNo);
	}
	@Override
	@Transactional("db1Tx")
	public List<ExportDetailsTo> usedCarVehicle(String loanNo) {
		// TODO Auto-generated method stub
		return customerDao.usedCarVehicle(loanNo);
	}
	
	@Override
	@Transactional("db1Tx")
	public List<ExportDetailsTo> indemnity(String loanNo) {
		
		return customerDao.indemnity(loanNo);
	}
}
