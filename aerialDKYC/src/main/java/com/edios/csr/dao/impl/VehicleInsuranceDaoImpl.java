package com.edios.csr.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.edios.csr.bean.InsuranceCoversBean;
import com.edios.csr.bean.InsuranceNomineesBean;
import com.edios.csr.bean.InsurancePaymentsBean;
import com.edios.csr.dao.VehicleInsuranceDao;
import com.edios.csr.entity.InsuranceNewCoversEntity;
import com.edios.csr.entity.InsuranceNomineesEntity;
import com.edios.csr.entity.InsurancePRVCoversEntity;
import com.edios.csr.entity.InsurancePaymentsEntity;
import com.edios.csr.entity.VehicleInsuranceEntity;
import com.edios.csr.entity.to.VehicleInsuranceManageTO;
import com.edios.csr.entity.to.VendorDocumentTO;
import com.ibm.icu.text.SimpleDateFormat;

@Repository
@SuppressWarnings("deprecation")
public class VehicleInsuranceDaoImpl extends BaseDaoImpl<VehicleInsuranceEntity> implements VehicleInsuranceDao {

	@Autowired
	MessageSource messageSource;

	@Override
	public Long addVehicleInsuranceDetails(AbstractEntity vehicleInsuranceEntity) {
		Session session = (Session) entityManager.getDelegate();

		Long id = (Long) session.save(vehicleInsuranceEntity);
		if (id > 0)
			return id;
		return id;
	}

	@Override
	public List<VehicleInsuranceManageTO> getManageInsuranceDetails(VehicleInsuranceManageTO vehicleInsuranceManage) {
		Session session = (Session) entityManager.getDelegate();
		List<VehicleInsuranceManageTO> vehicleInsuranceManageTOList = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String sqlQuery = "";
		try {
			StringBuilder whereClause = new StringBuilder();

			if (vehicleInsuranceManage.getInsuranceStatus() != null)
				whereClause.append(" AND vehInsurance.insuranceStatus= '")
						.append(vehicleInsuranceManage.getInsuranceStatus()).append("' ");
			if (vehicleInsuranceManage.getInsuranceSource() != null)
				whereClause.append(" AND vehInsurance.insuranceSource= '")
						.append(vehicleInsuranceManage.getInsuranceSource()).append("' ");
			if (vehicleInsuranceManage.getInsuranceCompanyId() != null)
				whereClause.append(" AND vehInsurance.insuranceCompanyId= ")
						.append(vehicleInsuranceManage.getInsuranceCompanyId());
			if (vehicleInsuranceManage.getPolicyNo() != null)
				whereClause.append(" AND vehInsurance.policyNo= '").append(vehicleInsuranceManage.getPolicyNo())
						.append("' ");
			;
			if (vehicleInsuranceManage.getInsurancePaymentPaidBy() != null)
				whereClause.append(" AND vehInsurance.insurancePaymentPaidBy= '")
						.append(vehicleInsuranceManage.getInsurancePaymentPaidBy()).append("' ");
			if (vehicleInsuranceManage.getInquiryNo() != null)
				whereClause.append(" AND inqe.inquiryNo = '").append(vehicleInsuranceManage.getInquiryNo())
						.append("' ");
			if (vehicleInsuranceManage.getInquiryType() != null)
				whereClause.append(" AND inqe.inquiryType= '").append(vehicleInsuranceManage.getInquiryType())
						.append("' ");
			if (vehicleInsuranceManage.getFirstName() != null && !(vehicleInsuranceManage.getFirstName().equals(""))) {
				whereClause.append(" AND (inqe.firstName like '%").append(vehicleInsuranceManage.getFirstName()).append("%'");
				if (vehicleInsuranceManage.getLastName() != null && !(vehicleInsuranceManage.getLastName().equals("")))
					whereClause.append(" AND inqe.lastName like '%").append(vehicleInsuranceManage.getLastName()).append("%')");
				else
					whereClause.append(" OR inqe.lastName like '%").append(vehicleInsuranceManage.getFirstName()).append("%')");
			}
			if (vehicleInsuranceManage.getContactNumber() != null)
				whereClause.append(" AND inqe.contactNumber= '").append(vehicleInsuranceManage.getContactNumber())
						.append("' ");
			if (vehicleInsuranceManage.getVehicleChassisNo() != null)
				whereClause.append(" AND vehicle.vehicleChassisNo= '")
						.append(vehicleInsuranceManage.getVehicleChassisNo()).append("' ");
			if (vehicleInsuranceManage.getVehicleEngineNo() != null)
				whereClause.append(" AND vehicle.vehicleEngineNo= '")
						.append(vehicleInsuranceManage.getVehicleEngineNo()).append("' ");
			if (vehicleInsuranceManage.getPolicyIssueFromDate() != null)
				whereClause.append(" AND vehInsurance.policyIssueDate >= '")
						.append(simpleDateFormat.format(vehicleInsuranceManage.getPolicyIssueFromDate())).append("' ");
			if (vehicleInsuranceManage.getPolicyIssueToDate() != null)
				whereClause.append(" AND vehInsurance.policyIssueDate <= '")
						.append(simpleDateFormat.format(vehicleInsuranceManage.getPolicyIssueToDate())).append("' ");

			sqlQuery = " Select  inqe.inquiryNo as inquiryNo, inqe.inquiryType as inquiryType, "
					+ " CONCAT(inqe.firstName,' ',inqe.lastName) as customerName, vehicle.vehicleId as vehicleId,inqe.contactNumber as contactNumber, "
					+ " vehicle.vehicleEngineNo as vehicleEngineNo, vehicle.vehicleChassisNo as vehicleChassisNo,"
					+ " vehInsurance.vehicleInsuranceId as vehicleInsuranceId ,vehInsurance.policyNo as policyNo "
					+ " ,vehInsurance.insuranceSource as insuranceSource,vehInsurance.insuranceStatus as insuranceStatus " 
					+ " from   VehicelsEntity vehicle "
					+ " left join InquiriesEntity inqe on (vehicle.inquiryId=inqe.inquiryId AND inqe.recordType<>'D') "
//					+ " left join CustomerEntity customer on (customer.customerId=inqe.customerId AND customer.recordType<>'D') "
					+ " left join VehicleInsuranceEntity vehInsurance on (vehInsurance.vehicleId=vehicle.vehicleId AND vehInsurance.recordType<>'D') "
					+ " where vehicle.recordType<>'D' and  inqe.inquiryType='Insurance'  and inqe.inquiryStatus='With Bank' " + whereClause  +" order by inqe.inquiryId desc ";

			vehicleInsuranceManageTOList = session.createQuery(sqlQuery)
//					.setParameter("id", custId)
					.setResultTransformer(Transformers.aliasToBean(VehicleInsuranceManageTO.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vehicleInsuranceManageTOList;
	}

	@Override
	public List<VehicleInsuranceManageTO> getExistingInsuranceDetails(PayloadBean payloadbean) {
		Session session = (Session) entityManager.getDelegate();
		List<VehicleInsuranceManageTO> vehicleInsuranceBeanList = null;
		String sqlQuery = "";
		String whereClause = "";
		try {

			if (payloadbean.getSearchParameter().equals("Contact No"))
				whereClause = " AND inqe.contactNumber = '" + payloadbean.getCustomParameter() + "' ";
			else if (payloadbean.getSearchParameter().equals("Customer Name"))
				whereClause = " AND inqe.firstName = '" + payloadbean.getCustomParameter() + "' ";
			else if (payloadbean.getSearchParameter().equals("Vehicle Engine No"))
				whereClause = " AND vehicle.vehicleEngineNo = '" + payloadbean.getCustomParameter() + "' ";
			else if (payloadbean.getSearchParameter().equals("Vehicle Chassis No"))
				whereClause = " AND vehicle.vehicleChassisNo = '" + payloadbean.getCustomParameter() + "' ";
			else if (payloadbean.getSearchParameter().equals("Vehicle Registration No"))
				whereClause = " AND vehicle.vehicleRegNo = '" + payloadbean.getCustomParameter() + "' ";
			sqlQuery = " Select  "
					+ " CONCAT(inqe.firstName,' ',inqe.lastName) as customerName,inqe.contactNumber as contactNumber, "
					+ " inqe.customerId as customerId,customerAdd.customerAddId as customerAddId,inqe.inquiryId as inquiryId, vehicle.vehicleId as vehicleId, "
					+ " vehicle.vehicleEngineNo as vehicleEngineNo, vehicle.vehicleChassisNo as vehicleChassisNo,vehInsurance.companyBankId as companyBankId,"
					+ " vehInsurance.vehicleInsuranceId as vehicleInsuranceId ,vehInsurance.policyNo as policyNo,vehInsurance.modeOfDispatch as modeOfDispatch, "
					+ " vehInsurance.insuranceSource as insuranceSource,vehInsurance.insuranceStatus as insuranceStatus ,vehInsurance.modeOfPayment as modeOfPayment,"
					+ " vehInsurance.insuranceCompanyId as insuranceCompanyId ,vehInsurance.insurancePaymentPaidBy as insurancePaymentPaidBy ,"
					+ " vehInsurance.insurancePaymentPaidDate as insurancePaymentPaidDate ,vehInsurance.insuranceDate as insuranceDate ,"
					+ " vehInsurance.policyIssueDate as policyIssueDate ,vehInsurance.insuranceAmount as insuranceAmount,"
					+ " vehInsurance.dispatchNo as dispatchNo ,vehInsurance.dispatchDate as dispatchDate, "
					+ " vehInsurance.notes as notes, aple.parameterListValue as insuranceCompanyValue, vehInsurance.transactionCount as transactionCount  "
					+ " from    VehicleInsuranceEntity vehInsurance"
					+ " left join InquiriesEntity inqe on (vehInsurance.inquiryId=inqe.inquiryId AND inqe.recordType<>'D') "
					+ " left join VehicelsEntity vehicle on (vehInsurance.vehicleId=vehicle.vehicleId AND vehicle.recordType<>'D') "
					+ " left join ApplicationParameterListEntity aple on (aple.parameterListID=vehInsurance.insuranceCompanyId AND aple.recordType<>'D') "
					+ " left join CustomerAddressEntity customerAdd on (customerAdd.customerId=inqe.customerId AND customerAdd.addressType='Current' AND vehicle.recordType<>'D') "
					+ " where vehInsurance.recordType<>'D'  " + whereClause;

			vehicleInsuranceBeanList = session.createQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(VehicleInsuranceManageTO.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vehicleInsuranceBeanList;
	}

	@Override
	public VehicleInsuranceManageTO getInsuranceDetailsByVehicleId(Long vehicleId) {
		Session session = (Session) entityManager.getDelegate();
		VehicleInsuranceManageTO vehicleInsuranceBean = null;
		List<VehicleInsuranceManageTO> vehicleInsuranceBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select  vehicle.previousOwner as previousOwner ,CONCAT(inqe.firstName,' ',inqe.lastName) as customerName ,vehInsurance.ignoreFlag as ignoreFlag ,"
					+ " vehInsurance.insCustomerName as insCustomerName,vehInsurance.contactNumber as contactNumber,vehicle.make as makeId, "
					+ " inqe.customerId as customerId,inqe.inquiryId as inquiryId, vehicle.vehicleId as vehicleId, "
					+ " vehInsurance.vehicleEngineNo as vehicleEngineNo, vehInsurance.vehicleChassisNo as vehicleChassisNo,"
					+ " vehInsurance.vehicleInsuranceId as vehicleInsuranceId ,vehInsurance.policyNo as policyNo,vehInsurance.modeOfPayment as modeOfPayment, "
					+ " vehInsurance.insuranceSource as insuranceSource,vehInsurance.insuranceStatus as insuranceStatus ,"
					+ " vehInsurance.insuranceCompanyId as insuranceCompanyId ,vehInsurance.insurancePaymentPaidBy as insurancePaymentPaidBy ,"
					+ " vehInsurance.insurancePaymentPaidDate as insurancePaymentPaidDate ,vehInsurance.insuranceDate as insuranceDate ,"
					+ " vehInsurance.policyIssueDate as policyIssueDate ,vehInsurance.insuranceAmount as insuranceAmount,vehInsurance.modeOfDispatch as modeOfDispatch,"
					+ " vehInsurance.dispatchNo as dispatchNo ,vehInsurance.dispatchDate as dispatchDate,vehInsurance.companyBankId as companyBankId, "
					+ " vehInsurance.notes as notes, aple.parameterListValue as insuranceCompanyValue, vehInsurance.transactionCount as transactionCount  "
					+ " from   VehicelsEntity vehicle "
					+ " left join InquiriesEntity inqe on (vehicle.inquiryId=inqe.inquiryId AND inqe.recordType<>'D') "
					+ " left join  CustomerAddressEntity customerAdd on (customerAdd.customerId=inqe.customerId AND customerAdd.addressType in "
					+ " ('Current') AND customerAdd.recordType<>'D')  "
					+ " left join VehicleInsuranceEntity vehInsurance on (vehInsurance.vehicleId=vehicle.vehicleId AND vehInsurance.recordType<>'D') "
					+ " left join ApplicationParameterListEntity aple on (aple.parameterListID=vehInsurance.insuranceCompanyId AND aple.recordType<>'D') "
					+ " where vehicle.recordType<>'D'  and vehicle.vehicleId=:vehicleId ";

			vehicleInsuranceBeanList = (List<VehicleInsuranceManageTO>) session.createQuery(sqlQuery)
					.setParameter("vehicleId", vehicleId)
					.setResultTransformer(Transformers.aliasToBean(VehicleInsuranceManageTO.class)).list();
			
			if(vehicleInsuranceBeanList.size()!=0) {
				vehicleInsuranceBean=vehicleInsuranceBeanList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vehicleInsuranceBean;
	}

	@Override
	public AbstractEntity fetchInsuranceDetailsByID(String entityName, Long vehicleInsuranceId) {
		AbstractEntity transactionData = null;
		try {
			Session session = (Session) entityManager.getDelegate();

			if (entityName.equals("VehicleInsuranceEntity")) {
				transactionData = session.get(VehicleInsuranceEntity.class, vehicleInsuranceId);
			} else if (entityName.equals("InsurancePaymentsEntity")) {
				transactionData = session.get(InsurancePaymentsEntity.class, vehicleInsuranceId);
			} else if (entityName.equals("InsuranceNewCoversEntity")) {
				transactionData = session.get(InsuranceNewCoversEntity.class, vehicleInsuranceId);
			} else if (entityName.equals("InsurancePRVCoversEntity")) {
				transactionData = session.get(InsurancePRVCoversEntity.class, vehicleInsuranceId);
			} else if (entityName.equals("InsuranceNomineesEntity")) {
				transactionData = session.get(InsuranceNomineesEntity.class, vehicleInsuranceId);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

	@Override
	public List<AbstractEntity> fetchInsuranceDetailsByInsuranceID(String entityName, Long vehicleInsuranceId) {
		List transactionDataList = null;
		String sqlQuery;
		try {
			sqlQuery = " from " + entityName + " en where en.vehicleInsuranceId.vehicleInsuranceId=:vehicleInsuranceId and en.recordType<>'D' ";

				transactionDataList = getSession().createQuery(sqlQuery)
						.setParameter("vehicleInsuranceId", vehicleInsuranceId).list();

		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionDataList;
	}

	@Override
	public List<InsurancePaymentsBean> getInsurancePaymentDetails(Long vehicleInsuranceId) {
		Session session = (Session) entityManager.getDelegate();
		List<InsurancePaymentsBean> insurancePaymentsBeanList = null;
		String dateFormat = messageSource.getMessage("dateFormat", null, "", Locale.US);
		String sqlQuery = "";
		try {
			sqlQuery = " Select payment.insurancePaymentId as insurancePaymentId,DATE_FORMAT( payment.insurancePaymentDate,'"+dateFormat+"') as insurancePaymentDateStr,payment.companyBankId as companyBankId,"
					+ " payment.insurancePaymentAmount as insurancePaymentAmount, payment.insuranceModePayment as insuranceModePayment, payment.transactionCount as transactionCount "
					+ " from   InsurancePaymentsEntity payment "
					+ " where payment.recordType<>'D' AND payment.vehicleInsuranceId.vehicleInsuranceId=:vehicleInsuranceId ";

			insurancePaymentsBeanList = session.createQuery(sqlQuery)
					.setParameter("vehicleInsuranceId", vehicleInsuranceId)
					.setResultTransformer(Transformers.aliasToBean(InsurancePaymentsBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return insurancePaymentsBeanList;
	}

	@Override
	public List<InsuranceCoversBean> getInsuranceNewCoversDetails(Long id) {
		Session session = (Session) entityManager.getDelegate();
		List<InsuranceCoversBean> insuranceNewCoversBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select newCover.insuranceNewCoverId as insuranceNewCoverId, newCover.renewalType as renewalType,newCover.insuranceCoverType as insuranceCoverType,"
					+ " newCover.insurancePremiumAmount as insurancePremiumAmount, newCover.sumInsuredAmount as sumInsuredAmount,newCover.inspectionReportNo as inspectionReportNo,"
					+ " newCover.numberOfYears as numberOfYears,newCover.inspectionDoneBy as inspectionDoneBy,aple.parameterListValue as addOnCoverageName,vi.insuranceSource as insuranceSource ,"
					+ "newCover.addOnCoverageId as addOnCoverageId,newCover.insuranceValidFrom as insuranceValidFrom,newCover.insuranceValidTo as insuranceValidTo,"
					+ " newCover.inspectionDate as inspectionDate,newCover.transactionCount as transactionCount,newCover.noClaimBonus as noClaimBonus,aple2.parameterListValue as insuranceCompany "
					+ " from   InsuranceNewCoversEntity newCover left join newCover.vehicleInsuranceId vi "
					+ " left join ApplicationParameterListEntity aple on (aple.parameterListID=newCover.addOnCoverageId AND aple.recordType<>'D') "
					+ " left join ApplicationParameterListEntity aple2 on (aple2.parameterListID=vi.insuranceCompanyId AND aple2.recordType<>'D') "
					+ " where newCover.recordType<>'D' AND newCover.vehicleInsuranceId.vehicleInsuranceId=:vehicleInsuranceId ";

			insuranceNewCoversBeanList = session.createQuery(sqlQuery).setParameter("vehicleInsuranceId", id)
					.setResultTransformer(Transformers.aliasToBean(InsuranceCoversBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return insuranceNewCoversBeanList;
	}

	@Override
	public List<InsuranceCoversBean> getInsurancePRVCoverDetails(Long id) {
		Session session = (Session) entityManager.getDelegate();
		List<InsuranceCoversBean> insuranceNewCoversBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = " Select prvCover.insurancePRVCoverId as insurancePRVCoverId, prvCover.renewalType as renewalType,prvCover.insuranceCoverType as insuranceCoverType,"
					+ " prvCover.insurancePremiumAmount as insurancePremiumAmount, prvCover.sumInsuredAmount as sumInsuredAmount,"
					+ " prvCover.numberOfYears as numberOfYears,prvCover.inspectionDoneBy as inspectionDoneBy,aple.parameterListValue as addOnCoverageName,"
					+ " prvCover.addOnCoverageId as addOnCoverageId,prvCover.insuranceValidFrom as insuranceValidFrom,prvCover.insuranceValidTo as insuranceValidTo,"
					+ " prvCover.inspectionDate as inspectionDate,prvCover.transactionCount as transactionCount,prvCover.noClaimBonus as noClaimBonus,"
					+ " prvCover.policyNo as policyNo, prvCover.insuranceCompanyId as insuranceCompanyId, customerAdd.contactNumber as contactNumber,"
					+ " CONCAT(customer.firstName,' ',customer.lastName) as customerName,customerAdd.customerAddId as customerAddressId,prvCover.inspectionReportNo as inspectionReportNo " 
					+ " from   InsurancePRVCoversEntity prvCover "
					+ " left join ApplicationParameterListEntity aple on (aple.parameterListID=prvCover.addOnCoverageId AND aple.recordType<>'D') "
					+ " left join CustomerEntity customer on (customer.customerId=prvCover.customerId AND customer.recordType<>'D') "
					+ " left join CustomerAddressEntity customerAdd on (customerAdd.customerId=customer.customerId AND customerAdd.addressType='Current' AND customerAdd.recordType<>'D') "
					+ " where prvCover.recordType<>'D' AND prvCover.vehicleInsuranceId.vehicleInsuranceId=:vehicleInsuranceId ";

			insuranceNewCoversBeanList = session.createQuery(sqlQuery).setParameter("vehicleInsuranceId", id)
					.setResultTransformer(Transformers.aliasToBean(InsuranceCoversBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return insuranceNewCoversBeanList;
	}

	@Override
	public List<InsuranceNomineesBean> getInsuranceNomineeDetails(Long id) {
		Session session = (Session) entityManager.getDelegate();
		List<InsuranceNomineesBean> insuranceNomineesBeanList = null;
		String sqlQuery = "";
		try {
			sqlQuery = "Select nominee.insuranceNomineeId as insuranceNomineeId ,nominee.nomineeContactNo as nomineeContactNo ,"
					+ " nominee.nomineeName as nomineeName ,nominee.nomineeRelationShip as nomineeRelationShip ,"
					+ " nominee.transactionCount as transactionCount,nominee.nomineeShare as nomineeShare "
					+ " from   InsuranceNomineesEntity nominee "
					// + " left join ApplicationParameterListEntity aple on
					// (aple.parameterListID=newCover.addOnCoverageId AND aple.recordType<>'D') "
					+ " where nominee.recordType<>'D' AND nominee.vehicleInsuranceId.vehicleInsuranceId=:vehicleInsuranceId ";

			insuranceNomineesBeanList = session.createQuery(sqlQuery).setParameter("vehicleInsuranceId", id)
					.setResultTransformer(Transformers.aliasToBean(InsuranceNomineesBean.class)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return insuranceNomineesBeanList;
	}
	

	@Override
	public boolean uploadInsuranceDocumentEntity(Long insuranceDocumentId, Long storageId, String fileName, Integer lastModifiedBy) {

		boolean result = false;
		String hql = "update VehicleInsuranceDocumentsEntity vehInsDocEnt set vehInsDocEnt.storageLocation.locationId=:storageId "
				+ " , vehInsDocEnt.fileName=:fileName "
				+ ",vehInsDocEnt.lastModifiedBy=:lastModifiedBy,vehInsDocEnt.lastModifiedDate=:lastModifiedDate,"
				+ " vehInsDocEnt.recordType='U' WHERE vehInsDocEnt.insuranceDocumentId=:insuranceDocumentId";
		Session session = (Session) entityManager.getDelegate();
		Query query = session.createQuery(hql);
		query.setParameter("storageId", storageId);
		query.setParameter("fileName", fileName);
		query.setParameter("insuranceDocumentId", insuranceDocumentId);
		query.setParameter("lastModifiedBy", Long.parseLong(lastModifiedBy.toString()));
		query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
		query.executeUpdate();
		result = true;
		return result;

	}
	
	
	@Override
	public boolean deleteInsuranceDocument(Long deleteRecordId, Long lastModifiedBy) {
		boolean result = true;
		try {
			String hql = "update VehicleInsuranceDocumentsEntity se set se.transactionCount=2, se.recordType='D' , se.lastModifiedBy=:lastModifiedBy ,se.lastModifiedDate=:lastModifiedDate "
					+ " WHERE se.insuranceDocumentId=:deleteRecordId";
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
	public List<VendorDocumentTO> fetchInsuranceDocumentDetails(Long vehicleInsuranceId) {
		Session session = (Session) entityManager.getDelegate();
		List<VendorDocumentTO> uploadApplicantCvToListt = null;
		String dateTimeFormat = messageSource.getMessage("dateTimeFormat", null, "", Locale.US);
		StringBuilder sqlString = new StringBuilder(
				" select vade.insuranceDocumentId as insuranceDocumentId,vade.documentName as documentName,DATE_FORMAT(vade.createdDate,'" + 
						 dateTimeFormat + "') As uploadDate,"
						+ "vade.documentNumber as documentNumber,aplv.parameterListValue as documentType, vade.documentDesc as documentDesc,vade.transactionCount as transactionCount "
						+ " ,vade.fileName as fileName, vade.storageLocation.locationId as viewDocument"
						+ " from VehicleInsuranceDocumentsEntity vade left join vade.documentTypeListId aplv"
						+ " where vade.vehicleInsuranceId=:vehicleInsuranceId and vade.recordType<>'D' order by vade.insuranceDocumentId desc");

		uploadApplicantCvToListt = session.createQuery(sqlString.toString()).setParameter("vehicleInsuranceId", vehicleInsuranceId)
				.setResultTransformer(Transformers.aliasToBean(VendorDocumentTO.class)).list();
		return uploadApplicantCvToListt;
	}

	



}
