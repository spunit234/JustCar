package com.edios.csr.dao.impl;

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
import com.edios.csr.bean.AggregatorLoanBean;
import com.edios.csr.bean.AggregatorPaymentReceivedBean;
import com.edios.csr.bean.AggregatorPaymentsBean;
import com.edios.csr.bean.AggregatorRCMovementBean;
import com.edios.csr.bean.AggregatorTasksBean;
import com.edios.csr.bean.AggregatorsBean;
import com.edios.csr.dao.AggregatorDao;
import com.edios.csr.entity.AggregatorLoanEntity;
import com.edios.csr.entity.AggregatorPaymentReceivedEntity;
import com.edios.csr.entity.AggregatorPaymentsEntity;
import com.edios.csr.entity.AggregatorRCMovementEntity;
import com.edios.csr.entity.AggregatorTasksEntity;
import com.edios.csr.entity.AggregatorsEntity;
import com.edios.csr.entity.to.AggregatorTO;
import com.edios.csr.entity.to.VendorDocumentTO;
import com.ibm.icu.text.SimpleDateFormat;

@Repository
@SuppressWarnings("deprecation")
public class AggregatorDaoImpl extends BaseDaoImpl<AggregatorsEntity> implements AggregatorDao {

	@Autowired
	MessageSource messageSource;

	@Override
	public List<AggregatorTO> getManageAggregatorDetails(AggregatorTO aggregatorManage) {
		List<AggregatorTO> aggregatorsBeanList = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateFormat = messageSource.getMessage("dateFormat", null, "", Locale.US);
		if (Optional.ofNullable(aggregatorManage.getCustomerAdharNo()).isPresent()) {
			aggregatorManage.setCustomerAdharNo(aggregatorManage.getCustomerAdharNo().replaceAll("-", ""));
			}
		String sqlQuery = "";
		try {
			StringBuilder whereClause = new StringBuilder();

			if (aggregatorManage.getAggregatorStatus() != null)
				whereClause.append(" AND agge.aggregatorStatus= '").append(aggregatorManage.getAggregatorStatus())
						.append("' ");
			if (aggregatorManage.getAggregarorFromDate() != null)
				whereClause.append(" AND agge.aggregatorDate >= '")
						.append(simpleDateFormat.format(aggregatorManage.getAggregarorFromDate())).append("' ");
			if (aggregatorManage.getAggregarortoDate() != null)
				whereClause.append(" AND agge.aggregatorDate <= '")
						.append(simpleDateFormat.format(aggregatorManage.getAggregarortoDate())).append(" 23:59:59' ");
			if (aggregatorManage.getAssignedStaffId() != null)
				whereClause.append(" AND inqe.assignedStaffId= ").append(aggregatorManage.getAssignedStaffId());
			if (aggregatorManage.getLoanStatus() != null && aggregatorManage.getLoanStatus().equals("disbursed"))
				whereClause.append(" AND loan.loanStatus= ").append(aggregatorManage.getLoanStatusId());
			if (aggregatorManage.getLoanNo() != null && !aggregatorManage.getLoanNo().trim().equals(""))
				whereClause.append(" AND loan.loanNo = '").append(aggregatorManage.getLoanNo()).append("' ");
			if (aggregatorManage.getInquiryNo() != null && !aggregatorManage.getInquiryNo().trim().equals(""))
				whereClause.append(" AND inqe.inquiryNo = '").append(aggregatorManage.getInquiryNo()).append("' ");
			if (aggregatorManage.getFirstName() != null && !(aggregatorManage.getFirstName().equals(""))) {
				whereClause.append(" AND (inqe.firstName like '%").append(aggregatorManage.getFirstName()).append("%'");
				if (aggregatorManage.getLastName() != null && !(aggregatorManage.getLastName().equals("")))
					whereClause.append(" AND inqe.lastName like '%").append(aggregatorManage.getLastName())
							.append("%')");
				else
					whereClause.append(" OR inqe.lastName like '%").append(aggregatorManage.getFirstName())
							.append("%')");
			}
			if (aggregatorManage.getCustomerContNo() != null)
				whereClause.append(" AND inqe.contactNumber= '").append(aggregatorManage.getCustomerContNo())
						.append("' ");
			if (aggregatorManage.getCustomerPanNo() != null)
				whereClause.append(" AND customer.panNo= '").append(aggregatorManage.getCustomerPanNo()).append("' ");
			if (aggregatorManage.getCustomerAdharNo() != null)
				whereClause.append(" AND customer.aadharNo= '").append(aggregatorManage.getCustomerAdharNo())
						.append("' ");
			if (aggregatorManage.getLarBankDate() != null)
				whereClause.append(" AND agge.lARBankDate = '")
						.append(simpleDateFormat.format(aggregatorManage.getLarBankDate())).append("' ");
			if (aggregatorManage.getLarStatus() != null)
				whereClause.append(" AND agge.lARStatus = '").append(aggregatorManage.getLarStatus()).append("' ");
			if (aggregatorManage.getExecutiveName() != null && !aggregatorManage.getExecutiveName().trim().equals(""))
				whereClause.append(" AND agge.executiveName = '").append(aggregatorManage.getExecutiveName())
						.append("' ");

			if (aggregatorManage.getInquiryType() != null)
				whereClause.append(" AND inqe.inquiryType= '").append(aggregatorManage.getInquiryType()).append("' ");
			else if (aggregatorManage.getLoanStatus() != null && aggregatorManage.getLoanStatus().equals("disbursed"))
				whereClause.append(
						" AND inqe.inquiryType in ('Vehicle Loan-Used Car Purchase','Vehicle Loan-New Car','Vehicle Loan-Refinance Car')");
			else
				whereClause.append(
						" AND inqe.inquiryType in ('Vehicle Loan-Used Car Purchase','Vehicle Loan-New Car','Vehicle Loan-Refinance Car','Aggregator')");

			sqlQuery = " Select  (case when inqe.inquiryType='Aggregator' then  inqe.inquiryNo else null end ) as inquiryNo ,"
					+ " (case when inqe.inquiryType='Aggregator' then  ale.loanNo else loan.loanNo end ) as loanNo ,loan.loanId as loanId,"
					+ " inqe.inquiryType as inquiryType,agge.aggregatorId as aggregatorId,agge.aggregatorType as aggregatorType , "
					+ " inqe.inquiryId as inquiryId,CONCAT(inqe.firstName,' ',inqe.lastName) as customerName, inqe.contactNumber as customerContNo, "
					+ " DATE_FORMAT(agge.aggregatorDate,'" + dateFormat
					+ "') as aggregatorDateStr, agge.aggregatorStatus as aggregatorStatus "
					+ " from  AggregatorsEntity agge  right join agge.inquiryId inqe "
					+ " left join VehicelsEntity vehicle on inqe.inquiryId = vehicle.inquiryId "
					+ " left join LoanEntity loan on (loan.inquiryId=inqe.inquiryId and loan.loanStatus='"
					+ aggregatorManage.getLoanStatusId() + "' and loan.recordType<>'D') "
					+ " left join CustomerEntity customer on (customer.customerId=inqe.customerId and customer.recordType<>'D') "
					+ " left join AggregatorLoanEntity ale on ale.aggregatorId=agge.aggregatorId "
					+ " where (agge.recordType<>'D' OR agge.recordType is null) and inqe.inquiryStatus='With Bank' and inqe.recordType<>'D'  "
					+ whereClause + " and vehicle.inquiryId is Not Null order by inqe.inquiryId desc ";

			aggregatorsBeanList = getSession().createQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(AggregatorTO.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aggregatorsBeanList;
	}

	@Override
	public Long addAggregatorsDetails(AbstractEntity aggregatorsEntity) {
		Session session = (Session) entityManager.getDelegate();

		Long id = (Long) session.save(aggregatorsEntity);
		if (id > 0)
			return id;
		return id;
	}

	@Override
	public AggregatorsBean getAggregatorDetailsByInquiryId(Long inquiryId) {
		Session session = (Session) entityManager.getDelegate();
		AggregatorsBean aggregatorsBean = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select "
					+ " agge.aggregatorId as aggregatorId,loan.netDisbursed as netDisbursed,inqe.inquiryType as inquiryType ,agge.aggregatorType as aggregatorType ,"
					+ "   agge.amountReceivedFromBank as amountReceivedFromBank,agge.amountDisbToCustomer as amountDisbToCustomer,"
					+ " agge.pendingAmountToDist as pendingAmountToDist,agge.bankName as bankName ,loan.netDisbursed as netDisbursed , "
					+ " aple.parameterListValue as loanBankName,agge.amountOnHold as amountOnHold,agge.aggregatorDate as aggregatorDate, "
					+ " agge.amountDeductedByRTO as amountDeductedByRTO,agge.transactionCount as transactionCount,"
					+ " agge.larStatus as larStatus, agge.larBankDate as larBankDate, agge.executiveName as executiveName,"
					+ " agge.executivePhone as executivePhone,loan.loanId as loanId,vehicle.vehicleId as vehicleId,aple1.parameterListValue  as customerBankName,"
					+ " insurance.vehicleInsuranceId as vehicleInsuranceId  from   InquiriesEntity inqe "
					+ " left join LoanEntity loan on (loan.inquiryId=inqe.inquiryId AND loan.recordType<>'D') "
					+ " left join AggregatorsEntity agge on (agge.inquiryId=inqe.inquiryId AND agge.recordType<>'D') "
					+ " left join LoanEntity loan on (loan.inquiryId=inqe.inquiryId AND loan.recordType<>'D') "
					+ " left join VehicelsEntity vehicle on (vehicle.inquiryId=inqe.inquiryId AND vehicle.recordType<>'D') "
					+ " left join VehicleInsuranceEntity insurance on (insurance.inquiryId=inqe.inquiryId AND insurance.recordType<>'D') "
					+ " left join ApplicationParameterListEntity aple on (aple.parameterListID=loan.loanBank ) "
					+ " left join CustomerBankDetailEntity ca on (ca.customerId=inqe.customerId  and ca.recordType<>'D' and ca.primaryAccount='Yes')"
					+ " left join CustomerEntity c on (c.customerId=inqe.customerId  and c.recordType<>'D')"
					+ " left join ApplicationParameterListEntity aple1 on (aple1.parameterListID=ca.bankListId ) "
					+ " where inqe.recordType<>'D'  and inqe.inquiryId=:inquiryId group by agge.aggregatorId";

			aggregatorsBean = (AggregatorsBean) session.createQuery(sqlQuery).setParameter("inquiryId", inquiryId)
					.setResultTransformer(Transformers.aliasToBean(AggregatorsBean.class)).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aggregatorsBean;
	}

	@Override
	public AggregatorPaymentsBean getCustomerDetailsForPaymentsByInquiryId(Long inquiryId) {
		Session session = (Session) entityManager.getDelegate();
		AggregatorPaymentsBean aggregatorPaymentsBean = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select  bank.customerBankId as customerbankId,concat(inqe.firstName,' ',case when inqe.lastName is not null then inqe.lastName else '' end) as customerName,"
					+ " inqe.contactNumber as customerContNo,customer.panNo as panNo ,customer.aadharNo as aadharNo ,"
					+ " aple.parameterListValue as customerBankName,bank.accountNo as customerAccNo,"
					+ " bank.ifscCode as customerIFSC, bank.accountType as customerAccType,customer.customerId as customerId "
					+ " from   InquiriesEntity inqe "
					+ " inner join CustomerEntity customer on (customer.customerId=inqe.customerId AND customer.recordType<>'D') "
					+ " left join CustomerBankDetailEntity bank on (customer.customerId=bank.customerId AND bank.primaryAccount='Yes' AND customer.recordType<>'D') "
					+ " left join ApplicationParameterListEntity aple on (aple.parameterListID=bank.bankListId) "
					+ " where bank.recordType<>'D'  and inqe.inquiryId=:inquiryId ";

			aggregatorPaymentsBean = (AggregatorPaymentsBean) session.createQuery(sqlQuery)
					.setParameter("inquiryId", inquiryId)
					.setResultTransformer(Transformers.aliasToBean(AggregatorPaymentsBean.class)).getSingleResult();
		} catch (NoResultException e) {
			System.out.println("No Result");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aggregatorPaymentsBean;
	}

	@Override
	public AbstractEntity fetchAggregatorsDetailsByID(String entityName, Long aggregatorId) {
		AbstractEntity transactionData = null;
		try {
			Session session = (Session) entityManager.getDelegate();

			if (entityName.equals("AggregatorsEntity")) {
				transactionData = session.get(AggregatorsEntity.class, aggregatorId);
			} else if (entityName.equals("AggregatorPaymentsEntity")) {
				transactionData = session.get(AggregatorPaymentsEntity.class, aggregatorId);
			} else if (entityName.equals("AggregatorRCMovementEntity")) {
				transactionData = session.get(AggregatorRCMovementEntity.class, aggregatorId);
			} else if (entityName.equals("AggregatorTasksEntity")) {
				transactionData = session.get(AggregatorTasksEntity.class, aggregatorId);
			}
//			else if (entityName.equals("InsuranceNomineesEntity")) {
//				transactionData = session.get(InsuranceNomineesEntity.class, vehicleInsuranceId);
//			}

		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

	@Override
	public List<AggregatorPaymentsBean> getAggregatorPaymentsDetails(Long aggregatorId) {
		Session session = (Session) entityManager.getDelegate();
		List<AggregatorPaymentsBean> aggregatorPaymentsBeanList = null;
		String dateFormat = messageSource.getMessage("dateFormat", null, "", Locale.US);
		String sqlQuery = "";
		try {
			sqlQuery = " Select  payment.aggregatorPaymentId as aggregatorPaymentId,payment.transactionCount as transactionCount,"
					+ " DATE_FORMAT(payment.transactionDate,'" + dateFormat
					+ "') as transactionDateStr,payment.transactionNo as transactionNo,sum(payment.transactionAmount) as totalPaidAmount, "
					+ " payment.applicationNo as applicationNo, payment.paymentType as paymentType, payment.companyBankId as companyBankId,aple.parameterListValue as customerBankName,"
//					+ " bank.accountNo as customerAccNo,bank.ifscCode as customerIFSC, bank.accountType as customerAccType,"
					+ " payment.transactionAmount as transactionAmount,payment.notes as notes,payment.transactionMode as transactionMode  "
					+ " from   AggregatorPaymentsEntity payment "
//					+ " left join CustomerBankDetailEntity bank on (bank.customerBankId=payment.customerbankId AND bank.primaryAccount='Yes' AND bank.recordType<>'D') "
					+ " left join ApplicationParameterListEntity aple on (aple.parameterListID=payment.companyBankId AND aple.recordType<>'D') "
					+ " where payment.recordType<>'D'  and payment.aggregatorId.aggregatorId=:aggregatorId group by payment.aggregatorPaymentId "
					+ " order by payment.aggregatorPaymentId  desc";

			aggregatorPaymentsBeanList = (List<AggregatorPaymentsBean>) session.createQuery(sqlQuery)
					.setParameter("aggregatorId", aggregatorId)
					.setResultTransformer(Transformers.aliasToBean(AggregatorPaymentsBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aggregatorPaymentsBeanList;

	}

	@Override
	public List<AggregatorRCMovementBean> getAggregatorRCMovementDetails(Long aggregatorId) {
		Session session = (Session) entityManager.getDelegate();
		List<AggregatorRCMovementBean> aggregatorRCMovementBeanList = null;
		String dateFormat = messageSource.getMessage("dateFormat", null, "", Locale.US);
		String sqlQuery = "";
		try {
			sqlQuery = " Select  rcMov.aggregatorRCMovementId as aggregatorRCMovementId,rcMov.transactionCount as transactionCount,"
					+ " DATE_FORMAT(rcMov.rcMovementDate,'" + dateFormat
					+ "') as rcMovementDateStr,rcMov.rcMovementName as rcMovementName, "
					+ " rcMov.dispatchNos as dispatchNos, rcMov.dispatchRemarks as dispatchRemarks, rcMov.rcMovementNotes as rcMovementNotes "
					+ " from   AggregatorRCMovementEntity rcMov "
					+ " where rcMov.recordType<>'D'  and rcMov.aggregatorId.aggregatorId=:aggregatorId ";

			aggregatorRCMovementBeanList = (List<AggregatorRCMovementBean>) session.createQuery(sqlQuery)
					.setParameter("aggregatorId", aggregatorId)
					.setResultTransformer(Transformers.aliasToBean(AggregatorRCMovementBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aggregatorRCMovementBeanList;
	}

	@Override
	public List<AggregatorTasksBean> getStaffDetailsForRCTasks() {
		Session session = (Session) entityManager.getDelegate();
		List<AggregatorTasksBean> aggregatorTasksBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select  staff.staffId as staffId,CONCAT(staff.firstName,' ',staff.lastName) as staffName "
					+ " from   StaffEntity staff " + " where staff.staffType='RTO Agent' AND staff.recordType<>'D'   ";

			aggregatorTasksBeanList = (List<AggregatorTasksBean>) session.createQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(AggregatorTasksBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aggregatorTasksBeanList;
	}

	@Override
	public List<AggregatorTasksBean> getAggregatorTasksDetails(Long aggregatorId) {
		Session session = (Session) entityManager.getDelegate();
		List<AggregatorTasksBean> aggregatorTasksBeanList = null;
		String dateFormat = messageSource.getMessage("dateFormat", null, "", Locale.US);
		String sqlQuery = "";
		try {
			sqlQuery = " Select  tasks.aggregatorTaskId as aggregatorTaskId,tasks.transactionCount as transactionCount,"
					+ " DATE_FORMAT(tasks.aggregatorTaskDate,'" + dateFormat
					+ "') as aggregatorTaskDateStr,tasks.aggregatorTaskCharges as aggregatorTaskCharges,"
					+ " tasks.aggregatorTaskStatus as aggregatorTaskStatus, tasks.notes as notes, "
					+ " tasks.aggregatorTaskNameId as aggregatorTaskNameId,aple.parameterListValue as aggregatorTaskName,"
					+ " tasks.staffId as staffId,CONCAT(staff.firstName,' ',staff.lastName) as staffName "
					+ " from   AggregatorTasksEntity tasks "
					+ " left join ApplicationParameterListEntity aple on (aple.parameterListID=tasks.aggregatorTaskNameId AND aple.recordType<>'D') "
					+ " left join StaffEntity staff on (staff.staffId=tasks.staffId AND staff.recordType<>'D') "
					+ " where tasks.recordType<>'D'  and tasks.aggregatorId.aggregatorId=:aggregatorId ";

			aggregatorTasksBeanList = (List<AggregatorTasksBean>) session.createQuery(sqlQuery)
					.setParameter("aggregatorId", aggregatorId)
					.setResultTransformer(Transformers.aliasToBean(AggregatorTasksBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aggregatorTasksBeanList;
	}

	@Override
	public boolean updateAggregatorDocumentEntity(Long aggregatorDocumentId, Long storageId, String fileName,
			Integer lastModifiedBy) {

		boolean result = false;
		String hql = "update AggregatorDocumentsEntity vendActDocEnt set vendActDocEnt.storageLocation.locationId=:storageId "
				+ " , vendActDocEnt.fileName=:fileName "
				+ ",vendActDocEnt.lastModifiedBy=:lastModifiedBy,vendActDocEnt.lastModifiedDate=:lastModifiedDate,"
				+ " vendActDocEnt.recordType='U' WHERE vendActDocEnt.aggregatorDocumentId=:aggregatorDocumentId";
		Session session = (Session) entityManager.getDelegate();
		Query query = session.createQuery(hql);
		query.setParameter("storageId", storageId);
		query.setParameter("fileName", fileName);
		query.setParameter("aggregatorDocumentId", aggregatorDocumentId);
		query.setParameter("lastModifiedBy", Long.parseLong(lastModifiedBy.toString()));
		query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
		query.executeUpdate();
		result = true;
		return result;

	}

	@Override
	public boolean deleteAggregatorDocument(Long deleteRecordId, Long lastModifiedBy) {
		boolean result = true;
		try {
			String hql = "update AggregatorDocumentsEntity se set se.transactionCount=2, se.recordType='D' , se.lastModifiedBy=:lastModifiedBy ,se.lastModifiedDate=:lastModifiedDate "
					+ " WHERE se.aggregatorDocumentId=:deleteRecordId";
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
	public List<VendorDocumentTO> fetchAggregatorDocumentDetails(Long aggregatorId) {
		Session session = (Session) entityManager.getDelegate();
		List<VendorDocumentTO> uploadApplicantCvToListt = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		StringBuilder sqlString = new StringBuilder(
				" select vade.aggregatorDocumentId as aggregatorDocumentId,vade.documentName as documentName,DATE_FORMAT(vade.createdDate,'"
						+ dateTimeFormat + "') As uploadDate,"
						+ "vade.documentNumber as documentNumber,aplv.parameterListValue as documentType, vade.documentDesc as documentDesc,vade.transactionCount as transactionCount "
						+ " ,vade.fileName as fileName, vade.storageLocation.locationId as viewDocument"
						+ " from AggregatorDocumentsEntity vade left join vade.documentTypeListId aplv"
						+ " where vade.aggregatorId=:aggregatorId and vade.recordType<>'D' order by vade.aggregatorDocumentId desc");

		uploadApplicantCvToListt = session.createQuery(sqlString.toString()).setParameter("aggregatorId", aggregatorId)
				.setResultTransformer(Transformers.aliasToBean(VendorDocumentTO.class)).list();
		return uploadApplicantCvToListt;
	}

	@Override
	public boolean addAggregatorLoan(AggregatorLoanEntity aggregatorLoanEntity) {
		boolean result = true;
		try {
			entityManager.persist(aggregatorLoanEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public AggregatorLoanBean getAggregatorLoanDetails(Long aggregatorId) {
		Session session = (Session) entityManager.getDelegate();
		AggregatorLoanBean aggregatorLoanBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select  ale.aggregatorLoanId as aggregatorLoanId,ale.aggregatorId as aggregatorId,ale.transactionCount as transactionCount,"
					+ " ale.loanNo as loanNo,ale.loanClass as loanClass,ale.loanType as loanType,ale.netDisbursed as netDisbursed,"
					+ " ale.loanStartDate as loanStartDate, ale.loanEndDate as loanEndDate,ale.loanDuration as loanDuration,ale.loanAmount as loanAmount, "
					+ " ale.loanInstallment as loanInstallment,aple.parameterListValue as loanClassName,ale.loanRoInterest as loanRoInterest,"
					+ " ale.advanceAmount as advanceAmount,ale.processingFee as processingFee,ale.amountDisbursedDate as amountDisbursedDate,"
					+ "  ale.approvedAmount as approvedAmount, ale.loanBank as loanBank, ale.createdBy as createdBy,ale.createdDate as createdDate "
					+ " from   AggregatorLoanEntity ale "
					+ " left join ApplicationParameterListEntity aple on (aple.parameterListID=ale.loanClass AND aple.recordType<>'D') "
					+ " where ale.recordType<>'D'  and ale.aggregatorId=:aggregatorId ";
			aggregatorLoanBeanList = (AggregatorLoanBean) session.createQuery(sqlQuery)
					.setParameter("aggregatorId", aggregatorId)
					.setResultTransformer(Transformers.aliasToBean(AggregatorLoanBean.class)).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aggregatorLoanBeanList;
	}

	@Override
	public boolean updateAggregatorLoan(AggregatorLoanEntity aggregatorLoanEntity) {
		boolean result = true;
		try {
			Session session = (Session) entityManager.getDelegate();
			session.update(aggregatorLoanEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean updateAllRCTask(AggregatorTasksBean aggregatorTasksBean) {
		boolean result = true;
		try {
			String hql = "update AggregatorTasksEntity se set se.ipAddress=:ipAddress , "
					+ " se.recordType='U' , se.lastModifiedBy=:lastModifiedBy ,se.lastModifiedDate=:lastModifiedDate,se.aggregatorTaskStatus='Complete' "
					+ " WHERE se.aggregatorId.aggregatorId=:aggregatorId and se.recordType<>'D' ";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("lastModifiedBy", aggregatorTasksBean.getLastModifiedBy());
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("aggregatorId", aggregatorTasksBean.getAggregatorId().getAggregatorId());
			query.setParameter("ipAddress", aggregatorTasksBean.getIpAddress());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return result;
	}

	@Override
	public boolean addAggregatorPaymentReceived(AggregatorPaymentReceivedEntity aggregatorPaymentReceivedEntity) {
		boolean result = true;
		try {
			entityManager.persist(aggregatorPaymentReceivedEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public List<AggregatorPaymentReceivedBean> getAggregatorPaymentReceivedDetails(Long aggregatorId) {
		Session session = (Session) entityManager.getDelegate();
		List<AggregatorPaymentReceivedBean> aggregatorPaymentReceivedBean = null;
		String sqlQuery = "";
		String dateFormat = messageSource.getMessage("dateFormat", null, "", Locale.US);
		try {
			sqlQuery = " Select  apre.aggregatorPaymentReceivedId as aggregatorPaymentReceivedId,apre.transactionCount as transactionCount,"
					+ " apre.aggregatorId as aggregatorId, apre.aggregatorPaymentReceivedDate as aggregatorPaymentReceivedDate ,"
					+ " apre.receivedAmount as receivedAmount, apre.notes as notes,aple.parameterListValue as balajiBankName,sum(apre.receivedAmount) as totalAmount ,"
					+ " apre.companyBankId as companyBankId,apre.createdBy as createdBy,"
					+ " apre.createdDate as createdDate , DATE_FORMAT(apre.aggregatorPaymentReceivedDate,'"
					+ dateFormat + "') as simpleFormatDate "
					+ " from   AggregatorPaymentReceivedEntity apre left join ApplicationParameterListEntity aple on (aple.parameterListID=apre.companyBankId )  "
					+ " where apre.recordType<>'D'  and apre.aggregatorId=:aggregatorId group by apre.aggregatorPaymentReceivedId order by apre.aggregatorPaymentReceivedId desc";

			aggregatorPaymentReceivedBean = (List<AggregatorPaymentReceivedBean>) session.createQuery(sqlQuery)
					.setParameter("aggregatorId", aggregatorId)
					.setResultTransformer(Transformers.aliasToBean(AggregatorPaymentReceivedBean.class))
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aggregatorPaymentReceivedBean;
	}

	@Override
	public boolean updateOrDeleteAggregatorPaymentReceived(
			AggregatorPaymentReceivedEntity aggregatorPaymentReceivedEntity) {
		boolean result = true;
		try {
			Session session = (Session) entityManager.getDelegate();
			session.update(aggregatorPaymentReceivedEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

}