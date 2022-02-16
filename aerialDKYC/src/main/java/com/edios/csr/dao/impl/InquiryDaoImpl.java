package com.edios.csr.dao.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.impl.BaseDaoImpl;
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
import com.edios.csr.dao.InquiryDao;
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

@Repository
@SuppressWarnings({ "unchecked", "deprecation" })
public class InquiryDaoImpl extends BaseDaoImpl<CustomerEntity> implements InquiryDao {

	@Autowired
	MessageSource messageSource;

	@Override
	public List<CustomersTO> fetchcustomerData(CustomerAddressBean customerAddressBean) {
		List<CustomersTO> customerTO = null;

		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		if (Optional.ofNullable(customerAddressBean.getContactNumber()).isPresent()) {
			sqlQuery = "Select ca.customerAddId as customerAddId, c.customerId as customerId, c.nameTitle as nameTitle, c.firstName as firstName,concat(c.firstName,' ',case when c.lastName is not null then c.lastName else '' end) as fullName , "
					+ " c.middleName as middleName, c.lastName as lastName,ca.city as city,ca.postalCode as postalCode,ca.transactionCount as transactionCount, "
					+ " ca.contactNumber as contactNumber, ca.emailAddress as emailAddress, c.panNo as panNo,c.aadharNo as aadharNo,c.customerType as customerType, "
					+ " 	c.gstNo as gstNo,c.firmType as firmType,c.firmFormationDate as firmFormationDate,c.customerType as customerType, "
					+ " 	c.dlNo as dlNo,c.voterId as voterId,c.passportNumber as passportNumber "
					+ " from  CustomerAddressEntity ca right join  ca.customerId c  where ca.contactNumber ="
					+ customerAddressBean.getContactNumber() + " and "
					+ "  ca.recordType<>'D' and c.recordType<>'D' and c.status='Active' and ca.addressType in('Permanent','Current','Office') ";
		} else {
			sqlQuery = "Select c.customerId as customerId, concat(c.nameTitle,' ', c.firstName,' ',case when c.lastName is not null then c.lastName else '' end) as fullName "
					+ " from CustomerEntity c  where c.recordType<>'D' and c.status='Active'";
		}
		customerTO = (List<CustomersTO>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(CustomersTO.class)).list();
		return customerTO;
	}

	@Override
	public List<StaffTo> fetchStaffData(StaffBean staffBean) {
		List<StaffTo> staffTO = null;

		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();

		if (staffBean.getIpAddress() != null && staffBean.getIpAddress().equalsIgnoreCase("All")) {
			sqlQuery = "Select s.staffId as staffId, concat(s.firstName,' ', s.lastName,' (',s.staffCode,') (',s.staffType,')' ) as fullName "
					+ " from StaffEntity s "
					+ " where s.recordType<>'D' and s.status!='Inactive' order by  s.firstName ASC ";
		} else {
			sqlQuery = "Select s.staffId as staffId, concat(s.firstName,' ', s.lastName,' (',s.staffCode,') (',s.staffType,')' ) as fullName "
					+ " from StaffEntity s " + " where s.accountId=" + staffBean.getAccountId() + " AND s.siteId="
					+ staffBean.getSiteId() + " AND "
					+ " s.recordType<>'D' and s.status='Active' order by  s.firstName ASC";
		}

		staffTO = (List<StaffTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(StaffTo.class)).list();
		return staffTO;
	}

	@Override
	public String addInquiry(InquiriesEntity inquiriesEntity) {
		try {
			entityManager.persist(inquiriesEntity);
			String format = LocalDate.now() + "";
			String prefix = "";
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("Loan Against Property")) {
				prefix = "LAP";
			}
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("Home Loan")) {
				prefix = "HML";
			}
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("Insurance")) {
				prefix = "INS";
			}
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("Used Car Sale")) {
				prefix = "UCS";
			}
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("Used Car Purchase")) {
				prefix = "UCP";
			}
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("Vehicle Loan-New Car")) {
				prefix = "VHLNC";
			}
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("Vehicle Loan-Refinance Car")) {
				prefix = "VHLRC";
			}
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("Vehicle Loan-Used Car Purchase")) {
				prefix = "VHLUCP";
			}
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("Task To Do")) {
				prefix = "TASKS";
			}
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("Help & Support")) {
				prefix = "HAS";
			}
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("Aggregator")) {
				prefix = "AG";
			}
			if (inquiriesEntity.getInquiryType().equalsIgnoreCase("New Car")) {
				prefix = "NC";
			}
			inquiriesEntity.setInquiryNo(
					prefix + format.replace("-", "").substring(2, 6) + "00" + inquiriesEntity.getInquiryId() + "");
			return inquiriesEntity.getInquiryId() + "";

		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}

	}

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

	@Override
	public boolean addInquiryVehicle(VehicelsEntity inquiriesVehicelsEntity) {
		boolean result = true;
		try {
			entityManager.persist(inquiriesVehicelsEntity);
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

	}

	@Override
	public List<MailDataTO> getMailData(Long id) {
		List<MailDataTO> mailDataTO = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select i.inquiryNo as inquiryNo, concat(c.nameTitle ,' ', c.firstName,' ',' ', case when c.lastName is not null then c.lastName else '' end) "
				+ "as fullName,i.contactNumber as contactNumber,s2.emailAddress as emailAddress1,s2.contactNumber as assigneeContact "
				+ ",DATE_FORMAT(i.inquiryDate,'" + dateTimeFormat
				+ "') As inquiryDate ,i.inquiryType as inquiryType,s1.emailAddress as emailAddress,"
				+ "concat(s2.nameTitle ,' ', s2.firstName,' ',' ', s2.lastName ) as assigneeName ,i.emailAddress as customerEmail "
				+ " from InquiriesEntity i left join   CustomerEntity  c    on  i.customerId=c.customerId "
				+ " left join StaffEntity  s1  on i.sourceStaffId=s1.staffId left join StaffEntity  s2  on i.assignedStaffId=s2.staffId  where "
				+ "i.inquiryId=" + id + "";
		mailDataTO = (List<MailDataTO>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(MailDataTO.class)).list();

		return mailDataTO;
	}

	@Override
	public List<InquiryTO> fetchInquiryData(InquiryBean inquiryBean) {
		List<InquiryTO> inquiryTO = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateto = null;
//		Date afterTwoMonths = Date
//				.from(LocalDate.now().minusMonths(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select i.inquiryNo as inquiryNo,i.inquiryId as inquiryId,l.loanId as loanId, concat(i.nameTitle ,' ', i.firstName,' ',case when i.middleName is not null then i.middleName else '' end,' ', i.lastName ) "
				+ "as customerFullname,i.contactNumber as contactNumber " + ",DATE_FORMAT(i.inquiryDate,'"
				+ dateTimeFormat + "') As inquiryDate ,DATE_FORMAT(i.callbackDate,'" + dateTimeFormat
				+ "') As callbackDate,i.transactionCount as transactionCount ,i.statusDate as statusDate, "
				+ " i.inquiryType as inquiryType,i.inquiryStatus as inquiryStatus ,DATE_FORMAT(i.lastModifiedDate,'"
				+ dateTimeFormat
				+ "') As lastModifiedDate , concat(s1.nameTitle ,' ', s1.firstName,' ',' ', s1.lastName ) as sourceName ,"
				+ "concat(s2.nameTitle ,' ', s2.firstName,' ',' ', s2.lastName ) as assigneeName ,DATE_FORMAT(i.createdDate,'"
				+ dateTimeFormat + "') As createdDate,i.transactionCount as transactionCount ,"
				+ " concat(s3.nameTitle ,' ', s3.firstName,' ',' ', s3.lastName ) as secondSource "
				+ " from InquiriesEntity i left join   CustomerEntity  c    on  i.customerId=c.customerId "
				+ " left join StaffEntity  s3  on i.sourceStaffId2=s3.staffId "
				+ " left join StaffEntity  s2  on i.assignedStaffId=s2.staffId "
				+ " left join LoanEntity l on i.inquiryId=l.inquiryId "
				+ " left join StaffEntity  s1  on i.sourceStaffId=s1.staffId  left join AccountUserEntity  a  on "
				+ inquiryBean.getSiteId() + "=a.userID " + " where i.recordType<>'D' and i.accountId="
				+ inquiryBean.getAccountId() + " and i.siteId in(a.siteID,s2.siteId) ";
// and i.inquiryDate>=:inquiryDate
		if (Optional.ofNullable(inquiryBean.getInquiryStatus()).isPresent()) {
			sqlQuery += " and i.inquiryStatus='" + inquiryBean.getInquiryStatus() + "' ";
		}
		if (Optional.ofNullable(inquiryBean.getInquiryType()).isPresent()) {

			sqlQuery += " and i.inquiryType='" + inquiryBean.getInquiryType() + "' ";
		}
		if (Optional.ofNullable(inquiryBean.getInquiryDate()).isPresent()) {
			dateto = formatter.format(inquiryBean.getInquiryDate()) + " 00:00:00";
			sqlQuery += " and i.callbackDate<='" + dateto + "' ";
		}
		if (Optional.ofNullable(inquiryBean.getCallbackDate()).isPresent()) {
			dateto = formatter.format(inquiryBean.getCallbackDate()) + " 00:00:00";
			sqlQuery += " and i.callbackDate>='" + dateto + "' ";
		}
		if (Optional.ofNullable(inquiryBean.getInquiryNo()).isPresent()) {
			sqlQuery += " and i.inquiryNo='" + inquiryBean.getInquiryNo() + "' ";
		}
		if (Optional.ofNullable(inquiryBean.getContactNumber()).isPresent()) {
			sqlQuery += " and i.contactNumber='" + inquiryBean.getContactNumber() + "' ";
		}
		if (Optional.ofNullable(inquiryBean.getAssignedSiteId()).isPresent()) {
			sqlQuery += " and i.assignedStaffId=" + inquiryBean.getAssignedSiteId() + " ";
		}
		if (Optional.ofNullable(inquiryBean.getSourceStaffId()).isPresent()) {
			sqlQuery += " and i.sourceStaffId=" + inquiryBean.getSourceStaffId() + " ";
		}
		if (Optional.ofNullable(inquiryBean.getName()).isPresent()) {
			sqlQuery += " and concat(i.firstName,' ',i.lastName ) like '%" + inquiryBean.getName() + "%'";
		}
		sqlQuery += " group by i.inquiryId order by i.inquiryId desc  ";
		// .setParameter("inquiryDate", afterTwoMonths)
		inquiryTO = (List<InquiryTO>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(InquiryTO.class)).list();

		for (int i = 0; i <= inquiryTO.size() - 1; i++) {
			inquiryTO.get(i).setSNo(i + 1);
		}
		return inquiryTO;
	}

	@Override
	public boolean inquiryNotesEntry(InquiriesNotesEntity inquiriesNotesEntity) {
		try {
			boolean result = true;
			entityManager.persist(inquiriesNotesEntity);
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@Override
	public List<InquiryNotesTO> getInquiryNotes(PayloadBean payloadBean) {
		List<InquiryNotesTO> inquiryNotesTO = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();

		sqlQuery = "Select i.inquiryNotes as inquiryNotes,coalesce(concat(s.firstName,' ', s.lastName ),concat(u.firstName,' ',case when u.lastName is not null then u.lastName else '' end )) as userName,DATE_FORMAT(i.noteDateTime,'"
				+ dateTimeFormat + "') As noteDateTime "
				+ " from InquiriesNotesEntity i left join UserEntity u on i.createdBy=u.userID "
				+ " left join StaffEntity s on i.staffId=s.staffId " + " where i.inquiryId="
				+ payloadBean.getSearchParameter() + " order by i.inquiryNoteId desc";
		inquiryNotesTO = (List<InquiryNotesTO>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(InquiryNotesTO.class)).list();
		return inquiryNotesTO;
	}

	@Override
	public InquiriesEntity findInquiryById(PayloadBean payloadbean) {
		@SuppressWarnings("unused")
		Session session = (Session) entityManager.getDelegate();
		InquiriesEntity inquiriesEntity = entityManager.find(InquiriesEntity.class, payloadbean.getId());
		return inquiriesEntity;
	}

	@Override
	public List<CustomersTO> editCustomer(PayloadBean payloadbean) {
		List<CustomersTO> customerTO = null;
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select c.customerId as customerId, c.panNo as panNo,c.aadharNo as aadharNo ,c.transactionCount as transactionCount,"
				+ " concat(u.firstName,' ',case when u.lastName is not null then u.lastName else '' end ) as createdByName ,c.age as age,"
				+ " concat(c.firstName,' ',case when c.lastName is not null then c.lastName else '' end ) as fullName ,c.professionType as professionType,"
				+ " c.landholding as landholding,c.actualLandholding as actualLandholding,c.villageName as villageName ,c.tehsil as tehsil ,"
				+ " c.district as district ,c.ownerName as ownerName ,c.relationWOwner as relationWOwner,c.city as city,c.dateOfBirth as dateOfBirth,  "
				+ "	c.gstNo as gstNo,c.firmType as firmType,c.firmFormationDate as firmFormationDate,c.customerType as customerType, "
				+ " c.designation as designation,c.occupation as occupation,c.landholding as landholding,c.actualLandholding as actualLandholding,"
				+ " c.villageName as villageName,c.tehsil as tehsil,c.district as district,c.ownerName as ownerName ,c.relationWOwner as relationWOwner,"
				+ "	c.dlNo as dlNo,c.voterId as voterId,c.passportNumber as passportNumber,c.city as city,c.cibilScore as cibilScore "
				+ " from CustomerEntity c  left join UserEntity u on c.createdBy=u.userID " + " where c.customerId ="
				+ payloadbean.getId() + " ";
		customerTO = (List<CustomersTO>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(CustomersTO.class)).list();
		return customerTO;
	}

	@Override
	public List<VehicleTo> editVehicle(PayloadBean payloadbean) {
		List<VehicleTo> vehicleTo = null;
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select v.vehicleId as vehicleId, v.inquiryId as inquiryId, v.make as make,v.model as model,v.colour as colour,v.modelVariant as modelVariant, "
				+ " v.fuelType as fuelType, v.bodyType as bodyType,v.vehicleType as vehicleType,v.engineCC as engineCC,v.transmission as transmission,v.kms as kms, "
				+ " v.sunroof as sunroof, v.alloysWheel as alloysWheel, v.sittingCapacity as sittingCapacity,v.vehicleCylinders as vehicleCylinders,v.vehicleEngineNo as vehicleEngineNo,  "
				+ " v.vehicleChassisNo as vehicleChassisNo, v.vehicleRegNo as vehicleRegNo, v.vehicleRegDate as vehicleRegDate,v.taxPaidUpto as taxPaidUpto,v.permitUpto as permitUpto,  "
				+ " v.passingUpto as passingUpto, v.vehicleManufDate as vehicleManufDate, v.vehicleAge as vehicleAge,v.odoReading as odoReading,v.ownerSequence as ownerSequence,  "
				+ " v.previousOwner as previousOwner, v.previousRegDate as previousRegDate, v.rcStatus as rcStatus,v.bankName as bankName,v.keyAvailable as keyAvailable,  "
				+ " v.anyMajorAccidents as anyMajorAccidents, v.serviceRecords as serviceRecords, v.vehicleBuyDate as vehicleBuyDate,v.hypothecated as hypothecated,v.otherName as otherName,  "
				+ " v.vehicleStatus as vehicleStatus, v.priceMin as priceMin, v.priceMax as priceMax,v.vehicleDesc as vehicleDesc,v.transactionCount as transactionCount,  "
				+ " v.recordType as recordType,v.createdBy as createdBy,v.createdDate as createdDate ,v.loanStatus as loanStatus,v.vehicleTransactionDate"
				+ " as vehicleTransactionDate,v.vehicleTransactionAmount as vehicleTransactionAmount,v.vehicleTransactionNotes as vehicleTransactionNotes ,"
				+ " v.vehiclePaymentType as vehiclePaymentType,v.beneficiaryName as beneficiaryName,v.beneficiaryBankName as beneficiaryBankName,v.beneficiaryAccountNo as beneficiaryAccountNo, "
				+ " v.beneficiaryAmount as beneficiaryAmount,v.beneficiaryIFSCCode as beneficiaryIFSCCode,v.vehicleValue as vehicleValue ,de.dealerExecutiveName as dealerExecutiveName, "
				+ " v.vehicleStockType as vehicleStockType,v.ownedInterest as ownedInterest,v.ownedRepairCharges as ownedRepairCharges ,v.ownedSellMargin as ownedSellMargin, "
				+ " v.ownedSaleValue as ownedSaleValue,v.inquiryOpenDays as inquiryOpenDays,v.taxValidUpto as taxValidUpto ,v.fitnessUpto as fitnessUpto,v.prevRegAt as prevRegAt, "
				+ " de.dealerExecutiveId as dealerExecutiveId,d.dealerId as dealerId ,d.dealerName as dealerName,de.contactNumber as contactNumber ,v.notes as notes,v.chalan as chalan "
				+ " ,v.finalizedCar as finalizedCar,v.runningLoan as runningLoan,v.loanAmount as loanAmount from VehicelsEntity v  left join v.dealerExecutiveId de left join de.dealerId d"
				+ " where v.inquiryId =" + payloadbean.getSearchParameter() + " ";
		vehicleTo = (List<VehicleTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(VehicleTo.class)).list();
		return vehicleTo;
	}

	@Override
	public TransactionData fetchTransactionDataById(Long vehicleId) {
		TransactionData transactionData = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select transactionCount as transactionCount, recordType as recordType "
					+ " from  VehicelsEntity where vehicleId=:vehicleId";
			transactionData = (TransactionData) session.createQuery(sqlQuery).setParameter("vehicleId", vehicleId)
					.setResultTransformer(Transformers.aliasToBean(TransactionData.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

	@Override
	public boolean updateVehicle(VehicelsEntity vehicelsEntity) {
		boolean result = true;
		try {
			Session session = (Session) entityManager.getDelegate();
			session.update(vehicelsEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public String addLoan(LoanEntity loanEntity) {
		try {
			String result = "";
			entityManager.persist(loanEntity);
			result=loanEntity.getLoanId()+"";
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return "false";
		}
	}

	@Override
	public List<LoanTo> editloan(PayloadBean payloadbean) {
		List<LoanTo> loanTo = null;

		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select l.loanId as loanId,l.loanNo as loanNo,l.loanClass as loanClass,l.accountNo as accountNo,l.bankName as bankName,l.ifscCode as ifscCode,  "
				+ " l.loanType as loanType, l.loanStartDate as loanStartDate,l.loanEndDate as loanEndDate,l.loanDuration as loanDuration,l.vehicleValue as vehicleValue,l.loanAmount as loanAmount, "
				+ " l.loanInstallment as loanInstallment, l.loanRoInterest as loanRoInterest, l.advanceAmount as advanceAmount,l.processingFee as processingFee,l.gstAmount as gstAmount,  "
				+ " l.tdsAmount as tdsAmount, l.netReceivable as netReceivable, l.netDisbursed as netDisbursed,l.amountDisbursedDate as amountDisbursedDate,l.approvedAmount as approvedAmount,  "
				+ " l.loanBank as loanBank, l.loanCreditedTo as loanCreditedTo, l.loanStatus as loanStatus,l.loanNotes as loanNotes ,le.employerName as employerName, "
				+ " l.transactionCount as transactionCount,l.vehicleType as vehicleType,l.bankLocation as bankLocation,l.accountType as accountType,l.loanProgram as loanProgram,  "
				+ " l.recordType as recordType,l.createdBy as createdBy,l.createdDate as createdDate  from LoanEntity l left join LoanAddressEntity le "
				+ " on (l.loanId=le.loanId and le.recordType<>'D' and le.addressType='Office' )"
				+ " where    l.inquiryId =" + payloadbean.getSearchParameter() + " and l.loanId=:loanId   group by l.loanId order by  le.customerAddId desc";
		loanTo = (List<LoanTo>) session.createQuery(sqlQuery).setParameter("loanId", payloadbean.getUserId())
				.setResultTransformer(Transformers.aliasToBean(LoanTo.class)).list();
		return loanTo;
	}


	@Override
	public TransactionData fetchTransactionDataById1(Long loanId) {
		TransactionData transactionData = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select transactionCount as transactionCount, recordType as recordType "
					+ " from  LoanEntity where loanId=:loanId";
			transactionData = (TransactionData) session.createQuery(sqlQuery).setParameter("loanId", loanId)
					.setResultTransformer(Transformers.aliasToBean(TransactionData.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

	@Override
	public boolean updateLoan(LoanEntity loanEntity) {
		boolean result = true;
		try {
			Session session = (Session) entityManager.getDelegate();
			session.update(loanEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public List<VendorDocumentTO> fetchProjectDocumentDetails(Long projectId) {
		Session session = (Session) entityManager.getDelegate();
		List<VendorDocumentTO> uploadApplicantCvToListt = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		StringBuilder sqlString = new StringBuilder(
				" select vade.projectDocumentId as projectDocumentId,vade.documentName as documentName,DATE_FORMAT(vade.createdDate,'"
						+ dateTimeFormat + "') As uploadDate,"
						+ "vade.documentNumber as documentNumber,aplv.parameterListValue as documentType, vade.documentDesc as documentDesc,vade.transactionCount as transactionCount "
						+ " ,vade.fileName as fileName, vade.storageLocation.locationId as viewDocument"
						+ " from ProjectDocumentEntity vade left join vade.documentTypeListId aplv"
						+ " where vade.project=:projectID and vade.recordType<>'D' order by vade.projectDocumentId desc");

		uploadApplicantCvToListt = session.createQuery(sqlString.toString()).setParameter("projectID", projectId)
				.setResultTransformer(Transformers.aliasToBean(VendorDocumentTO.class)).list();
		return uploadApplicantCvToListt;
	}

	@Override
	public Long uploadProjectDocument(ProjectDocumentEntity ProjectDocumentEntity) {
		Long pkId = null;
		Session session = (Session) entityManager.getDelegate();
		pkId = (Long) session.save(ProjectDocumentEntity);
		return pkId;
	}

	@Override
	public boolean updateProjectDocumentEntity(Long pkId, Long storageId, String fileName, Integer lastModifiedBy) {

		boolean result = false;
		String hql = "update ProjectDocumentEntity vendActDocEnt set vendActDocEnt.storageLocation.locationId=:storageId "
				+ " , vendActDocEnt.fileName=:fileName "
				+ ",vendActDocEnt.lastModifiedBy=:lastModifiedBy,vendActDocEnt.lastModifiedDate=:lastModifiedDate,vendActDocEnt.recordType='U' WHERE vendActDocEnt.projectDocumentId=:requestCvId";
		Session session = (Session) entityManager.getDelegate();
		Query query = session.createQuery(hql);
		query.setParameter("storageId", storageId);
		query.setParameter("fileName", fileName);
		query.setParameter("requestCvId", pkId);
		query.setParameter("lastModifiedBy", Long.parseLong(lastModifiedBy.toString()));
		query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
		query.executeUpdate();
		result = true;
		return result;

	}

	@Override
	public boolean loanNotesEntry(LoanNotesEntity loanNotesEntity) {
		try {
			boolean result = true;
			entityManager.persist(loanNotesEntity);
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@Override
	public List<LoanNotesTo> getLoanNotes(PayloadBean payloadBean) {
		List<LoanNotesTo> loanNotesTo = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();

		sqlQuery = "Select i.loanNotes as loanNotes,i.notesType  as notesType, concat(u.firstName,' ', u.lastName ) as userName,DATE_FORMAT(i.noteDateTime,'"
				+ dateTimeFormat + "') As noteDateTime "
				+ " from LoanNotesEntity i left join UserEntity u on i.createdBy=u.userID   where i.loanId="
				+ payloadBean.getSearchParameter() + " order by i.loanNotesId desc";
		loanNotesTo = (List<LoanNotesTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(LoanNotesTo.class)).list();
		return loanNotesTo;
	}

	@Override
	public boolean saveLoanCrossSale(LoanCrossSaleEntity loanCrossSaleEntity) {
		try {
			boolean result = true;
			entityManager.persist(loanCrossSaleEntity);
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveLoanSurityCheques(LoanSurityChequesEntity loanSurityChequesEntity) {
		try {
			boolean result = true;
			entityManager.persist(loanSurityChequesEntity);
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@Override
	public List<LoanCrossSaleTo> getLoanCrossSale(PayloadBean payloadBean) {
		List<LoanCrossSaleTo> loanCrossSaleTo = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();

		sqlQuery = "Select i.itemCharges as itemCharges,a.parameterListValue  as crossSaleItem,i.loanCrossSaleId as loanCrossSaleId,a.parameterListID as itemId, "
				+ " concat(u.firstName,' ', u.lastName ) as userName,DATE_FORMAT(i.createdDate,'" + dateTimeFormat
				+ "') As createdDate,i.transactionCount as transactionCount  from LoanCrossSaleEntity i left join ApplicationParameterListEntity a on "
				+ "i.crossSaleItem =a.parameterListID left join UserEntity u on i.createdBy=u.userID  where i.loanId="
				+ payloadBean.getSearchParameter() + " and i.recordType<>'D' order by i.loanCrossSaleId desc";
		loanCrossSaleTo = (List<LoanCrossSaleTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(LoanCrossSaleTo.class)).list();
		return loanCrossSaleTo;
	}

	@Override
	public List<LoanSurityChequesTo> getLoanSurityCheques(PayloadBean payloadBean) {
		List<LoanSurityChequesTo> loanSurityChequesTo = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();

		sqlQuery = "Select i.chequeReceived as chequeReceived,i.chequeNo as chequeNo,a.parameterListValue  as bankName, i.amount as amount,DATE_FORMAT(i.chequeDate,'"
				+ dateTimeFormat
				+ "') As chequeDate,i.loanChequeId as loanChequeId,i.chequeType as chequeType,i.ifscCode as ifscCode,i.accountNo as accountNo,"
				+ " i.transactionCount as transactionCount ,i.reasonNotes as reasonNotes , "
				+ " i.accountType as accountType from LoanSurityChequesEntity i left join ApplicationParameterListEntity a on i.bankName =a.parameterListID  where i.loanId="
				+ payloadBean.getSearchParameter() + " and i.recordType<>'D'  order by i.loanChequeId desc";
		loanSurityChequesTo = (List<LoanSurityChequesTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(LoanSurityChequesTo.class)).list();
		return loanSurityChequesTo;
	}

	@Override
	public boolean saveLoanTransStatus(LoanTransStatusEntity loanTransStatusEntity) {
		try {
			boolean result = true;
			entityManager.persist(loanTransStatusEntity);
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteLoanCrossSell(PayloadBean payloadBean) {
		boolean result = true;
		Long id = Long.parseLong(payloadBean.getSearchParameter());
		try {
			String hql = "update LoanCrossSaleEntity le set  le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy"
					+ " ,le.ipAddress =:ipAddress ,le.recordType='D',le.transactionCount=:transactionCount ";
			hql = hql + " WHERE le.loanCrossSaleId=:loanCrossSaleId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", payloadBean.getId());
			query.setParameter("loanCrossSaleId", id);
			query.setParameter("ipAddress", payloadBean.getCustom1());
			query.setParameter("transactionCount", payloadBean.getUserId());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public TransactionData fetchTransactionDataById2(Long Id) {
		TransactionData transactionData = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select table.transactionCount as transactionCount,table.recordType as recordType "
					+ " from LoanCrossSaleEntity table where table.loanCrossSaleId=:Id";
			transactionData = (TransactionData) session.createQuery(sqlQuery).setParameter("Id", Id)
					.setResultTransformer(Transformers.aliasToBean(TransactionData.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

	@Override
	public boolean deleteLoanSurityCheques(PayloadBean payloadBean) {
		boolean result = true;
		Long id = Long.parseLong(payloadBean.getSearchParameter());
		try {
			String hql = "update LoanSurityChequesEntity le set  le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy"
					+ " ,le.ipAddress =:ipAddress ,le.recordType='D',le.transactionCount=:transactionCount ";
			hql = hql + " WHERE le.loanChequeId=:loanChequeId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", payloadBean.getId());
			query.setParameter("loanChequeId", id);
			query.setParameter("ipAddress", payloadBean.getCustom1());
			query.setParameter("transactionCount", 2l);
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public TransactionData fetchTransactionDataById3(Long Id) {
		TransactionData transactionData = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select table.transactionCount as transactionCount,table.recordType as recordType "
					+ " from LoanSurityChequesEntity table where table.loanChequeId=:Id";
			transactionData = (TransactionData) session.createQuery(sqlQuery).setParameter("Id", Id)
					.setResultTransformer(Transformers.aliasToBean(TransactionData.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

	@Override
	public boolean updateloanStatus(LoanStatusEntity loanstatusbean) {
		boolean result = true;

		try {
			entityManager.persist(loanstatusbean);
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

	}

	@Override
	public List<LoanStatusTo> fetchloanstatus(int vehicleId) {
		List<LoanStatusTo> LoanStatusTo = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);

		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "select lse.vehopenid as vehopenid , lse.vehicleId as vehicleId ,DATE_FORMAT (lse.openDate ,'"
				+ dateTimeFormat + "') as openDate,lse.bankName as bankName,lse.loanNo as loanNo"
				+ ",lse.foreclouserAmount as foreclouserAmount,lse.transactionCount as transactionCount from LoanStatusEntity lse where lse.vehicleId = "
				+ vehicleId + " " + " and recordType<>'D' ";
		LoanStatusTo = (List<LoanStatusTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(LoanStatusTo.class)).list();
		return LoanStatusTo;
	}

	@Override
	public boolean wvokupdateloanStatus(LoanStatusEntity loanstatusbean) {
		boolean result = true;
		try {
			Session session = (Session) entityManager.getDelegate();
			session.update(loanstatusbean);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;

	}

	@Override
	public boolean delloanStatus(int vid) {
		boolean result = true;
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "update LoanStatusEntity lse set lse.recordType='D' where lse.vehopenid=" + vid + " ";
		Query q = session.createQuery(sqlQuery);
		q.executeUpdate();
		return result;
	}

	@Override
	public boolean saveTransactionNo(InquiryBean inquiryBean) {
		boolean result = true;
		try {
			String hql = "update InquiriesEntity le set  le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy,le.statusDate=:statusDate, "
					+ " le.ipAddress =:ipAddress ,le.recordType='U',le.transactionCount=:transactionCount,le.inquiryStatus=:inquiryStatus ";
			if (Optional.ofNullable(inquiryBean.getCity()).isPresent())
				hql = hql + " ,le.city=:city ";
			if (Optional.ofNullable(inquiryBean.getInquiryDescription()).isPresent())
				hql = hql + " ,le.inquiryDescription=:inquiryDescription ";
			if (Optional.ofNullable(inquiryBean.getPostalCode()).isPresent())
				hql = hql + " ,le.postalCode=:postalCode ";
			if (Optional.ofNullable(inquiryBean.getEmailAddress()).isPresent())
				hql = hql + " ,le.emailAddress=:emailAddress ";
			if (Optional.ofNullable(inquiryBean.getCallbackDate()).isPresent())
				hql = hql + " ,le.callbackDate=:callbackDate ";
			if (Optional.ofNullable(inquiryBean.getTransactionInquiryNo()).isPresent())
				hql = hql + " ,le.transactionInquiryNo=:transactionInquiryNo ";
			if (Optional.ofNullable(inquiryBean.getAssignedStaffId()).isPresent()
					&& inquiryBean.getAssignedStaffId() != 0)
				hql = hql + " ,le.assignedStaffId=:assignedStaffId ";
			if (Optional.ofNullable(inquiryBean.getCustomerId()).isPresent())
				hql = hql + " ,le.customerId=:customerId ";

			hql = hql + " WHERE le.inquiryId=:inquiryId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", inquiryBean.getLastModifiedBy());
			query.setParameter("inquiryId", inquiryBean.getInquiryId());
			query.setParameter("ipAddress", inquiryBean.getIpAddress());
			query.setParameter("statusDate", new Date());
			if (Optional.ofNullable(inquiryBean.getTransactionInquiryNo()).isPresent())
				query.setParameter("transactionInquiryNo", inquiryBean.getTransactionInquiryNo());
			if (Optional.ofNullable(inquiryBean.getCity()).isPresent())
				query.setParameter("city", inquiryBean.getCity());
			if (Optional.ofNullable(inquiryBean.getPostalCode()).isPresent())
				query.setParameter("postalCode", inquiryBean.getPostalCode());
			if (Optional.ofNullable(inquiryBean.getEmailAddress()).isPresent())
				query.setParameter("emailAddress", inquiryBean.getEmailAddress());
			if (Optional.ofNullable(inquiryBean.getInquiryDescription()).isPresent())
				query.setParameter("inquiryDescription", inquiryBean.getInquiryDescription());
			if (Optional.ofNullable(inquiryBean.getCallbackDate()).isPresent())
				query.setParameter("callbackDate", inquiryBean.getCallbackDate());
			query.setParameter("inquiryStatus", inquiryBean.getInquiryStatus());
			query.setParameter("transactionCount", inquiryBean.getTransactionCount() + 1L);
			if (Optional.ofNullable(inquiryBean.getAssignedStaffId()).isPresent()
					&& inquiryBean.getAssignedStaffId() != 0)
				query.setParameter("assignedStaffId", inquiryBean.getAssignedStaffId());
			if (Optional.ofNullable(inquiryBean.getCustomerId()).isPresent())
				query.setParameter("customerId", inquiryBean.getCustomerId());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean saveVehicleTransactionDetail(VehiclesBean vehiclesBean) {
		boolean result = true;
		try {
			String hql = "update VehicelsEntity le set  le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy"
					+ " ,le.ipAddress =:ipAddress ,le.recordType='U',le.transactionCount=:transactionCount,"
					+ " le.vehicleTransactionNotes=:vehicleTransactionNotes ,vehicleTransactionAmount =:vehicleTransactionAmount ,"
					+ "le.vehicleTransactionDate =:vehicleTransactionDate WHERE le.vehicleId=:vehicleId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", vehiclesBean.getLastModifiedBy());
			query.setParameter("vehicleId", vehiclesBean.getVehicleId());
			query.setParameter("ipAddress", vehiclesBean.getIpAddress());
			query.setParameter("vehicleTransactionDate", vehiclesBean.getVehicleTransactionDate());
			query.setParameter("vehicleTransactionNotes", vehiclesBean.getVehicleTransactionNotes());
			query.setParameter("vehicleTransactionAmount", vehiclesBean.getVehicleTransactionAmount());
			query.setParameter("transactionCount", vehiclesBean.getTransactionCount() + 1L);
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public List<InquiryTO> fetchInquiryNo(InquiryBean inquiryBean) {
		List<InquiryTO> inquiryTO = null;
		String sqlQuery = "";
		String inquiryType = "";
		if (inquiryBean.getInquiryType().equalsIgnoreCase("Used Car Sale")) {
			inquiryType = "Used Car Purchase";
		} else {
			inquiryType = "Used Car Sale";
		}
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select i.inquiryNo as inquiryNo,i.inquiryId as inquiryId,concat(i.nameTitle ,' ', i.firstName,' ',' ', i.lastName ) as name from InquiriesEntity i"
				+ "  where i.recordType<>'D' and i.inquiryType='" + inquiryType + "' and i.accountId="
				+ inquiryBean.getAccountId() + " and i.siteId=" + inquiryBean.getSiteId() + " and i.contactNumber="
				+ inquiryBean.getContactNumber() + "  order by i.inquiryId desc";
		inquiryTO = (List<InquiryTO>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(InquiryTO.class)).list();
		return inquiryTO;
	}

	@Override
	public List<InquiryTO> fetchInquiryLoanData(ManageLoansBean manageLoansBean) {
		List<InquiryTO> inquiryTO = null;
		String sqlQuery = "";
		String dateto = null;
		String dateFrom = null;
		if (Optional.ofNullable(manageLoansBean.getAadharNo()).isPresent()) {
			manageLoansBean.setAadharNo(manageLoansBean.getAadharNo().replaceAll("-", ""));
		}
//		+ " left join StaffEntity  s1  on i.sourceStaffId=s1.staffId  left join AccountUserEntity  a  on "
//		+ inquiryBean.getSiteId() + "=a.userID where i.recordType<>'D' and i.accountId="
//		+ inquiryBean.getAccountId() + " and i.siteId in(a.siteID,s2.siteId) ";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select le.loanId as loanId,le.loanNo as loanNo,a1.parameterListValue as loanType,a2.parameterListValue as loanClass,a3.parameterListValue as loanStatus,"
				+ " i.inquiryNo as inquiryNo,i.inquiryId as inquiryId,concat(i.nameTitle ,' ', i.firstName,' ',case when i.middleName is not null then i.middleName else '' end,' ', i.lastName ) "
				+ "as customerFullname,a4.parameterListValue as loanBank,le.loanAmount as loanAmount, "
				+ "i.inquiryType as inquiryType,i.contactNumber as contactNumber,c.customerType as customerType,c.dateOfBirth as dateOfBirth  "
				+ " from  LoanEntity le right join le.inquiryId i left join   CustomerEntity  c    on  i.customerId=c.customerId "
				+ " left join VehicelsEntity vehicle on i.inquiryId = vehicle.inquiryId  "
				+ " left join ApplicationParameterListEntity  a1  on le.loanType=a1.parameterListID left join StaffEntity  s2  on i.assignedStaffId=s2.staffId "
				+ " left join ApplicationParameterListEntity  a2  on le.loanClass=a2.parameterListID left join ApplicationParameterListEntity  a4  on le.loanBank=a4.parameterListID  "
				+ " left join ApplicationParameterListEntity  a3  on le.loanStatus=a3.parameterListID left join AccountUserEntity  a  on "
				+ manageLoansBean.getSiteId() + "=a.userID   where i.recordType<>'D' and i.accountId="
				+ manageLoansBean.getAccountId() + " and i.siteId in (a.siteID,s2.siteId) "
				+ " and i.inquiryType in('Vehicle Loan-New Car', 'Vehicle Loan-Refinance Car','Vehicle Loan-Used Car Purchase') and  i.inquiryStatus='With Bank'  and vehicle.inquiryId is Not Null";

		if (Optional.ofNullable(manageLoansBean.getLoanStatus()).isPresent()) {
			sqlQuery += " and le.loanStatus=" + manageLoansBean.getLoanStatus().getParameterListID() + " ";
		}
		if (Optional.ofNullable(manageLoansBean.getLoanType()).isPresent()) {
			sqlQuery += " and le.loanType=" + manageLoansBean.getLoanType().getParameterListID() + " ";
		}

		if (Optional.ofNullable(manageLoansBean.getLoanBank()).isPresent()) {
			sqlQuery += " and le.loanBank=" + manageLoansBean.getLoanBank().getParameterListID() + " ";
		}
		if (Optional.ofNullable(manageLoansBean.getLoanClass()).isPresent()) {
			sqlQuery += " and le.loanClass=" + manageLoansBean.getLoanClass().getParameterListID() + " ";
		}

		if (Optional.ofNullable(manageLoansBean.getInquiryNo()).isPresent()) {
			sqlQuery += " and i.inquiryNo='" + manageLoansBean.getInquiryNo() + "' ";
		}
		if (Optional.ofNullable(manageLoansBean.getContactNumber()).isPresent()) {
			sqlQuery += " and i.contactNumber='" + manageLoansBean.getContactNumber() + "' ";
		}
		if (Optional.ofNullable(manageLoansBean.getLoanNo()).isPresent()) {
			sqlQuery += " and le.loanNo='" + manageLoansBean.getLoanNo() + "' ";
		}
		if (Optional.ofNullable(manageLoansBean.getPanNo()).isPresent()) {
			sqlQuery += " and c.panNo='" + manageLoansBean.getPanNo() + "' ";
		}
		if (Optional.ofNullable(manageLoansBean.getAadharNo()).isPresent()) {
			sqlQuery += " and c.aadharNo='" + manageLoansBean.getAadharNo() + "' ";
		}
		if (Optional.ofNullable(manageLoansBean.getLoanFromDate()).isPresent()) {
			dateFrom = formatter.format(manageLoansBean.getLoanFromDate()) + " 00:00:00";
			sqlQuery += " and le.createdDate >= '" + dateFrom + "' ";
		}
		if (Optional.ofNullable(manageLoansBean.getLoanToDate()).isPresent()) {
			dateto = formatter.format(manageLoansBean.getLoanToDate()) + " 00:00:00";
			sqlQuery += " and le.createdDate <= '" + dateto + "' ";
		}
		if (Optional.ofNullable(manageLoansBean.getName()).isPresent()) {
			sqlQuery += " and concat(i.firstName,' ',i.lastName ) like '%" + manageLoansBean.getName() + "%'";
		}
		if (Optional.ofNullable(manageLoansBean.getAssignedStaffId()).isPresent()) {
			sqlQuery += " and i.assignedStaffId=" + manageLoansBean.getAssignedStaffId().getStaffId() + " ";
		}
		if (Optional.ofNullable(manageLoansBean.getInquiryType()).isPresent()) {
			sqlQuery += " and i.inquiryType='" + manageLoansBean.getInquiryType().getParameterListValue() + "'";
		}

		if (manageLoansBean.getLoanNumberFlag().equals("All")) {
		} else if (manageLoansBean.getLoanNumberFlag().equals("Existing")) {
			sqlQuery += " and le.loanNo!=null ";
		} else {
			sqlQuery += " and le.loanNo=null ";
		}
		sqlQuery += " group by i.inquiryId,le.loanNo order by i.inquiryId desc ";
		inquiryTO = (List<InquiryTO>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(InquiryTO.class)).list();

		return inquiryTO;
	}

	@Override
	public boolean updateInquiryImages(Long pkId, Long storageId, String fileName, Long lastModifiedBy) {

		boolean result = false;
		String hql = "update InquiryImagesEntity vendActDocEnt set vendActDocEnt.storageLocationId.locationId=:storageId "
				+ " , vendActDocEnt.fileName=:fileName "
				+ ",vendActDocEnt.lastModifiedBy=:lastModifiedBy,vendActDocEnt.lastModifiedDate=:lastModifiedDate,vendActDocEnt.recordType='U' WHERE vendActDocEnt.inquiryVehImageId=:requestCvId";
		Session session = (Session) entityManager.getDelegate();
		Query query = session.createQuery(hql);
		query.setParameter("storageId", storageId);
		query.setParameter("fileName", fileName);
		query.setParameter("requestCvId", pkId);
		query.setParameter("lastModifiedBy", Long.parseLong(lastModifiedBy.toString()));
		query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
		query.executeUpdate();
		result = true;
		return result;

	}

	@Override
	public Long uploadInquiryImages(InquiryImagesEntity inquiryImagesEntity) {
		Long pkId = null;
		Session session = (Session) entityManager.getDelegate();
		pkId = (Long) session.save(inquiryImagesEntity);
		return pkId;
	}

	@Override
	public List<InquiryDocumentTO> fetchInquiryDocumentDetails(Long projectId) {
		Session session = (Session) entityManager.getDelegate();
		List<InquiryDocumentTO> uploadApplicantCvToListt = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		StringBuilder sqlString = new StringBuilder(
				" select vade.inquiryVehImageId as inquiryVehImageId,a.parameterListValue as documentName,vade.fileName as fileName ,DATE_FORMAT(vade.createdDate,'"
						+ dateTimeFormat + "') As createdDate " + " ,vade.storageLocationId.locationId as viewDocument"
						+ " from InquiryImagesEntity vade left join ApplicationParameterListEntity a on vade.documentName=a.parameterListID "
						+ " where vade.inquiryId.inquiryId=:projectID and vade.recordType<>'D' order by vade.inquiryVehImageId desc");

		uploadApplicantCvToListt = session.createQuery(sqlString.toString()).setParameter("projectID", projectId)
				.setResultTransformer(Transformers.aliasToBean(InquiryDocumentTO.class)).list();
		return uploadApplicantCvToListt;
	}

	@Override
	public boolean deleteDocument(Long deleteRecordId, Long lastModifiedBy) {
		boolean result = true;
		try {
			String hql = "update InquiryImagesEntity se set se.transactionCount=2, se.recordType='D' , se.lastModifiedBy=:lastModifiedBy ,se.lastModifiedDate=:lastModifiedDate "
					+ " WHERE se.inquiryVehImageId=:deleteRecordId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("lastModifiedBy", lastModifiedBy);
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("deleteRecordId", deleteRecordId);
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return result;
	}

	@Override
	public boolean isLoanNoExist(String loanNo) {
		try {
			return entityManager.createQuery("select table.loanNo as loanNo from LoanEntity table where "
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
	public List<DealerTo2> fetchDealerName(PayloadBean payloadBean) {
		List<DealerTo2> dealerTo = null;

		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select d.dealerId as dealerId, d.dealerName as dealerName " + " from DealerEntity2 d "
				+ " where  d.recordType<>'D' and d.dealerStatus='Active' order by  d.dealerName ASC";
		dealerTo = (List<DealerTo2>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(DealerTo2.class)).list();
		return dealerTo;
	}

	@Override
	public List<DealerExecutiveTo2> fetchDealerExecutiveName(PayloadBean payloadBean) {
		List<DealerExecutiveTo2> dealerExecutiveTo = null;

		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select de.dealerExecutiveId as dealerExecutiveId, de.dealerExecutiveName as dealerExecutiveName,de.contactNumber as contactNumber "
				+ " from DealerExecutiveEntity2 de " + " where de.dealerId=" + payloadBean.getSearchParameter()
				+ " and de.recordType<>'D' and de.executiveStatus='Active' order by  de.dealerExecutiveName ASC";
		dealerExecutiveTo = (List<DealerExecutiveTo2>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(DealerExecutiveTo2.class)).list();
		return dealerExecutiveTo;
	}

	@Override
	public String addCustomerAddress(CustomerAddressEntity customerAddressEntity) {
		try {
			entityManager.persist(customerAddressEntity);
			return customerAddressEntity.getCustomerAddId() + "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}

	@Override
	public boolean updateCustomerAddress(CustomerAddressBean customerAddressBean) {
		boolean result = true;
		try {
			String hql = "update CustomerAddressEntity ca set  ca.recordType='U' ,"
					+ "ca.lastModifiedBy=:lastModifiedBy,ca.lastModifiedDate=:lastModifiedDate ,ca.city =:city ,"
					+ " ca.transactionCount=:transactionCount ";
			if (Optional.ofNullable(customerAddressBean.getPostalCode()).isPresent())
				hql = hql + " ,ca.emailAddress=:emailAddress ";
			if (Optional.ofNullable(customerAddressBean.getEmailAddress()).isPresent())
				hql = hql + " ,ca.postalCode=:postalCode ";
			hql = hql + " WHERE ca.customerAddId=:customerAddId";

			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("lastModifiedBy", customerAddressBean.getLastModifiedBy());
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("customerAddId", customerAddressBean.getCustomerAddId());
			query.setParameter("transactionCount", customerAddressBean.getTransactionCount() + 1);
			if (Optional.ofNullable(customerAddressBean.getPostalCode()).isPresent())
				query.setParameter("postalCode", customerAddressBean.getPostalCode());
			if (Optional.ofNullable(customerAddressBean.getEmailAddress()).isPresent())
				query.setParameter("emailAddress", customerAddressBean.getEmailAddress());
			query.setParameter("city", customerAddressBean.getCity());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return result;
	}

	@Override
	public boolean updateLoanCrossSell(LoanCrossSaleBean loanCrossSaleBean) {
		boolean result = true;
		try {
			String hql = "update LoanCrossSaleEntity le set  le.crossSaleItem=:crossSaleItem,le.itemCharges=:itemCharges,le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy"
					+ " ,le.ipAddress =:ipAddress ,le.recordType='U',le.transactionCount=:transactionCount ";
			hql = hql + " WHERE le.loanCrossSaleId=:loanCrossSaleId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", loanCrossSaleBean.getLastModifiedBy());
			query.setParameter("loanCrossSaleId", loanCrossSaleBean.getLoanCrossSaleId());
			query.setParameter("ipAddress", loanCrossSaleBean.getIpAddress());
			query.setParameter("itemCharges", loanCrossSaleBean.getItemCharges());
			query.setParameter("crossSaleItem", loanCrossSaleBean.getCrossSaleItem());
			query.setParameter("transactionCount", loanCrossSaleBean.getTransactionCount());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean saveLoanCommission(LoanCommissionEntity loanCommissionEntity) {
		try {
			boolean result = true;
			entityManager.persist(loanCommissionEntity);
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveVehicleInvoice(LoanVehicleInvoiceEntity loanVehicleInvoiceEntity) {
		try {
			boolean result = true;
			entityManager.persist(loanVehicleInvoiceEntity);
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteLoanCommission(PayloadBean payloadBean) {
		boolean result = true;
		Long id = Long.parseLong(payloadBean.getSearchParameter());
		try {
			String hql = "update LoanCommissionEntity le set  le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy"
					+ " ,le.ipAddress =:ipAddress ,le.recordType='D',le.transactionCount=:transactionCount ";
			hql = hql + " WHERE le.commissionId=:commissionId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", payloadBean.getId());
			query.setParameter("commissionId", id);
			query.setParameter("ipAddress", payloadBean.getCustom1());
			query.setParameter("transactionCount", payloadBean.getUserId());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean deleteLoanVehicleInvoice(PayloadBean payloadBean) {
		boolean result = true;
		Long id = Long.parseLong(payloadBean.getSearchParameter());
		try {
			String hql = "update LoanVehicleInvoiceEntity le set  le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy"
					+ " ,le.ipAddress =:ipAddress ,le.recordType='D',le.transactionCount=:transactionCount ";
			hql = hql + " WHERE le.loanInvoiceId=:loanInvoiceId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", payloadBean.getId());
			query.setParameter("loanInvoiceId", id);
			query.setParameter("ipAddress", payloadBean.getCustom1());
			query.setParameter("transactionCount", payloadBean.getUserId());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public List<LoanCommissionTo> getLoanCommission(PayloadBean payloadBean) {
		List<LoanCommissionTo> loanCommissionTo = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();

		sqlQuery = "Select i.commissionId as commissionId,i.transactionType as transactionType,"
				+ "i.commissionType  as commissionType, i.paymentMode as paymentMode,DATE_FORMAT(i.transactionDate,'"
				+ dateTimeFormat
				+ "') As transactionDate,i.bankName as bankName,i.transactionPercentage as transactionPercentage,"
				+ " i.transactionCount as transactionCount ,i.paybleTo as paybleTo,"
				+ " i.paymentType as paymentType,i.ifscCode as ifscCode,i.accountNo as accountNo ,i.accountType as accountType,"
				+ "i.transactionValue as transactionValue   from LoanCommissionEntity i  where i.loanId="
				+ payloadBean.getSearchParameter() + " and i.recordType<>'D'  order by i.commissionId desc";
		loanCommissionTo = (List<LoanCommissionTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(LoanCommissionTo.class)).list();
		return loanCommissionTo;
	}

	@Override
	public List<LoanVehicleInvoiceTo> getLoanVehicleInvoice(PayloadBean payloadBean) {
		List<LoanVehicleInvoiceTo> loanVehicleInvoiceTo = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();

		sqlQuery = "Select i.loanInvoiceId as loanInvoiceId,i.invoiceNo as invoiceNo,i.invoiceAmount  as invoiceAmount,DATE_FORMAT(i.invoiceDate,'"
				+ dateTimeFormat
				+ "') As invoiceDate,i.transactionCount as transactionCount from LoanVehicleInvoiceEntity i  where i.loanId="
				+ payloadBean.getSearchParameter() + " and i.recordType<>'D'  order by i.loanInvoiceId desc";
		loanVehicleInvoiceTo = (List<LoanVehicleInvoiceTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(LoanVehicleInvoiceTo.class)).list();
		return loanVehicleInvoiceTo;
	}

	@Override
	public boolean updateLoanSurityCheques(LoanSurityChequesBean loanSurityChequesBean) {
		boolean result = true;
		try {
			String hql = "update LoanSurityChequesEntity le set le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy"
					+ " ,le.ipAddress =:ipAddress ,le.recordType='U',le.transactionCount=:transactionCount ";
			if (Optional.ofNullable(loanSurityChequesBean.getChequeReceived()).isPresent())
				hql = hql + " ,le.chequeReceived=:chequeReceived ";
			if (Optional.ofNullable(loanSurityChequesBean.getChequeNo()).isPresent())
				hql = hql + " ,le.chequeNo=:chequeNo ";
			if (Optional.ofNullable(loanSurityChequesBean.getChequeType()).isPresent())
				hql = hql + " ,le.chequeType=:chequeType ";
			if (Optional.ofNullable(loanSurityChequesBean.getChequeDate()).isPresent())
				hql = hql + " ,le.chequeDate=:chequeDate ";
			if (Optional.ofNullable(loanSurityChequesBean.getAmount()).isPresent())
				hql = hql + " ,le.amount=:amount ";
			if (Optional.ofNullable(loanSurityChequesBean.getBankName()).isPresent())
				hql = hql + " ,le.bankName=:bankName ";
			if (Optional.ofNullable(loanSurityChequesBean.getIfscCode()).isPresent())
				hql = hql + " ,le.ifscCode=:ifscCode ";
			if (Optional.ofNullable(loanSurityChequesBean.getAccountNo()).isPresent())
				hql = hql + " ,le.accountNo=:accountNo ";
			if (Optional.ofNullable(loanSurityChequesBean.getAccountType()).isPresent())
				hql = hql + " ,le.accountType=:accountType ";
			if (Optional.ofNullable(loanSurityChequesBean.getReasonNotes()).isPresent())
				hql = hql + " ,le.reasonNotes=:reasonNotes ";
			hql = hql + " WHERE le.loanChequeId=:loanChequeId";

			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", loanSurityChequesBean.getLastModifiedBy());
			query.setParameter("ipAddress", loanSurityChequesBean.getIpAddress());
			query.setParameter("transactionCount", loanSurityChequesBean.getTransactionCount());
			query.setParameter("loanChequeId", loanSurityChequesBean.getLoanChequeId());
			if (Optional.ofNullable(loanSurityChequesBean.getChequeReceived()).isPresent())
				query.setParameter("chequeReceived", loanSurityChequesBean.getChequeReceived());
			if (Optional.ofNullable(loanSurityChequesBean.getChequeNo()).isPresent())
				query.setParameter("chequeNo", loanSurityChequesBean.getChequeNo());
			if (Optional.ofNullable(loanSurityChequesBean.getChequeType()).isPresent())
				query.setParameter("chequeType", loanSurityChequesBean.getChequeType());
			if (Optional.ofNullable(loanSurityChequesBean.getChequeDate()).isPresent())
				query.setParameter("chequeDate", loanSurityChequesBean.getChequeDate());
			if (Optional.ofNullable(loanSurityChequesBean.getAmount()).isPresent())
				query.setParameter("amount", loanSurityChequesBean.getAmount());
			if (Optional.ofNullable(loanSurityChequesBean.getBankName()).isPresent())
				query.setParameter("bankName", loanSurityChequesBean.getBankName());
			if (Optional.ofNullable(loanSurityChequesBean.getIfscCode()).isPresent())
				query.setParameter("ifscCode", loanSurityChequesBean.getIfscCode());
			if (Optional.ofNullable(loanSurityChequesBean.getAccountNo()).isPresent())
				query.setParameter("accountNo", loanSurityChequesBean.getAccountNo());
			if (Optional.ofNullable(loanSurityChequesBean.getAccountType()).isPresent())
				query.setParameter("accountType", loanSurityChequesBean.getAccountType());
			if (Optional.ofNullable(loanSurityChequesBean.getReasonNotes()).isPresent())
				query.setParameter("reasonNotes", loanSurityChequesBean.getReasonNotes());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean updateLoanCommission(LoanCommissionBean loanCommissionBean) {
		boolean result = true;
		try {
			String hql = "update LoanCommissionEntity le set le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy"
					+ " ,le.ipAddress =:ipAddress ,le.recordType='U',le.transactionCount=:transactionCount ";

			if (Optional.ofNullable(loanCommissionBean.getTransactionDate()).isPresent())
				hql = hql + " ,le.transactionDate=:transactionDate ";
			if (Optional.ofNullable(loanCommissionBean.getTransactionType()).isPresent())
				hql = hql + " ,le.transactionType=:transactionType ";
			if (Optional.ofNullable(loanCommissionBean.getCommissionType()).isPresent())
				hql = hql + " ,le.commissionType=:commissionType ";
			if (Optional.ofNullable(loanCommissionBean.getPaymentMode()).isPresent())
				hql = hql + " ,le.paymentMode=:paymentMode ";
			if (Optional.ofNullable(loanCommissionBean.getPaymentType()).isPresent())
				hql = hql + " ,le.paymentType=:paymentType ";
			if (Optional.ofNullable(loanCommissionBean.getBankName()).isPresent())
				hql = hql + " ,le.bankName=:bankName ";
			if (Optional.ofNullable(loanCommissionBean.getIfscCode()).isPresent())
				hql = hql + " ,le.ifscCode=:ifscCode ";
			if (Optional.ofNullable(loanCommissionBean.getAccountNo()).isPresent())
				hql = hql + " ,le.accountNo=:accountNo ";
			if (Optional.ofNullable(loanCommissionBean.getAccountType()).isPresent())
				hql = hql + " ,le.accountType=:accountType ";
			if (Optional.ofNullable(loanCommissionBean.getPaybleTo()).isPresent())
				hql = hql + " ,le.paybleTo=:paybleTo ";
			if (Optional.ofNullable(loanCommissionBean.getTransactionPercentage()).isPresent())
				hql = hql + " ,le.transactionPercentage=:transactionPercentage ";
			if (Optional.ofNullable(loanCommissionBean.getTransactionValue()).isPresent())
				hql = hql + " ,le.transactionValue=:transactionValue ";
			hql = hql + " WHERE le.commissionId=:commissionId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", loanCommissionBean.getLastModifiedBy());
			query.setParameter("commissionId", loanCommissionBean.getCommissionId());
			query.setParameter("ipAddress", loanCommissionBean.getIpAddress());
			query.setParameter("transactionCount", loanCommissionBean.getTransactionCount());
			if (Optional.ofNullable(loanCommissionBean.getTransactionDate()).isPresent())
				query.setParameter("transactionDate", loanCommissionBean.getTransactionDate());
			if (Optional.ofNullable(loanCommissionBean.getTransactionType()).isPresent())
				query.setParameter("transactionType", loanCommissionBean.getTransactionType());
			if (Optional.ofNullable(loanCommissionBean.getCommissionType()).isPresent())
				query.setParameter("commissionType", loanCommissionBean.getCommissionType());
			if (Optional.ofNullable(loanCommissionBean.getPaymentMode()).isPresent())
				query.setParameter("paymentMode", loanCommissionBean.getPaymentMode());
			if (Optional.ofNullable(loanCommissionBean.getPaymentType()).isPresent())
				query.setParameter("paymentType", loanCommissionBean.getPaymentType());
			if (Optional.ofNullable(loanCommissionBean.getBankName()).isPresent())
				query.setParameter("bankName", loanCommissionBean.getBankName());
			if (Optional.ofNullable(loanCommissionBean.getIfscCode()).isPresent())
				query.setParameter("ifscCode", loanCommissionBean.getIfscCode());
			if (Optional.ofNullable(loanCommissionBean.getAccountNo()).isPresent())
				query.setParameter("accountNo", loanCommissionBean.getAccountNo());
			if (Optional.ofNullable(loanCommissionBean.getAccountType()).isPresent())
				query.setParameter("accountType", loanCommissionBean.getAccountType());
			if (Optional.ofNullable(loanCommissionBean.getPaybleTo()).isPresent())
				query.setParameter("paybleTo", loanCommissionBean.getPaybleTo());
			if (Optional.ofNullable(loanCommissionBean.getTransactionPercentage()).isPresent())
				query.setParameter("transactionPercentage", loanCommissionBean.getTransactionPercentage());
			if (Optional.ofNullable(loanCommissionBean.getTransactionValue()).isPresent())
				query.setParameter("transactionValue", loanCommissionBean.getTransactionValue());

			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean updateLoanVehicleInvoice(LoanVehicleInvoiceBean loanVehicleInvoiceBean) {
		boolean result = true;
		try {
			String hql = "update LoanVehicleInvoiceEntity le set  le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy"
					+ " ,le.ipAddress =:ipAddress ,le.recordType='U',le.transactionCount=:transactionCount ";

			if (Optional.ofNullable(loanVehicleInvoiceBean.getInvoiceNo()).isPresent())
				hql = hql + " ,le.invoiceNo=:invoiceNo ";
			if (Optional.ofNullable(loanVehicleInvoiceBean.getInvoiceDate()).isPresent())
				hql = hql + " ,le.invoiceDate=:invoiceDate ";
			if (Optional.ofNullable(loanVehicleInvoiceBean.getInvoiceAmount()).isPresent())
				hql = hql + " ,le.invoiceAmount=:invoiceAmount ";
			hql = hql + " WHERE le.loanInvoiceId=:loanInvoiceId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", loanVehicleInvoiceBean.getLastModifiedBy());
			query.setParameter("loanInvoiceId", loanVehicleInvoiceBean.getLoanInvoiceId());
			query.setParameter("ipAddress", loanVehicleInvoiceBean.getIpAddress());
			query.setParameter("transactionCount", loanVehicleInvoiceBean.getTransactionCount());
			if (Optional.ofNullable(loanVehicleInvoiceBean.getInvoiceNo()).isPresent())
				query.setParameter("invoiceNo", loanVehicleInvoiceBean.getInvoiceNo());
			if (Optional.ofNullable(loanVehicleInvoiceBean.getInvoiceDate()).isPresent())
				query.setParameter("invoiceDate", loanVehicleInvoiceBean.getInvoiceDate());
			if (Optional.ofNullable(loanVehicleInvoiceBean.getInvoiceAmount()).isPresent())
				query.setParameter("invoiceAmount", loanVehicleInvoiceBean.getInvoiceAmount());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
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
	public List<LoanAddressBean> getLoanAddressDetails(Long loanId,Long customerId) {
		Session session = (Session) entityManager.getDelegate();
		List<LoanAddressBean> loanAddressBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select  cae.customerAddId as customerAddId,cae.customerId as customerId, cae.addressType as addressType,cae.employerName as employerName,cae.createdDate as createdDate,"
					+ " cae.houseType as houseType, cae.contactNumber as contactNumber, cae.city as city,"
					+ " cae.stateId as stateId,cae.altContactNumber as altContactNumber,cae.emailAddress as emailAddress,"
					+ " cae.address as address, cae.tehsil as tehsil, cae.postalCode as postalCode,cae.createdBy as createdBy,"
					+ " cae.district as district,cae.transactionCount as transactionCount,apl.parameterListValue as stateName "
					+ " from LoanAddressEntity cae left join ApplicationParameterListEntity apl "
					+ " on (apl.parameterListID = cae.stateId) "
					+ " where cae.loanId.loanId = :id  and cae.customerId = :customerId and cae.recordType<>'D' ";

			loanAddressBeanList = session.createQuery(sqlQuery).setParameter("id", loanId).setParameter("customerId", customerId)
					.setResultTransformer(Transformers.aliasToBean(LoanAddressBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return loanAddressBeanList;
	}

	@Override
	public List<LoanReferencesBean> getLoanReferencesDetails(Long id,Long customerId) {
		Session session = (Session) entityManager.getDelegate();
		List<LoanReferencesBean> loanReferencesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select cfde.customerRefId as customerRefId, cfde.referenceName as referenceName, cfde.createdDate as createdDate,"
					+ " cfde.contactNo as contactNo, cfde.altContactNo as altContactNo, cfde.email as email,cfde.customerId as customerId, "
					+ " cfde.address as address,cfde.tehsil as tehsil , cfde.city as city, cfde.postalCode as postalCode, "
					+ " cfde.district as district, cfde.stateId as stateId ,cfde.createdBy as createdBy  "
					+ " , cfde.transactionCount as transactionCount, apl.parameterListValue as stateName "
					+ " from LoanReferencesEntity cfde left join ApplicationParameterListEntity apl "
					+ " on (apl.parameterListID = cfde.stateId)"
					+ " where cfde.loanId.loanId = :id and cfde.customerId = :customerId and cfde.recordType<>'D' ";

			loanReferencesBeanList = session.createQuery(sqlQuery).setParameter("id", id).setParameter("customerId", customerId)
					.setResultTransformer(Transformers.aliasToBean(LoanReferencesBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return loanReferencesBeanList;
	}

	@Override
	public List<LoanMembersBean> getLoanMembersDetails(Long custId,Long customerId) {
		Session session = (Session) entityManager.getDelegate();
		List<LoanMembersBean> loanMembersBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select cmde.membershipDate as membersShipDate, cmde.memberName as memberName,cmde.createdDate as createdDate,"
					+ " cmde.customerMemberId as customerMemberId ,cmde.createdBy as createdBy,cmde.customerId as customerId "
					+ " ,cmde.memberStatus as memberStatus, cmde.transactionCount as transactionCount "
					+ " from LoanMembersEntity cmde " + " where cmde.loanId.loanId = :id and cmde.customerId = :customerId and cmde.recordType<>'D' ";

			loanMembersBeanList = session.createQuery(sqlQuery).setParameter("id", custId).setParameter("customerId", customerId)
					.setResultTransformer(Transformers.aliasToBean(LoanMembersBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return loanMembersBeanList;
	}

	@Override
	public List<LoanCoborrowerTo> getLoanCoborrower(Long Id) {
		Session session = (Session) entityManager.getDelegate();
		List<LoanCoborrowerTo> loanCoborrowerTo = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select lc.relationWithCustomer as relationWithCustomer,lc.transactionCount as transactionCount,lc.customerCoborrowersId as customerCoborrowersId,"
					+ " c.nameTitle as nameTitle, c.firstName as firstName,concat(c.firstName,' ',case when c.lastName is not null then c.lastName else '' end) as fullName ,lc.loanId.loanId as loanId,lc.customerId.customerId as customerId ,"
					+ " c.middleName as middleName, c.lastName as lastName,ca.city as city,ca.postalCode as postalCode ,c.customerType as customerType,le.employerName as employerName,c.professionType as professionType,c.age as age, "
					+ " ca.contactNumber as contactNumber, ca.emailAddress as emailAddress, c.panNo as panNo,c.aadharNo as aadharNo,c.designation as designation,c.occupation as occupation,c.cibilScore as cibilScore  "
					+ " from LoanCoborrowerEntity lc left join lc.customerId c left join CustomerAddressEntity ca on c.customerId=ca.customerId and ca.addressType='Current' and ca.recordType<>'D' "
					+ " left join LoanAddressEntity le on lc.customerId=le.customerId and le.recordType<>'D' and le.addressType='Office' "
					+ " where lc.loanId.loanId = :id and lc.recordType<>'D' group by lc.customerCoborrowersId order by lc.customerCoborrowersId desc";

			loanCoborrowerTo = (List<LoanCoborrowerTo>) session.createQuery(sqlQuery).setParameter("id", Id)
					.setResultTransformer(Transformers.aliasToBean(LoanCoborrowerTo.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return loanCoborrowerTo;
	}

	@Override
	public boolean updateLoanCoborrower(LoanCoborrowerBean loanCoborrowerBean) {
		boolean result = true;
		try {
			String hql = "update LoanCoborrowerEntity le set  le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy"
					+ " ,le.ipAddress =:ipAddress ,le.recordType='U',le.transactionCount=:transactionCount "
					+ " ,le.relationWithCustomer=:relationWithCustomer WHERE le.customerCoborrowersId= "
					+ loanCoborrowerBean.getCustomerCoborrowersId() + " ";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", loanCoborrowerBean.getLastModifiedBy());
			query.setParameter("ipAddress", loanCoborrowerBean.getIpAddress());
			query.setParameter("transactionCount", loanCoborrowerBean.getTransactionCount());
			query.setParameter("relationWithCustomer", loanCoborrowerBean.getRelationWithCustomer());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean deleteLoanCoborrower(LoanCoborrowerBean loanCoborrowerBean) {
		boolean result = true;

		try {
			String hql = "update LoanCoborrowerEntity le set  le.lastModifiedDate=:datetime ,le.lastModifiedBy=:lastModifiedBy"
					+ " ,le.ipAddress =:ipAddress ,le.recordType='D',le.transactionCount=:transactionCount ";
			hql = hql + " WHERE le.customerCoborrowersId=:customerCoborrowersId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("datetime", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("lastModifiedBy", loanCoborrowerBean.getLastModifiedBy());
			query.setParameter("customerCoborrowersId", loanCoborrowerBean.getCustomerCoborrowersId());
			query.setParameter("ipAddress", loanCoborrowerBean.getIpAddress());
			query.setParameter("transactionCount", loanCoborrowerBean.getTransactionCount());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public List<LoanSurityChequesTo> getLoanSurityChequeValidation(PayloadBean payloadBean) {
		Session session = (Session) entityManager.getDelegate();
		List<LoanSurityChequesTo> loanSurityChequesTo = null;
		String sqlQuery = "";

		try {
			sqlQuery = " Select lc.bankListId as bankNameId,lc.ifscCode as ifscCode,"
					+ " lc.accountNo as accountNo, lc.accountType as accountType "
					+ " from CustomerBankDetailEntity lc where lc.primaryAccount='Yes' "
					+ " and lc.customerId.customerId = :customerId and lc.recordType<>'D'";

			loanSurityChequesTo = (List<LoanSurityChequesTo>) session.createQuery(sqlQuery)
					.setParameter("customerId", payloadBean.getUserId())
					.setResultTransformer(Transformers.aliasToBean(LoanSurityChequesTo.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return loanSurityChequesTo;
	}

	@Override
	public boolean primaryAccountExistorNot(Long id) {
		Session session = (Session) entityManager.getDelegate();
		List<LoanSurityChequesTo> loanSurityChequesTo = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select lc.bankListId as bankNameId,lc.ifscCode as ifscCode,"
					+ " lc.accountNo as accountNo, lc.accountType as accountType "
					+ " from CustomerBankDetailEntity lc where lc.primaryAccount='Yes'"
					+ " and lc.customerId.customerId = :id and lc.recordType<>'D'";

			loanSurityChequesTo = (List<LoanSurityChequesTo>) session.createQuery(sqlQuery).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(LoanSurityChequesTo.class)).list();
			if (loanSurityChequesTo.size() != 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public boolean deleteLoanDocument(Long deleteRecordId, Long lastModifiedBy) {
		boolean result = true;
		try {
			String hql = "update ProjectDocumentEntity se set se.transactionCount=2, se.recordType='D' , se.lastModifiedBy=:lastModifiedBy ,se.lastModifiedDate=:lastModifiedDate "
					+ " WHERE se.projectDocumentId=:deleteRecordId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("lastModifiedBy", lastModifiedBy);
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("deleteRecordId", deleteRecordId);
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return result;
	}

	@Override
	public boolean updateCustomer(CustomerBean customerBean) {
		boolean result = true;
		try {
			if (Optional.ofNullable(customerBean.getAadharNo()).isPresent()) {
				customerBean.setAadharNo(customerBean.getAadharNo().replaceAll("-", ""));
			}
			String sqlQuery = "update CustomerEntity ca set  ca.recordType=:recordType , "
					+ "ca.lastModifiedBy=:lastModifiedBy,ca.lastModifiedDate=:lastModifiedDate ,"
					+ " ca.transactionCount=:transactionCount ";
			if (Optional.ofNullable(customerBean.getAge()).isPresent()) {
				sqlQuery += " , ca.age=:age ";
			}
			if (Optional.ofNullable(customerBean.getPanNo()).isPresent()) {
				sqlQuery += " , ca.panNo=:panNo ";
			}
			if (Optional.ofNullable(customerBean.getAadharNo()).isPresent()) {
				sqlQuery += " , ca.aadharNo=:aadharNo ";
			}
			if (Optional.ofNullable(customerBean.getDlNo()).isPresent()) {
				sqlQuery += " , ca.dlNo=:dlNo ";
			}
			if (Optional.ofNullable(customerBean.getPassportNumber()).isPresent()) {
				sqlQuery += " , ca.passportNumber=:passportNumber ";
			}
			if (Optional.ofNullable(customerBean.getVoterId()).isPresent()) {
				sqlQuery += " , ca.voterId=:voterId ";
			}
			sqlQuery += " WHERE ca.customerId=:customerId ";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(sqlQuery);
			query.setParameter("lastModifiedBy", customerBean.getLastModifiedBy());
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("customerId", customerBean.getCustomerId());
			if (Optional.ofNullable(customerBean.getTransactionCount()).isPresent())
				query.setParameter("transactionCount", customerBean.getTransactionCount() + 1);
			else
				query.setParameter("transactionCount", 1L);
			if (Optional.ofNullable(customerBean.getAadharNo()).isPresent())
				query.setParameter("aadharNo", customerBean.getAadharNo());
			if (Optional.ofNullable(customerBean.getPanNo()).isPresent())
				query.setParameter("panNo", customerBean.getPanNo());
			if (!customerBean.isDuplicateFlag())
				query.setParameter("recordType", 'U');
			else
				query.setParameter("recordType", 'D');
			if (Optional.ofNullable(customerBean.getAge()).isPresent())
				query.setParameter("age", customerBean.getAge());
			if (Optional.ofNullable(customerBean.getPassportNumber()).isPresent())
				query.setParameter("passportNumber", customerBean.getPassportNumber());
			if (Optional.ofNullable(customerBean.getVoterId()).isPresent())
				query.setParameter("voterId", customerBean.getVoterId());
			if (Optional.ofNullable(customerBean.getDlNo()).isPresent())
				query.setParameter("dlNo", customerBean.getDlNo());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public List<CustomersTO> fetchcustomerDataForMerging(CustomerBean customerBean) {
		List<CustomersTO> customerTO = null;
		if (Optional.ofNullable(customerBean.getAadharNo()).isPresent()) {
			customerBean.setAadharNo(customerBean.getAadharNo().replaceAll("-", ""));
		}
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select ca.customerAddId as customerAddId, c.customerId as customerId, c.nameTitle as nameTitle, c.firstName as firstName,concat(c.firstName,' ',case when c.lastName is not null then c.lastName else '' end) as fullName , "
				+ " c.middleName as middleName, c.lastName as lastName,ca.city as city,ca.postalCode as postalCode,ca.transactionCount as transactionCount, "
				+ " ca.contactNumber as contactNumber, ca.emailAddress as emailAddress, c.panNo as panNo,c.aadharNo as aadharNo,c.customerType as customerType ,"
				+ " 	c.dlNo as dlNo,c.voterId as voterId,c.passportNumber as passportNumber "
				+ " from  CustomerAddressEntity ca right join  ca.customerId c where "
				+ "  ca.recordType<>'D' and c.recordType<>'D'  and c.status='Active' ";
		if (Optional.ofNullable(customerBean.getCustomerId()).isPresent()) {
			sqlQuery += " and  c.customerId!=" + customerBean.getCustomerId() + "";
		}
		if (Optional.ofNullable(customerBean.getAadharNo()).isPresent()) {
			sqlQuery += " and ( c.aadharNo=" + customerBean.getAadharNo() + "  ";
			if (Optional.ofNullable(customerBean.getPanNo()).isPresent()) {
				sqlQuery += " or ";
			}
		} else {
			sqlQuery += " and ";
		}
		if (Optional.ofNullable(customerBean.getPanNo()).isPresent()) {
			sqlQuery += "  c.panNo='" + customerBean.getPanNo() + "' ";
		}

		if (Optional.ofNullable(customerBean.getAadharNo()).isPresent()) {
			sqlQuery += " ) ";
		}

		sqlQuery += " group by c.customerId";
		customerTO = (List<CustomersTO>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(CustomersTO.class)).list();
		System.out.println(customerTO.toString());
		return customerTO;
	}

	@Override
	public boolean updateLoanAddressDetails(LoanAddressEntity loanAddressEntity) {
		boolean result = true;
		try {
			Session session = (Session) entityManager.getDelegate();
			session.update(loanAddressEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean updateLoanReferencesDetails(LoanReferencesEntity loanReferencesEntity) {
		boolean result = true;
		try {
			Session session = (Session) entityManager.getDelegate();
			session.update(loanReferencesEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean updateLoanMembersDetails(LoanMembersEntity loanMembersEntity) {
		boolean result = true;
		try {
			Session session = (Session) entityManager.getDelegate();
			session.update(loanMembersEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean updateLoanBankDetails(LoanBean loanBean) {
		boolean result = true;
		try {
			String sqlQuery = "update LoanEntity le set  le.recordType='U' ,le.bankName=:bankName ,le.accountNo=:accountNo , "
					+ "le.lastModifiedBy=:lastModifiedBy,le.lastModifiedDate=:lastModifiedDate ,le.ifscCode=:ifscCode ,"
					+ " le.transactionCount=:transactionCount ,le.accountType=:accountType,le.ipAddress=:ipAddress,le.loanProgram=:loanProgram ";
			sqlQuery += " WHERE le.loanId=:loanId ";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(sqlQuery);
			query.setParameter("lastModifiedBy", loanBean.getLastModifiedBy());
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("loanId", loanBean.getLoanId());
			query.setParameter("transactionCount", loanBean.getTransactionCount() + 1);
			query.setParameter("bankName", loanBean.getBankName());
			query.setParameter("accountNo", loanBean.getAccountNo());
			query.setParameter("ifscCode", loanBean.getIfscCode());
			query.setParameter("accountType", loanBean.getAccountType());
			query.setParameter("ipAddress", loanBean.getIpAddress());
			query.setParameter("loanProgram", loanBean.getLoanProgram());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

}
