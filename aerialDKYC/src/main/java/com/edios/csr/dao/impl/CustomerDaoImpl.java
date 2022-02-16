package com.edios.csr.dao.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.impl.BaseDaoImpl;
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

@Repository
@SuppressWarnings({ "unchecked", "deprecation" })
public class CustomerDaoImpl extends BaseDaoImpl<CustomerEntity> implements CustomerDao {

	@Autowired
	MessageSource messageSource;

	@Override
	public List<CustomerTO> fetchcustomerData(CustomerBean customerBean) {
		List<CustomerTO> customerTO = null;

		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);

		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();

		sqlQuery = "Select c.customerId as customerId,  c.firstName as firstName,"
				+ " c.middleName as middleName, c.lastName as lastName,  c.ignoreFlag as ignoreFlag,"
				+ " c.contactNumber as contactNumber, c.emailAddress as emailAddress, c.panNo as panNo,c.aadharNo as aadharNo,c.motherName as motherName"
				+ " ,c.fatherName as fatherName ,DATE_FORMAT (c.dateOfBirth ,'" + dateTimeFormat
				+ "') as dateOfBirth, c.gender as gender, c.husbandName as husbandName, c.altContactNumber as altContactNumber"
				+ ", c.customerType as customerType, c.status as status , c.passportNumber as passportNumber, c.cibilScore as cibilScore, c.presentAddress as presentAddress"
				+ ", c.presentCity as presentCity , c.presentPinCode as presentPinCode, c.presentStateListId as  presentStateListId,c.presentCounteryListId as presentCounteryListId"
				+ ", c.permanentAddress as permanentAddress , c.permanentCity as permanentCity, c.permanenetStateListId as permanenetStateListId, c.permanenetCountryListId as permanenetCountryListId"
				+ ", c.permanentPinCode as permanentPinCode , c.officeAddress as officeAddress , c.officeCity as officeCity, c.officeStateListId as officeStateListId"
				+ ", c.officeCountryListId as officeCountryListId, c.officePinCode as officePinCode , c.typeOfBusinessId as typeOfBusinessId, c.typeOfCompanyId as typeOfCompanyId"
				+ ", c.professionId as professionId , c.employmentId as employmentId  from CustomerEntity c "
				+ " where "
				+ " c.recordType<>'D' order by c.customerId desc ";
		customerTO = (List<CustomerTO>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(CustomerTO.class)).list();

		return customerTO;
	}

	@Override
	public boolean checkPrimaryAccountStatus(CustomersBankDetailsBean customersBankDetailsBean) {
		List<CustomersBankDetailsTO> customerTO = null;
		String sqlQuery = "";
		StringBuilder whereClause = new StringBuilder();

		if (customersBankDetailsBean.getCustomerBankId() != null)
			whereClause.append(" AND  c.customerBankId != ").append(customersBankDetailsBean.getCustomerBankId());

		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "select c.primaryAccount as primaryAccount from CustomerBankDetailEntity c where c.recordType<> 'D' and c.customerId=:customerId"
				+ " AND  c.primaryAccount ='Yes' " + whereClause.toString();

		customerTO = (List<CustomersBankDetailsTO>) session.createQuery(sqlQuery)
				.setParameter("customerId", customersBankDetailsBean.getCustomerId())
				.setResultTransformer(Transformers.aliasToBean(CustomersBankDetailsTO.class)).list();

		if (customerTO.isEmpty())
			return true;
		else
			return false;
	}

//	@Override
//	public List<StaffTo> fetchStaffData(StaffBean staffBean) {
//		List<StaffTo> staffTO = null;
//
//		String sqlQuery = "";
//		Session session = (Session) entityManager.getDelegate();
//
//		sqlQuery = "Select s.staffId as staffId, concat(s.firstName,' ', s.lastName,' (',s.staffCode,') (',s.designation,')' ) as fullName "
//				+ " from StaffEntity s " + " where s.accountId=" + staffBean.getAccountId() + " AND s.siteId="
//				+ staffBean.getSiteId() + " AND " + " s.recordType<>'D'  ";
//		staffTO = (List<StaffTo>) session.createQuery(sqlQuery)
//				.setResultTransformer(Transformers.aliasToBean(StaffTo.class)).list();
//
//		return staffTO;
//	}
//
//	@Override
//	public String addInquiry(InquiriesEntity inquiriesEntity) {
//		try {
//			entityManager.persist(inquiriesEntity);
//			String format = LocalDate.now() + "";
//			inquiriesEntity.setInquiryNo("VHL" + format.replace("-", "") + "00" + inquiriesEntity.getInquiryId() + "");
//			return inquiriesEntity.getInquiryId() + "";
//
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			return "";
//		}
//
//	}
//	
	@Override
	public boolean updateCustomer(CustomerEntity customerEntity) {
		boolean result = true;
		try {
			if (Optional.ofNullable(customerEntity.getAadharNo()).isPresent()) {
			customerEntity.setAadharNo(customerEntity.getAadharNo().replaceAll("-", ""));
			}
			// entityManager.merge(siteEntity);
			Session session = (Session) entityManager.getDelegate();
			session.update(customerEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

//	@Override
//	public boolean updateCustomerbankDetails(CustomerBankDetailEntity customerEntity) {
//		boolean result = true;
//		try {
//			//entityManager.merge(siteEntity);
//			Session session = (Session) entityManager.getDelegate();
//			session.update(customerEntity);
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			return false;
//		}
//		return result;
//	}
//
	@Override
	public String addCustomer(CustomerEntity customerEntity) {
		try {
			if (Optional.ofNullable(customerEntity.getAadharNo()).isPresent()) {
				customerEntity.setAadharNo(customerEntity.getAadharNo().replaceAll("-", ""));
				}
			entityManager.persist(customerEntity);

			return customerEntity.getCustomerId() + "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}

//	@Override
//	public String addMember(MemberEntity customerEntity) {
//		try {
//			entityManager.persist(customerEntity);
//
//			return customerEntity.getCustomerId() + "";
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			return "";
//		}
//		
//	}
//	
	@Override
	public boolean addCustomerbankDetails(CustomerBankDetailEntity customerEntity) {
		Session session = (Session) entityManager.getDelegate();

		Long id = (Long) session.save(customerEntity);
		if (id > 0)
			return true;
		return false;
	}

//	@Override
//	public boolean addInquiryVehicle(VehicelsEntity inquiriesVehicelsEntity) {
//		boolean result = true;
//		try {
//			entityManager.persist(inquiriesVehicelsEntity);
//			return result;
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			return false;
//		}
//
//	}
	@Override
	public List<CustomerTO> searchcustomerData(CustomerTO customerBean) {
		List<CustomerTO> customerto = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (Optional.ofNullable(customerBean.getAadharNo()).isPresent()) {
			customerBean.setAadharNo(customerBean.getAadharNo().replaceAll("-", ""));
			}
		try {
			StringBuilder whereClause = new StringBuilder();
			if (customerBean.getStatus() != null && !(customerBean.getStatus().equals("All")))
				whereClause.append(" AND c.status='").append(customerBean.getStatus()).append("'");
			if (customerBean.getFirstName() != null && !(customerBean.getFirstName().equals(""))) {
				whereClause.append(" AND (c.firstName like '%").append(customerBean.getFirstName()).append("%'");
				if (customerBean.getLastName() != null && !(customerBean.getLastName().equals("")))
					whereClause.append(" AND c.lastName like '%").append(customerBean.getLastName()).append("%')");
				else
					whereClause.append(" OR c.lastName like '%").append(customerBean.getFirstName()).append("%')");
			}
			if (customerBean.getContactNumber() != null && !(customerBean.getContactNumber().equals("")))
				whereClause.append(" AND cae.contactNumber='").append(customerBean.getContactNumber()).append("'");
			if (customerBean.getCustomerType() != null)
				whereClause.append(" AND c.customerType='").append(customerBean.getCustomerType()).append("'");
			if (customerBean.getPanNo() != null && !(customerBean.getPanNo().equals("")))
				whereClause.append(" AND c.panNo='").append(customerBean.getPanNo()).append("'");
			if (customerBean.getAadharNo() != null && !(customerBean.getAadharNo().equals("")))
				whereClause.append(" AND c.aadharNo='").append(customerBean.getAadharNo()).append("'");
			if (customerBean.getGender() != null)
				whereClause.append(" AND c.gender='").append(customerBean.getGender()).append("'");
			if (customerBean.getOccupation() != null)
				whereClause.append(" AND c.occupation='").append(customerBean.getOccupation()).append("'");
			if (customerBean.getFromDateOfBirth() != null)
				whereClause.append(" AND c.dateOfBirth >='")
						.append(simpleDateFormat.format(customerBean.getFromDateOfBirth())).append(" 00:00:00'");
			if (customerBean.getToDateOfBirth() != null)
				whereClause.append(" AND c.dateOfBirth <= '")
						.append(simpleDateFormat.format(customerBean.getToDateOfBirth())).append(" 00:00:00'");

			Session session = (Session) entityManager.getDelegate();
			String sqlQuery = "select c.customerId as customerId, concat(c.firstName , ' ',  case when c.lastName is not null then c.lastName else '' end) as customerName,"
					+ " cae.contactNumber as contactNumber,"
					+ " c.customerType as customerType , c.occupation as occupation," + " c.status as status "
					+ " from CustomerEntity c left join CustomerAddressEntity cae "
					+ " on (cae.customerId = c.customerId and cae.recordType<>'D' )  " + " where c.recordType<>'D' and "
					+ " c.siteId=:siteId " + whereClause.toString() + " group by c.customerId ";

			customerto = (List<CustomerTO>) session.createQuery(sqlQuery)
					.setParameter("siteId", customerBean.getSiteId())
					.setResultTransformer(Transformers.aliasToBean(CustomerTO.class)).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(customerto.size());
		return customerto;

	}

	@Override
	public CustomerEntity editCustomer(PayloadBean payloadbean) {
//		Session session = (Session) entityManager.getDelegate();
		CustomerEntity employeeEntity = entityManager.find(CustomerEntity.class, payloadbean.getId());
		return employeeEntity;
	}

	@Override
	public List<CustomersBankDetailsBean> editCustomerbankDetails(PayloadBean payloadbean) {
		List<CustomersBankDetailsBean> customerTO = null;

		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select c.customerBankId as customerBankId,  c.bankListId as bankListId,"
				+ " c.ifscCode as ifscCode, c.accountType as accountType, c.accountNo as accountNo , c.primaryAccount as primaryAccount"

				+ " from CustomerBankDetailEntity c " + " where c.customerBankId =" + payloadbean.getId() + " AND "
				+ " c.recordType<>'D'  ";
		customerTO = (List<CustomersBankDetailsBean>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(CustomersBankDetailsBean.class)).list();
		return customerTO;
	}

	@Override
	public boolean deleteCustomer(DeleteRecords deleteRecords) {
		boolean result = true;
		String sqlQuery = "";
		try {

			sqlQuery = "update CustomerEntity c set record_type ='D' where customerId = " + deleteRecords.getId() + "";
			Session session = (Session) entityManager.getDelegate();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery(sqlQuery);
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return result = false;
		}
		return result;

	}

	@Override
	public boolean deleteCustomerbankDetails(DeleteRecords deleteRecords) {
		boolean result = true;
		String sqlQuery = "";
		try {

			sqlQuery = "update CustomerBankDetailEntity c set record_type ='D' where customerBankId = "
					+ deleteRecords.getId() + "";
			Session session = (Session) entityManager.getDelegate();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery(sqlQuery);
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return result = false;
		}
		return result;

	}

//	

//	*************************************************-----------------------------------***********************************

	@Override
	public List<CustomersBankDetailsTO> fetchcustomerbankData(Long customerId) {
		List<CustomersBankDetailsTO> customerTO = null;
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();

		sqlQuery = "select c.customerBankId as customerBankId, c.bankListId as bankListId, apl.parameterListValue as bankName,c.primaryAccount as primaryAccount ,c.accountNo as accountNo, c.accountType as accountType,"
				+ " c.ifscCode as ifscCode , c.transactionCount as transactionCount "
				+ " from CustomerBankDetailEntity c left join ApplicationParameterListEntity apl on "
				+ " apl.parameterListID=c.bankListId where c.recordType<>'D' and " + " c.customerId =" + customerId;
		customerTO = (List<CustomersBankDetailsTO>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(CustomersBankDetailsTO.class)).list();

		return customerTO;
	}

	@Override
	public boolean addCustomersDetails(AbstractEntity customersEntity) {
		Session session = (Session) entityManager.getDelegate();

		Long id = (Long) session.save(customersEntity);
		if (id > 0)
			return true;
		return false;
	}

	@Override
	public List<CustomerMembersBean> getCustMembersDetails(Long custId) {
		Session session = (Session) entityManager.getDelegate();
		List<CustomerMembersBean> customerMembersBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select cmde.membershipDate as membersShipDate, cmde.memberName as memberName,"
					+ " cmde.customerMemberId as customerMemberId "
					+ " ,cmde.memberStatus as memberStatus, cmde.transactionCount as transactionCount "
					+ " from CustomerMembersEntity cmde "
					+ " where cmde.customerId.customerId = :id and cmde.recordType<>'D' ";

			customerMembersBeanList = session.createQuery(sqlQuery).setParameter("id", custId)
					.setResultTransformer(Transformers.aliasToBean(CustomerMembersBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerMembersBeanList;
	}

	@Override
	public List<CustomerAddressBean> getCustAddressDetails(Long custId) {
		Session session = (Session) entityManager.getDelegate();
		List<CustomerAddressBean> customerAddressBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select  cae.customerAddId as customerAddId, cae.addressType as addressType,"
					+ " cae.houseType as houseType, cae.contactNumber as contactNumber, cae.city as city,"
					+ " cae.stateId as stateId,cae.altContactNumber as altContactNumber,cae.emailAddress as emailAddress,"
					+ " cae.address as address, cae.tehsil as tehsil, cae.postalCode as postalCode,cae.employerName as employerName ,"
					+ " cae.district as district,cae.transactionCount as transactionCount,apl.parameterListValue as stateName "
					+ " from CustomerAddressEntity cae left join ApplicationParameterListEntity apl "
					+ " on (apl.parameterListID = cae.stateId) "
					+ " where cae.customerId.customerId = :id and cae.recordType<>'D' ";

			customerAddressBeanList = session.createQuery(sqlQuery).setParameter("id", custId)
					.setResultTransformer(Transformers.aliasToBean(CustomerAddressBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerAddressBeanList;
	}

	@Override
	public boolean updateOrDeleteCustomersDetails(AbstractEntity customerAnyTypeEntity) {
		Session session = (Session) entityManager.getDelegate();
		session.update(customerAnyTypeEntity);
		return true;
	}

	@Override
	public AbstractEntity fetchCustomersDetailsByID(String entityName, Long customerEntityId) {
		AbstractEntity transactionData = null;
		try {
			Session session = (Session) entityManager.getDelegate();

			if (entityName.equals("CustomerBankDetailEntity")) {
				transactionData = session.get(CustomerBankDetailEntity.class, customerEntityId);
				;
			} else if (entityName.equals("CustomerAddressEntity")) {
				transactionData = (CustomerAddressEntity) session.get(CustomerAddressEntity.class, customerEntityId);
				;
			} else if (entityName.equals("CustomerMembersEntity")) {
				transactionData = session.get(CustomerMembersEntity.class, customerEntityId);
				;
			} else if (entityName.equals("CustomerReferencesEntity")) {
				transactionData = (CustomerReferencesEntity) session.get(CustomerReferencesEntity.class,
						customerEntityId);
				;
			} else if (entityName.equals("CustomerIncomeEntity")) {
				transactionData = (CustomerIncomeEntity) session.get(CustomerIncomeEntity.class, customerEntityId);
				;
			} else {
				transactionData = (CustomerLiabilitiesEntity) session.get(CustomerLiabilitiesEntity.class,
						customerEntityId);
				;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

	@Override
	public List<CustomerReferencesBean> getCustReferencesDetails(Long id) {
		Session session = (Session) entityManager.getDelegate();
		List<CustomerReferencesBean> customerReferencesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select cfde.customerRefId as customerRefId, cfde.referenceName as referenceName, "
					+ " cfde.contactNo as contactNo, cfde.altContactNo as altContactNo, cfde.email as email, "
					+ " cfde.address as address,cfde.tehsil as tehsil , cfde.city as city, cfde.postalCode as postalCode, "
					+ " cfde.district as district, cfde.stateId as stateId   "
					+ " , cfde.transactionCount as transactionCount, apl.parameterListValue as stateName "
					+ " from CustomerReferencesEntity cfde left join ApplicationParameterListEntity apl "
					+ " on (apl.parameterListID = cfde.stateId)"
					+ " where cfde.customerId.customerId = :id and cfde.recordType<>'D' ";

			customerReferencesBeanList = session.createQuery(sqlQuery).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CustomerReferencesBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerReferencesBeanList;
	}

	@Override
	public List<CustomerIncomeBean> getCustIncomeDetails(Long id) {
		Session session = (Session) entityManager.getDelegate();
		List<CustomerIncomeBean> customerIncomeBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select cie.customerIncId as customerIncId,cie.financialYear as financialYear, cie.income as income,"
					+ " cie.itrFillingDate as incItrFillingDate,cie.itrAckNo as itrAckNo, "
					+ " cie.transactionCount as transactionCount  from CustomerIncomeEntity cie "
					+ " where cie.customerId.customerId = :id and cie.recordType<>'D' order by cie.customerIncId";

			customerIncomeBeanList = session.createQuery(sqlQuery).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CustomerIncomeBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerIncomeBeanList;
	}

	@Override
	public List<CustomerLiabilitiesBean> getCustLiabilityDetails(Long id) {
		Session session = (Session) entityManager.getDelegate();
		List<CustomerLiabilitiesBean> customerLiabilitiesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select cle.customerLiabId as customerLiabId, cle.loanNo as loanNo, cle.startDate as liabStartDate,"
					+ " cle.endDate as liabEndDate, cle.loanAmount as loanAmount, cle.foreclosureAmount as foreclosureAmount,"
					+ "  apl.parameterListValue as bankName"
					+ " ,cle.bankNameId as bankNameId, cle.transactionCount as transactionCount "
					+ " from CustomerLiabilitiesEntity cle left join ApplicationParameterListEntity apl "
					+ " on (apl.parameterListID = cle.bankNameId)  "
					+ " where cle.customerId.customerId = :id and cle.recordType<>'D' ";

			customerLiabilitiesBeanList = session.createQuery(sqlQuery).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CustomerLiabilitiesBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerLiabilitiesBeanList;
	}

	@Override
	public boolean isLoanNoExist(String loanNo) {
		try {
			return entityManager.createQuery("select table.loanNo as loanNo from CustomerLiabilitiesEntity table where "
					+ " table.loanNo='" + loanNo + "' and table.recordType<>'D'").getSingleResult() == null ? false
							: true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@Override
	public boolean isLoanNoExistWhileUpdate(String loanNo, Long customerLiabId) {
		try {
			return entityManager.createQuery(
					"select table.loanNo as loanNo from CustomerLiabilitiesEntity table where " + " table.loanNo='"
							+ loanNo + "' and table.customerLiabId!=" + customerLiabId + " and table.recordType<>'D'")
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@Override
	public String isCustomerDuplicateDataExist(String gstNo, String aadharNo, String panNo, String passportNumber) {
		try {
			boolean fieldFlag = false;
			String allFieldData = "";
			if (aadharNo != null && !aadharNo.equals("")) {
				fieldFlag = entityManager
						.createQuery(
								"select table.transactionCount as transactionCount from CustomerEntity table where "
										+ " table.aadharNo='" + aadharNo + "' and table.recordType<>'D'")
						.getResultList().size() == 0  ? false : true;
				if (fieldFlag) {
					fieldFlag = false;
					allFieldData += "aadharNo";
				}
			}
			if (panNo != null && !panNo.equals("")) {
				fieldFlag = entityManager
						.createQuery(
								"select table.transactionCount as transactionCount from CustomerEntity table where "
										+ " table.panNo='" + panNo + "' and table.recordType<>'D'")
						.getResultList().size() == 0 ? false : true;
				if (fieldFlag) {
					fieldFlag = false;
					allFieldData += "panNo";
				}
			}
			
			return allFieldData;
		} catch (NoResultException ex) {
			return "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}

	@Override
	public String isCustomerDuplicateDataExistWhileUpdate(String gstNo, String aadharNo, String panNo,
			String passportNumber, Long customerId) {
		try {
			boolean fieldFlag = false;
			if (Optional.ofNullable(aadharNo).isPresent()) {
				aadharNo=aadharNo.replaceAll("-", "");
				}
			String allFieldData = "";
			if (aadharNo != null && !aadharNo.equals("")) {
				fieldFlag = entityManager.createQuery(
						"select table.transactionCount as transactionCount from CustomerEntity table where "
								+ " table.aadharNo='" + aadharNo + "' and table.customerId!=" + customerId
								+ " and table.recordType<>'D'")
						.getResultList().size() == 0 ? false : true;
				if (fieldFlag) {
					fieldFlag = false;
					allFieldData += "aadharNo";
				}
			}
			if (panNo != null && !panNo.equals("")) {
				fieldFlag = entityManager.createQuery(
						"select table.transactionCount as transactionCount from CustomerEntity table where "
								+ " table.panNo='" + panNo + "' and  table.customerId!=" + customerId
								+ " and table.recordType<>'D'")
						.getResultList().size() ==0  ? false : true;
				if (fieldFlag) {
					fieldFlag = false;
					allFieldData += "panNo";
				}
			}
			return allFieldData;
		} catch (NoResultException ex) {
			return "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}
	
//	@Override
//	public List<ExportDetailsTo> fetctRTO2record(String loanNo) {
//		Session session = (Session) entityManager.getDelegate();
//		List<ExportDetailsTo> customerLiabilitiesBeanList = null;
//		String sqlQuery = "";
//		try {
//			sqlQuery = " SELECT (SELECT Inquiry_no FROM inquiries i WHERE inquiry_id IN(SELECT inquiry_id FROM loans l3 WHERE l3.loan_no = l.loan_no ) ) AS refNo, l.loan_Amount as amount,l.bank_location as location  ,l.LOAN_NO as loanNo ,cl.FIRST_NAME as firstName , cl.last_name as lastName, (SELECT apl.PARAMETER_LIST_VALUE FROM  application_parameter_lists apl WHERE apl.PARAMETER_LIST_ID = l.loan_bank_id) AS bankName,( SELECT vm.MODEL_NAME from vehicle_models vm WHERE vm.MODEL_ID in"
//					+ " (SELECT Model_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  )as modelName , "
//					+ "(SELECT vmk.make_name FROM vehicle_makes vmk WHERE vmk.make_id in (SELECT Make_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  ) makeName , "
//					+ " (SELECT vehicle_reg_No FROM vehicles WHERE vehicle_id IN (SELECT vehicle_id FROM loans l2 WHERE l2.loan_no = l.loan_no )) as registrationNo"
//					+ " , 'DefaultVC' as vendorCode,'DefaultBM' as bankManager,  'DefaultCustomer' as customerName, 'DefaultMake' as makeandmodel,'DefaultAC' as accountCode"
//					+ " ,(SELECT vehicle_Manuf_Date FROM vehicles WHERE vehicle_id IN (SELECT vehicle_id FROM loans l2 WHERE l2.loan_no = l.loan_no ))  as manufacturingYear  FROM loans l LEFT JOIN customerslatest cl  ON l.CUSTOMER_ID = cl.CUSTOMER_ID WHERE l.loan_No ='" + loanNo + "'";
//			//customerLiabilitiesBeanList =	session.createNativeQuery(sqlQuery).list();
//			customerLiabilitiesBeanList =	(List<ExportDetailsTo>)session.createNativeQuery(sqlQuery)
//					.addScalar("loanNo").addScalar("bankName").addScalar("customerName").addScalar("makeandmodel")
//					.addScalar("firstName" ).addScalar("lastName").addScalar("makeName").addScalar("modelName").addScalar("registrationNo").addScalar("bankManager")
//					.addScalar("refNo").addScalar("vendorCode").addScalar("accountCode").addScalar("location").addScalar("manufacturingYear" , new DateType()).addScalar("amount")
//					
//					.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).list();
//			
//			
//			
//			
//			
//			//customerLiabilitiesBeanList= qry.getResultList();
////			System.out.println(customerLiabilitiesBeanList.toString());
////			 for(ExportDetailsTo ex : customerLiabilitiesBeanList) {
////				 System.out.println(ex.getFirstName());
////			 }
//
//			//customerLiabilitiesBeanList = session.createQuery(sqlQuery).setParameter("id", loanNo)
//					
//					//.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).getResultList();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return customerLiabilitiesBeanList;
//	}
	@Override
	public List<ExportDetailsTo> fetctRTO1record(String loanNo) {
		Session session = (Session) entityManager.getDelegate();
		List<ExportDetailsTo> customerLiabilitiesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " SELECT l.LOAN_NO as loanNo ,cl.FIRST_NAME as firstName , cl.last_name as lastName, (SELECT apl.PARAMETER_LIST_VALUE FROM  application_parameter_lists apl WHERE apl.PARAMETER_LIST_ID = l.loan_bank_id) AS bankName,( SELECT vm.MODEL_NAME from vehicle_models vm WHERE vm.MODEL_ID in"
					+ " (SELECT Model_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  )as modelName , "
					+ "(SELECT vmk.make_name FROM vehicle_makes vmk WHERE vmk.make_id in (SELECT Make_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  ) makeName , "
					+ " (SELECT vehicle_reg_No FROM vehicles WHERE vehicle_id IN (SELECT vehicle_id FROM loans l2 WHERE l2.loan_no = l.loan_no ))"
					+ "  as registrationNo,  'DefaultCustomer' as customerName, 'DefaultMake' as makeandmodel ,(SELECT AT.aggregator_task_charges FROM aggregator_tasks AT WHERE AT.aggregator_id = agg.AGGREGATOR_ID LIMIT 1) rtoCharges "
					+ " FROM loans l LEFT JOIN customerslatest cl  ON l.CUSTOMER_ID = cl.CUSTOMER_ID  LEFT JOIN inquiries i ON cl.CUSTOMER_ID = i.CUSTOMER_ID LEFT JOIN aggregators agg ON i.INQUIRY_ID = agg.INQUIRY_ID WHERE l.loan_No ='" + loanNo + "' AND i.INQUIRY_ID = l.INQUIRY_ID";
			//customerLiabilitiesBeanList =	session.createNativeQuery(sqlQuery).list();
			customerLiabilitiesBeanList =	(List<ExportDetailsTo>)session.createNativeQuery(sqlQuery)
					.addScalar("loanNo").addScalar("bankName").addScalar("customerName").addScalar("makeandmodel")
					.addScalar("firstName" ).addScalar("lastName").addScalar("makeName").addScalar("modelName").addScalar("registrationNo")
					.addScalar("rtoCharges" , new IntegerType())
					.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).list();
			
			
			
			
			
			//customerLiabilitiesBeanList= qry.getResultList();
//			System.out.println(customerLiabilitiesBeanList.toString());
//			 for(ExportDetailsTo ex : customerLiabilitiesBeanList) {
//				 System.out.println(ex.getFirstName());
//			 }

			//customerLiabilitiesBeanList = session.createQuery(sqlQuery).setParameter("id", loanNo)
					
					//.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerLiabilitiesBeanList;
	}
	@Override
	public List<ExportDetailsTo> fetctRTO2record(String loanNo) {
		Session session = (Session) entityManager.getDelegate();
		
		List<ExportDetailsTo> customerLiabilitiesBeanList = null;
		String sqlQuery = "";
		try {
			//(SELECT Inquiry_no FROM inquiries i WHERE inquiry_id IN(SELECT inquiry_id FROM loans l3 WHERE l3.loan_no = l.loan_no ) ) AS refNo
			sqlQuery = " SELECT l.loan_Amount as amount,l.bank_location as location  ,l.LOAN_NO as loanNo ,cl.FIRST_NAME as firstName , cl.last_name as lastName, (SELECT apl.PARAMETER_LIST_VALUE FROM  application_parameter_lists apl WHERE apl.PARAMETER_LIST_ID = l.loan_bank_id) AS bankName,( SELECT vm.MODEL_NAME from vehicle_models vm WHERE vm.MODEL_ID in"
					+ " (SELECT Model_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  )as modelName , "
					+ "(SELECT vmk.make_name FROM vehicle_makes vmk WHERE vmk.make_id in (SELECT Make_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  ) makeName , "
					+ " (SELECT vehicle_reg_No FROM vehicles WHERE vehicle_id IN (SELECT vehicle_id FROM loans l2 WHERE l2.loan_no = l.loan_no )) as registrationNo"
					+ " , 'DefaultVC' as vendorCode,'DefaultBM' as bankManager,  'DefaultCustomer' as customerName, 'DefaultMake' as makeandmodel,'DefaultAC' as accountCode"
					+ " ,(SELECT vehicle_Manuf_Date FROM vehicles WHERE vehicle_id IN (SELECT vehicle_id FROM loans l2 WHERE l2.loan_no = l.loan_no ))  as manufacturingYear  FROM loans l LEFT JOIN customerslatest cl  ON l.CUSTOMER_ID = cl.CUSTOMER_ID WHERE l.loan_No ='" + loanNo + "'";
			//customerLiabilitiesBeanList =	session.createNativeQuery(sqlQuery).list();
			customerLiabilitiesBeanList =	(List<ExportDetailsTo>)session.createNativeQuery(sqlQuery)
					.addScalar("loanNo").addScalar("bankName").addScalar("customerName").addScalar("makeandmodel")
					.addScalar("firstName" ).addScalar("lastName").addScalar("makeName").addScalar("modelName").addScalar("registrationNo").addScalar("bankManager")
					.addScalar("vendorCode").addScalar("accountCode").addScalar("location").addScalar("manufacturingYear" , new DateType()).addScalar("amount")
					
					
					.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).list();
			         
			ExportDetailsTo ext = customerLiabilitiesBeanList.get(0);
			ext.setRefNo(loanNo);
			
			
			
			
			//customerLiabilitiesBeanList= qry.getResultList();
//			System.out.println(customerLiabilitiesBeanList.toString());
//			 for(ExportDetailsTo ex : customerLiabilitiesBeanList) {
//				 System.out.println(ex.getFirstName());
//			 }

			//customerLiabilitiesBeanList = session.createQuery(sqlQuery).setParameter("id", loanNo)
					
					//.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerLiabilitiesBeanList;
	}
	
	@Override
	public List<ExportDetailsTo> fetctbordResolutionrecord(String loanNo) {
		Session session = (Session) entityManager.getDelegate();
		List<ExportDetailsTo> customerLiabilitiesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " SELECT l.Loan_Duration as loanDuration,l.loan_amount as amount, cl.FIRST_NAME as firstName, cl.LAST_NAME as lastName, ca.ADDRESS as address, ca.CITY as city, ca.DISTRICT as district, ca.POSTAL_CODE "
					+ " as postalCode, (SELECT apl.PARAMETER_LIST_VALUE FROM  application_parameter_lists apl WHERE apl.PARAMETER_LIST_ID = l.loan_bank_id) "
					+ " AS bankName, ( SELECT vm.MODEL_NAME from vehicle_models vm WHERE vm.MODEL_ID in "
					+ " (SELECT Model_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  )as modelName, 'DefaultCustomer' as customerName,'loanAmountinWords' as loanAmountinWords FROM loans l LEFT JOIN customerslatest cl "
					+ "ON l.CUSTOMER_ID = cl.CUSTOMER_ID LEFT JOIN  customer_addresses ca on cl.CUSTOMER_ID = ca.CUSTOMER_ID WHERE ca.record_type <>'D' and loan_no = '"+ loanNo +"'"
			        +"AND ca.address_type = 'Current' LIMIT 1";
			//customerLiabilitiesBeanList =	session.createNativeQuery(sqlQuery).list();
			  customerLiabilitiesBeanList =	(List<ExportDetailsTo>)session.createNativeQuery(sqlQuery)
					.addScalar("loanDuration", new DoubleType()).addScalar("amount").addScalar("bankName").addScalar("customerName").addScalar("district")
					.addScalar("firstName" ).addScalar("lastName").addScalar("address").addScalar("modelName").addScalar("city")
					.addScalar("loanAmountinWords").addScalar("postalCode")
					
					.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).list();
			
			
			
			
			
			//customerLiabilitiesBeanList= qry.getResultList();
//			System.out.println(customerLiabilitiesBeanList.toString());
//			 for(ExportDetailsTo ex : customerLiabilitiesBeanList) {
//				 System.out.println(ex.getFirstName());
//			 }

			//customerLiabilitiesBeanList = session.createQuery(sqlQuery).setParameter("id", loanNo)
					
					//.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerLiabilitiesBeanList;
	}
	@Override
	public List<ExportDetailsTo> fetctauthorisationDeclarationrecord(String loanNo) {
		Session session = (Session) entityManager.getDelegate();
		List<ExportDetailsTo> customerLiabilitiesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " SELECT l.loan_amount as amount, cl.FIRST_NAME as firstName, cl.LAST_NAME as lastName, ca.ADDRESS as address,cl.FIRM_FORMATION_DATE AS partnershipDate, ca.CITY as city, ca.DISTRICT as district, ca.POSTAL_CODE "
					+ " as postalCode, (SELECT apl.PARAMETER_LIST_VALUE FROM  application_parameter_lists apl WHERE apl.PARAMETER_LIST_ID = l.loan_bank_id) "
					+ " AS bankName, ( SELECT vm.MODEL_NAME from vehicle_models vm WHERE vm.MODEL_ID in "
					+ " (SELECT Model_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  )as modelName, 'DefaultCustomer' as customerName,'loanAmountinWords' as loanAmountinWords FROM loans l LEFT JOIN customerslatest cl "
					+ "ON l.CUSTOMER_ID = cl.CUSTOMER_ID LEFT JOIN  customer_addresses ca on cl.CUSTOMER_ID = ca.CUSTOMER_ID WHERE loan_no = '"+ loanNo +"'"
			        +"AND ca.address_type = 'Current' LIMIT 1";
			//customerLiabilitiesBeanList =	session.createNativeQuery(sqlQuery).list();
			  customerLiabilitiesBeanList =	(List<ExportDetailsTo>)session.createNativeQuery(sqlQuery)
					.addScalar("amount").addScalar("bankName").addScalar("customerName").addScalar("district")
					.addScalar("firstName" ).addScalar("lastName").addScalar("address").addScalar("modelName").addScalar("city")
					.addScalar("loanAmountinWords").addScalar("postalCode").addScalar("partnershipDate")
					
					.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).list();
			
			
			
			
			
			//customerLiabilitiesBeanList= qry.getResultList();
//			System.out.println(customerLiabilitiesBeanList.toString());
//			 for(ExportDetailsTo ex : customerLiabilitiesBeanList) {
//				 System.out.println(ex.getFirstName());
//			 }

			//customerLiabilitiesBeanList = session.createQuery(sqlQuery).setParameter("id", loanNo)
					
					//.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerLiabilitiesBeanList;
	}
	
	@Override
	public List<PartnerTo> fetctPartnershiprecord(String loanNo) {
		Session session = (Session) entityManager.getDelegate();
		List<PartnerTo> customerLiabilitiesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = "SELECT cm.MEMBER_NAME AS partnerName FROM loans l LEFT JOIN customerslatest cl  ON l.customer_id = cl.CUSTOMER_ID LEFT JOIN customer_members cm "
					+ "  ON cl.CUSTOMER_ID = cm.CUSTOMER_ID WHERE loan_no = '"+ loanNo +"' ";
					
					
			//customerLiabilitiesBeanList =	session.createNativeQuery(sqlQuery).list();
			  customerLiabilitiesBeanList =	(List<PartnerTo>)session.createNativeQuery(sqlQuery)
					.addScalar("partnerName")
					
					.setResultTransformer(Transformers.aliasToBean(PartnerTo.class)).list();
					
		
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerLiabilitiesBeanList;
	}
	
	@Override
	public List<ExportDetailsTo> fetct1a_authorisationDeclarationrecord(String loanNo) {
		Session session = (Session) entityManager.getDelegate();
		List<ExportDetailsTo> customerLiabilitiesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " SELECT l.loan_amount as amount, cl.FIRST_NAME as firstName, cl.LAST_NAME as lastName, ca.ADDRESS as address,cl.FIRM_FORMATION_DATE AS partnershipDate, ca.CITY as city, ca.DISTRICT as district, ca.POSTAL_CODE "
					+ " as postalCode, (SELECT apl.PARAMETER_LIST_VALUE FROM  application_parameter_lists apl WHERE apl.PARAMETER_LIST_ID = l.loan_bank_id) "
					+ " AS bankName, ( SELECT vm.MODEL_NAME from vehicle_models vm WHERE vm.MODEL_ID in (SELECT Model_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  )as modelName,"
					+ " (SELECT MEMBER_NAME FROM customer_members cm  WHERE cm.customer_id = l.CUSTOMER_ID LIMIT 1 ) customerName1,"
					+ " 'DefaultCustomer' as customerName,'loanAmountinWords' as loanAmountinWords FROM" 
					+ " loans l LEFT JOIN customerslatest cl ON l.CUSTOMER_ID = cl.CUSTOMER_ID LEFT JOIN  customer_addresses ca on cl.CUSTOMER_ID = ca.CUSTOMER_ID WHERE loan_no = '"+ loanNo +"'"
			        +" AND ca.address_type = 'Current' LIMIT 1";
			//customerLiabilitiesBeanList =	session.createNativeQuery(sqlQuery).list();
			  customerLiabilitiesBeanList =	(List<ExportDetailsTo>)session.createNativeQuery(sqlQuery)
					.addScalar("amount").addScalar("bankName").addScalar("customerName").addScalar("district")
					.addScalar("firstName" ).addScalar("lastName").addScalar("address").addScalar("modelName").addScalar("city")
					.addScalar("loanAmountinWords").addScalar("postalCode").addScalar("partnershipDate").addScalar("customerName1")
					
					.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).list();
			
			
			
			
			
			//customerLiabilitiesBeanList= qry.getResultList();
//			System.out.println(customerLiabilitiesBeanList.toString());
//			 for(ExportDetailsTo ex : customerLiabilitiesBeanList) {
//				 System.out.println(ex.getFirstName());
//			 }

			//customerLiabilitiesBeanList = session.createQuery(sqlQuery).setParameter("id", loanNo)
					
					//.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerLiabilitiesBeanList;
	}
	
	@Override
	public List<ExportDetailsTo> usedCarVehicle(String loanNo) {
		Session session = (Session) entityManager.getDelegate();
		List<ExportDetailsTo> customerLiabilitiesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " SELECT l.LOAN_NO as loanNo ,l.net_Disbursed as amount ,cl.FIRST_NAME as firstName  , cl.FATHER_NAME as fatherName, cl.PAN_NO as panNo,cl.AADHAR_NO as adharNo, cl.last_name as lastName,"
					+" (SELECT apl.PARAMETER_LIST_VALUE FROM  application_parameter_lists apl WHERE apl.PARAMETER_LIST_ID = l.loan_bank_id) AS bankName,( SELECT vm.MODEL_NAME from vehicle_models vm WHERE vm.MODEL_ID in"
					+ " (SELECT Model_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  )as modelName , "
					+ "(SELECT vmk.make_name FROM vehicle_makes vmk WHERE vmk.make_id in (SELECT Make_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  ) makeName , "
					+ " (SELECT vehicle_reg_No FROM vehicles WHERE vehicle_id IN (SELECT vehicle_id FROM loans l2 WHERE l2.loan_no = l.loan_no ))"
					+ " as registrationNo,  'DefaultCustomer' as customerName, 'DefaultMake' as makeandmodel ,"
					+ "(SELECT vehicle_engine_No FROM vehicles WHERE vehicle_id IN (SELECT vehicle_id FROM loans l2 WHERE l2.loan_no = l.loan_no ))  AS engineNo,"
					+ "(select ca.CONTACT_NUMBER from customer_addresses ca WHERE ca.CUSTOMER_ID IN (SELECT l1.CUSTOMER_ID from loans l1 WHERE l1.LOAN_NO = l.loan_no )limit 1 ) AS contactNo,"
					+ "(SELECT vehicle_chesis_No FROM vehicles WHERE vehicle_id IN (SELECT vehicle_id FROM loans l2 WHERE l2.loan_no = l.loan_no ))  as chassisNo,(SELECT AT.aggregator_task_charges FROM aggregator_tasks AT WHERE AT.aggregator_id = agg.AGGREGATOR_ID LIMIT 1) rtoCharges "
					+ " FROM loans l LEFT JOIN customerslatest cl  ON l.CUSTOMER_ID = cl.CUSTOMER_ID LEFT JOIN inquiries i ON cl.CUSTOMER_ID = i.CUSTOMER_ID LEFT JOIN aggregators agg ON i.INQUIRY_ID = agg.INQUIRY_ID WHERE i.INQUIRY_ID = l.INQUIRY_ID and l.loan_No ='" + loanNo + "'";
			//customerLiabilitiesBeanList =	session.createNativeQuery(sqlQuery).list();
			customerLiabilitiesBeanList =	(List<ExportDetailsTo>)session.createNativeQuery(sqlQuery)
					.addScalar("loanNo").addScalar("bankName").addScalar("customerName").addScalar("makeandmodel")
					.addScalar("amount" , new DoubleType())
					.addScalar("firstName" ).addScalar("lastName").addScalar("makeName").addScalar("modelName").addScalar("registrationNo")
					.addScalar("rtoCharges" , new IntegerType()).addScalar("engineNo").addScalar("chassisNo").addScalar("fatherName").addScalar("panNo").addScalar("adharNo")
					.addScalar("contactNo")
					.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).list();
			ExportDetailsTo ext = customerLiabilitiesBeanList.get(0);
			
			ext.setRefNo(loanNo);
			
			
			
			//customerLiabilitiesBeanList= qry.getResultList();
//			System.out.println(customerLiabilitiesBeanList.toString());
//			 for(ExportDetailsTo ex : customerLiabilitiesBeanList) {
//				 System.out.println(ex.getFirstName());
//			 }

			//customerLiabilitiesBeanList = session.createQuery(sqlQuery).setParameter("id", loanNo)
					
					//.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerLiabilitiesBeanList;
	}
	
	@Override
	public List<ExportDetailsTo> indemnity(String loanNo) {
		Session session = (Session) entityManager.getDelegate();
		List<ExportDetailsTo> customerLiabilitiesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " SELECT l.net_disbursed as amount, (SELECT account_no FROM customer_bank_details WHERE customer_id = l.customer_id LIMIT 1) AS accountNo, (SELECT ifsc_code FROM customer_bank_details WHERE customer_id = l.customer_id LIMIT 1 ) AS ifsc,l.LOAN_NO as loanNo ,cl.FIRST_NAME as firstName  , cl.FATHER_NAME as fatherName, cl.PAN_NO as panNo,cl.AADHAR_NO as adharNo, cl.last_name as lastName,"
//					+" (SELECT apl.PARAMETER_LIST_VALUE FROM  application_parameter_lists apl WHERE apl.PARAMETER_LIST_ID = l.loan_bank_id) AS bankName,( SELECT vm.MODEL_NAME from vehicle_models vm WHERE vm.MODEL_ID in"
                    + " l.bank_name AS bankName,( SELECT vm.MODEL_NAME from vehicle_models vm WHERE vm.MODEL_ID in"                   
					+ " (SELECT Model_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  )as modelName , (select insurance_amount from vehicle_insurances where loan_id  = (SELECT loan_id FROM loans WHERE loan_no = l.loan_no)) as insurence, "
					+ "(SELECT vmk.make_name FROM vehicle_makes vmk WHERE vmk.make_id in (SELECT Make_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  ) makeName , "
					+ " (SELECT vehicle_reg_No FROM vehicles WHERE vehicle_id IN (SELECT vehicle_id FROM loans l2 WHERE l2.loan_no = l.loan_no ))"
					+ " as registrationNo,  'DefaultCustomer' as customerName, 'DefaultMake' as makeandmodel ,"
					+ "(SELECT vehicle_engine_No FROM vehicles WHERE vehicle_id IN (SELECT vehicle_id FROM loans l2 WHERE l2.loan_no = l.loan_no ))  AS engineNo,"
					+ "(select ca.CONTACT_NUMBER from customer_addresses ca WHERE ca.CUSTOMER_ID IN (SELECT l1.CUSTOMER_ID from loans l1 WHERE l1.LOAN_NO = l.loan_no )limit 1 ) AS contactNo,"
					+ "(SELECT vehicle_chesis_No FROM vehicles WHERE vehicle_id IN (SELECT vehicle_id FROM loans l2 WHERE l2.loan_no = l.loan_no ))  as chassisNo , (SELECT AT.aggregator_task_charges FROM aggregator_tasks AT WHERE AT.aggregator_id = agg.AGGREGATOR_ID LIMIT 1) rtoCharges"
					+ " FROM loans l LEFT JOIN customerslatest cl  ON l.CUSTOMER_ID = cl.CUSTOMER_ID LEFT JOIN inquiries i ON cl.CUSTOMER_ID = i.CUSTOMER_ID LEFT JOIN aggregators agg ON i.INQUIRY_ID = agg.INQUIRY_ID WHERE i.INQUIRY_ID = l.INQUIRY_ID and  l.loan_No ='" + loanNo + "'";
			//customerLiabilitiesBeanList =	session.createNativeQuery(sqlQuery).list();
			customerLiabilitiesBeanList =	(List<ExportDetailsTo>)session.createNativeQuery(sqlQuery)
					.addScalar("loanNo").addScalar("bankName").addScalar("customerName").addScalar("makeandmodel")
					.addScalar("firstName" ).addScalar("lastName").addScalar("makeName").addScalar("modelName").addScalar("registrationNo")
					.addScalar("engineNo").addScalar("chassisNo").addScalar("fatherName").addScalar("panNo").addScalar("adharNo")
					.addScalar("contactNo").addScalar("accountNo").addScalar("ifsc").addScalar("amount").addScalar("rtoCharges").addScalar("insurence")
					.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).list();
			
			
			
			
			
			//customerLiabilitiesBeanList= qry.getResultList();
//			System.out.println(customerLiabilitiesBeanList.toString());
//			 for(ExportDetailsTo ex : customerLiabilitiesBeanList) {
//				 System.out.println(ex.getFirstName());
//			 }

			//customerLiabilitiesBeanList = session.createQuery(sqlQuery).setParameter("id", loanNo)
					
					//.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerLiabilitiesBeanList;
	}

	@Override
	public List<ExportDetailsTo> welcome(String loanNo) {
		Session session = (Session) entityManager.getDelegate();
		List<ExportDetailsTo> customerLiabilitiesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " SELECT l.loan_amount as amount, cl.FIRST_NAME as firstName, cl.LAST_NAME as lastName, ca.ADDRESS as address,cl.FIRM_FORMATION_DATE AS partnershipDate, ca.CITY as city, ca.DISTRICT as district, ca.POSTAL_CODE "
					+ " as postalCode, (SELECT apl.PARAMETER_LIST_VALUE FROM  application_parameter_lists apl WHERE apl.PARAMETER_LIST_ID = l.loan_bank_id) "
					+ " AS bankName, ( SELECT vm.MODEL_NAME from vehicle_models vm WHERE vm.MODEL_ID in (SELECT Model_id FROM vehicles v WHERE v.vehicle_id = l.VEHICLE_ID)  )as modelName,"
					+ " (SELECT MEMBER_NAME FROM customer_members cm  WHERE cm.customer_id = l.CUSTOMER_ID LIMIT 1 ) customerName1,"
					+ "(select ca.CONTACT_NUMBER from customer_addresses ca WHERE ca.CUSTOMER_ID IN (SELECT l1.CUSTOMER_ID from loans l1 WHERE l1.LOAN_NO = l.loan_no )limit 1 ) AS contactNo,"
					+ " MONTHNAME(l.Loan_Start_date)as emiMonth , year(l.loan_start_Date) as emiYear, DAY(l.loan_start_Date) as emiDay,l.loan_Duration as loanDuration,l.loan_installment as installment ,"
					+ " 'DefaultCustomer' as customerName,'loanAmountinWords' as loanAmountinWords FROM" 
					+ " loans l LEFT JOIN customerslatest cl ON l.CUSTOMER_ID = cl.CUSTOMER_ID LEFT JOIN  customer_addresses ca on cl.CUSTOMER_ID = ca.CUSTOMER_ID  WHERE loan_no = '"+ loanNo +"'"
			        +" AND ca.address_type = 'Current' LIMIT 1";
			
			  customerLiabilitiesBeanList =	(List<ExportDetailsTo>)session.createNativeQuery(sqlQuery)
					.addScalar("amount").addScalar("bankName").addScalar("customerName").addScalar("district")
					.addScalar("firstName" ).addScalar("lastName").addScalar("address").addScalar("modelName").addScalar("city")
					.addScalar("loanAmountinWords").addScalar("postalCode").addScalar("partnershipDate").addScalar("customerName1")
					.addScalar("emiMonth").addScalar("emiYear").addScalar("emiDay").addScalar("loanDuration").addScalar("installment").addScalar("contactNo")
					
					.setResultTransformer(Transformers.aliasToBean(ExportDetailsTo.class)).list();
									
			
			} catch (Exception e) {
			e.printStackTrace();
		}

		return customerLiabilitiesBeanList;
	}

}
