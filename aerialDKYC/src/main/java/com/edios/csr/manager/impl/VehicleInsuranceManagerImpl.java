package com.edios.csr.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.entity.AbstractEntity;
import com.edios.cdf.manager.impl.AbstractManagerImpl;
import com.edios.cdf.manager.impl.StorageLocationUtil;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.util.StorageData;
import com.edios.csr.bean.InsuranceCoversBean;
import com.edios.csr.bean.InsuranceNomineesBean;
import com.edios.csr.bean.InsurancePaymentsBean;
import com.edios.csr.bean.ProjectDocumentBean;
import com.edios.csr.bean.VehicleInsuranceBean;
import com.edios.csr.dao.VehicleInsuranceDao;
import com.edios.csr.entity.InsuranceNewCoversEntity;
import com.edios.csr.entity.InsuranceNomineesEntity;
import com.edios.csr.entity.InsurancePRVCoversEntity;
import com.edios.csr.entity.InsurancePaymentsEntity;
import com.edios.csr.entity.VehicleInsuranceDocumentsEntity;
import com.edios.csr.entity.VehicleInsuranceEntity;
import com.edios.csr.entity.to.VehicleInsuranceManageTO;
import com.edios.csr.entity.to.VendorDocumentTO;
import com.edios.csr.manager.VehicleInsuranceManager;

@Service("vehicleInsuranceManager")
public class VehicleInsuranceManagerImpl extends AbstractManagerImpl<VehicleInsuranceBean, VehicleInsuranceEntity>
		implements VehicleInsuranceManager {

	@Autowired
	VehicleInsuranceDao vehicleInsuranceDao;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	StorageLocationUtil storageLocationUtil;

	@Override
	@Transactional("db1Tx")
	public String addVehicleInsuranceDetails(VehicleInsuranceBean vehicleInsuranceBean) {
		Long vehicleInsuranceId;

		vehicleInsuranceBean.setTransactionCount(1L);
		vehicleInsuranceBean.setCreatedDate(new Date());
		vehicleInsuranceBean.setRecordType('I');

		VehicleInsuranceEntity vehicleInsuranceEntity = mapper.map(vehicleInsuranceBean, VehicleInsuranceEntity.class);
		vehicleInsuranceId = vehicleInsuranceDao.addVehicleInsuranceDetails(vehicleInsuranceEntity);
		
		if(vehicleInsuranceBean.getVehicleInsuranceExistingId() != null) {
			
			vehicleInsuranceEntity = new VehicleInsuranceEntity();
			vehicleInsuranceEntity.setVehicleInsuranceId(vehicleInsuranceId);
			
			List<AbstractEntity> insuranceCoversEntityList =  vehicleInsuranceDao
					.fetchInsuranceDetailsByInsuranceID("InsuranceNewCoversEntity", vehicleInsuranceBean.getVehicleInsuranceExistingId());
			InsurancePRVCoversEntity insurancePRVCoversEntity = null;
//			CustomerAddressEntity custAddId = new CustomerAddressEntity();
//			custAddId.setCustomerAddId(vehicleInsuranceBean.getInsuranceExistingCustAddrId());
			for(AbstractEntity insuranceNewCoversEntity: insuranceCoversEntityList) {
				insurancePRVCoversEntity = new InsurancePRVCoversEntity();
				insurancePRVCoversEntity.setUpdatedFields((InsuranceNewCoversEntity)insuranceNewCoversEntity);
				insurancePRVCoversEntity.setVehicleInsuranceId(vehicleInsuranceEntity);
				insurancePRVCoversEntity.setPolicyNo(vehicleInsuranceBean.getInsuranceExistingPolicyNo());
				insurancePRVCoversEntity.setInsuranceCompanyId(vehicleInsuranceBean.getInsuranceExistingInsurCompId());
				insurancePRVCoversEntity.setCustomerId(vehicleInsuranceBean.getInsuranceExistingCustId());
				insurancePRVCoversEntity.setTransactionCount(1L);
				insurancePRVCoversEntity.setCreatedDate(new Date());
				insurancePRVCoversEntity.setCreatedBy(vehicleInsuranceBean.getCreatedBy());
				insurancePRVCoversEntity.setRecordType('I');
				vehicleInsuranceDao.addVehicleInsuranceDetails(insurancePRVCoversEntity);

			}
			
			 insuranceCoversEntityList =  vehicleInsuranceDao
					.fetchInsuranceDetailsByInsuranceID("InsurancePRVCoversEntity", vehicleInsuranceBean.getVehicleInsuranceExistingId());
			insurancePRVCoversEntity = null;
			for(AbstractEntity insuranceCoversEntity: insuranceCoversEntityList) {
				insurancePRVCoversEntity = new InsurancePRVCoversEntity();
				insurancePRVCoversEntity.setUpdatedFields((InsurancePRVCoversEntity)insuranceCoversEntity);
				insurancePRVCoversEntity.setVehicleInsuranceId(vehicleInsuranceEntity);
				insurancePRVCoversEntity.setTransactionCount(1L);
				insurancePRVCoversEntity.setCreatedDate(new Date());
				insurancePRVCoversEntity.setCreatedBy(vehicleInsuranceBean.getCreatedBy());
				insurancePRVCoversEntity.setRecordType('I');
				vehicleInsuranceDao.addVehicleInsuranceDetails(insurancePRVCoversEntity);

			}
			
			
			
			List<AbstractEntity> insuranceNomineesEntityList =  vehicleInsuranceDao
					.fetchInsuranceDetailsByInsuranceID("InsuranceNomineesEntity", vehicleInsuranceBean.getVehicleInsuranceExistingId());
			InsuranceNomineesEntity nomineesEntity = null;
			for(AbstractEntity insuranceNomineesEntity: insuranceNomineesEntityList) {
				nomineesEntity = new InsuranceNomineesEntity();
				nomineesEntity.setUpdatedFields((InsuranceNomineesEntity)insuranceNomineesEntity);
				nomineesEntity.setVehicleInsuranceId(vehicleInsuranceEntity);
				nomineesEntity.setInsuranceNomineeId(null);
				nomineesEntity.setTransactionCount(1L);
				nomineesEntity.setCreatedDate(new Date());
				nomineesEntity.setRecordType('I');
				nomineesEntity.setCreatedBy(vehicleInsuranceBean.getCreatedBy());

				vehicleInsuranceDao.addVehicleInsuranceDetails(nomineesEntity);
			}
			

			
		}
		return "ADDED";

	}

	@Override
	@Transactional("db1Tx")
	public List<VehicleInsuranceManageTO> getManageInsuranceDetails(VehicleInsuranceManageTO vehicleInsuranceManag) {
		// TODO Auto-generated method stub
		return vehicleInsuranceDao.getManageInsuranceDetails(vehicleInsuranceManag);
	}

	
	@Override
	@Transactional("db1Tx")
	public List<VehicleInsuranceManageTO> getExistingInsuranceDetails(PayloadBean payloadbean) {
		// TODO Auto-generated method stub
		return vehicleInsuranceDao.getExistingInsuranceDetails(payloadbean);
	}

	
	@Override
	@Transactional("db1Tx")
	public VehicleInsuranceManageTO getInsuranceDetailsByVehicleId(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return vehicleInsuranceDao.getInsuranceDetailsByVehicleId(payloadBean.getId());
	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteInsuranceDetails(VehicleInsuranceBean vehicleInsuranceBean) {

		String resultString = "DELETED";

		VehicleInsuranceEntity latestData = (VehicleInsuranceEntity) vehicleInsuranceDao
				.fetchInsuranceDetailsByID("VehicleInsuranceEntity", vehicleInsuranceBean.getVehicleInsuranceId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (vehicleInsuranceBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(vehicleInsuranceBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(vehicleInsuranceBean.getLastModifiedBy());

		if (vehicleInsuranceBean.getRecordType() != 'D') {
			latestData.setInsuranceDate(vehicleInsuranceBean.getInsuranceDate());
			latestData.setInsuranceSource(vehicleInsuranceBean.getInsuranceSource());
			latestData.setInsurancePaymentPaidBy(vehicleInsuranceBean.getInsurancePaymentPaidBy());
			latestData.setInsuranceStatus(vehicleInsuranceBean.getInsuranceStatus());
			latestData.setInsuranceCompanyId(vehicleInsuranceBean.getInsuranceCompanyId());
			latestData.setPolicyNo(vehicleInsuranceBean.getPolicyNo());
			latestData.setPolicyIssueDate(vehicleInsuranceBean.getPolicyIssueDate());
			latestData.setInsurancePaymentPaidDate(vehicleInsuranceBean.getInsurancePaymentPaidDate());
			latestData.setInsuranceAmount(vehicleInsuranceBean.getInsuranceAmount());
			latestData.setDispatchNo(vehicleInsuranceBean.getDispatchNo());
			latestData.setDispatchDate(vehicleInsuranceBean.getDispatchDate());
			latestData.setNotes(vehicleInsuranceBean.getNotes());
			latestData.setCompanyBankId(vehicleInsuranceBean.getCompanyBankId());
			latestData.setModeOfDispatch(vehicleInsuranceBean.getModeOfDispatch());
			latestData.setModeOfPayment(vehicleInsuranceBean.getModeOfPayment());
			latestData.setContactNumber(vehicleInsuranceBean.getContactNumber());
			latestData.setVehicleChassisNo(vehicleInsuranceBean.getVehicleChassisNo());
			latestData.setVehicleEngineNo(vehicleInsuranceBean.getVehicleEngineNo());
			latestData.setInsCustomerName(vehicleInsuranceBean.getInsCustomerName());
			latestData.setIgnoreFlag(vehicleInsuranceBean.getIgnoreFlag());
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String addVehicleInsuranceDetails(AbstractBean insuranceBeans, String beanType) {
		Long resultFlag ;

		InsurancePaymentsEntity insuranceEntity = mapper.map(insuranceBeans, InsurancePaymentsEntity.class);

		insuranceEntity.setTransactionCount(1L);
		insuranceEntity.setCreatedDate(new Date());
		insuranceEntity.setRecordType('I');

		resultFlag = vehicleInsuranceDao.addVehicleInsuranceDetails(insuranceEntity);
		if (resultFlag != null) {
			return "ADDED";
		} else
			return "";
	}

	@Override
	@Transactional("db1Tx")
	public List<InsurancePaymentsBean> getInsurancePaymentDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return vehicleInsuranceDao.getInsurancePaymentDetails(payloadBean.getId());
	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeletePaymentDetails(InsurancePaymentsBean insurancePaymentsBean) {
		String resultString = "DELETED";

		InsurancePaymentsEntity latestData = (InsurancePaymentsEntity) vehicleInsuranceDao
				.fetchInsuranceDetailsByID("InsurancePaymentsEntity", insurancePaymentsBean.getInsurancePaymentId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (insurancePaymentsBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(insurancePaymentsBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(insurancePaymentsBean.getLastModifiedBy());

		if (insurancePaymentsBean.getRecordType() != 'D') {
			latestData.setInsurancePaymentDate(insurancePaymentsBean.getInsurancePaymentDate());
			latestData.setInsuranceModePayment(insurancePaymentsBean.getInsuranceModePayment());
			latestData.setInsurancePaymentAmount(insurancePaymentsBean.getInsurancePaymentAmount());
			latestData.setCompanyBankId(insurancePaymentsBean.getCompanyBankId());
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

//		boolean resultFlag = customerDao.updateOrDeleteCustomersDetails(latestData);

//		if (resultFlag) {
		return resultString;
//		}
//		return null;
	}

	@Override
	@Transactional("db1Tx")
	public String addInsuranceNewCoversDetails(InsuranceCoversBean insuranceNewCoversBean) {
		Long resultFlag ;
		
		if(insuranceNewCoversBean.getRenewalType().equals("On Time Renewal")) {

			
			List<AbstractEntity> insuranceCoversEntityList =  vehicleInsuranceDao
					.fetchInsuranceDetailsByInsuranceID("InsuranceNewCoversEntity", insuranceNewCoversBean.getVehicleInsuranceId().getVehicleInsuranceId());
			
			InsurancePRVCoversEntity insurancePRVCoversEntity = null;
//			CustomerAddressEntity custAddId = new CustomerAddressEntity();
//			custAddId.setCustomerAddId(3L);
			for(AbstractEntity insuranceNewCoversEntity: insuranceCoversEntityList) {
				insurancePRVCoversEntity = new InsurancePRVCoversEntity();
				insurancePRVCoversEntity.setUpdatedFields((InsuranceNewCoversEntity)insuranceNewCoversEntity);
				insurancePRVCoversEntity.setVehicleInsuranceId(insuranceNewCoversBean.getVehicleInsuranceId());
				insurancePRVCoversEntity.setPolicyNo(insuranceNewCoversBean.getPolicyNo());
				insurancePRVCoversEntity.setInsuranceCompanyId(insuranceNewCoversBean.getInsuranceCompanyId());
				insurancePRVCoversEntity.setCustomerId(insuranceNewCoversBean.getCustomerId());

//				insurancePRVCoversEntity = mapper.map((InsuranceNewCoversEntity)insuranceNewCoversEntity, InsurancePRVCoversEntity.class);
				insurancePRVCoversEntity.setTransactionCount(1L);
				insurancePRVCoversEntity.setCreatedDate(new Date());
				insurancePRVCoversEntity.setCreatedBy(insuranceNewCoversBean.getCreatedBy());
				insurancePRVCoversEntity.setRecordType('I');
				vehicleInsuranceDao.addVehicleInsuranceDetails(insurancePRVCoversEntity);
				InsuranceNewCoversEntity a = (InsuranceNewCoversEntity)insuranceNewCoversEntity;
				a.setRecordType('D');

			}
			insurancePRVCoversEntity = null;
			insuranceCoversEntityList = null;

		}
		InsuranceNewCoversEntity insuranceNewCoversEntity = mapper.map(insuranceNewCoversBean,
				InsuranceNewCoversEntity.class);

		insuranceNewCoversEntity.setTransactionCount(1L);
		insuranceNewCoversEntity.setCreatedDate(new Date());
		insuranceNewCoversEntity.setRecordType('I');

		resultFlag = vehicleInsuranceDao.addVehicleInsuranceDetails(insuranceNewCoversEntity);
		if (resultFlag != null) {
			return "ADDED";
		} else
			return "";
	}

	@Override
	@Transactional("db1Tx")
	public List<InsuranceCoversBean> getInsuranceNewCoverDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return vehicleInsuranceDao.getInsuranceNewCoversDetails(payloadBean.getId());
	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteNewCoversDetails(InsuranceCoversBean insuranceNewCoversBean) {
		String resultString = "DELETED";

		InsuranceNewCoversEntity latestData = (InsuranceNewCoversEntity) vehicleInsuranceDao
				.fetchInsuranceDetailsByID("InsuranceNewCoversEntity", insuranceNewCoversBean.getInsuranceNewCoverId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (insuranceNewCoversBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(insuranceNewCoversBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(insuranceNewCoversBean.getLastModifiedBy());

		if (insuranceNewCoversBean.getRecordType() != 'D') {
			// set all the fields and update the updated field automatically
			latestData.setUpdatedFields(insuranceNewCoversBean);
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		return resultString;

	}

	@Override
	@Transactional("db1Tx")
	public List<InsuranceCoversBean> getInsurancePRVCoverDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return vehicleInsuranceDao.getInsurancePRVCoverDetails(payloadBean.getId());
	}

	
	@Override
	@Transactional("db1Tx")
	public String addInsurancePrvCoversDetails(InsuranceCoversBean insurancePrvCoversBean) {
		Long resultFlag ;

		InsurancePRVCoversEntity insurancePrvCoversEntity = mapper.map(insurancePrvCoversBean,
				InsurancePRVCoversEntity.class);

		insurancePrvCoversEntity.setTransactionCount(1L);
		insurancePrvCoversEntity.setCreatedDate(new Date());
		insurancePrvCoversEntity.setRecordType('I');

		resultFlag = vehicleInsuranceDao.addVehicleInsuranceDetails(insurancePrvCoversEntity);
		if (resultFlag != null) {
			return "ADDED";
		} else
			return "";
	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeletePrvCoversDetails(InsuranceCoversBean insurancePrvCoversBean) {
		String resultString = "DELETED";

		InsurancePRVCoversEntity latestData = (InsurancePRVCoversEntity) vehicleInsuranceDao
				.fetchInsuranceDetailsByID("InsurancePRVCoversEntity", insurancePrvCoversBean.getInsurancePRVCoverId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (insurancePrvCoversBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(insurancePrvCoversBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(insurancePrvCoversBean.getLastModifiedBy());

		if (insurancePrvCoversBean.getRecordType() != 'D') {
			// set all the fields and update the updated field automatically
			latestData.setUpdatedFields(insurancePrvCoversBean);
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String addInsuranceNomineeDetails(InsuranceNomineesBean insuranceNomineesBean) {
		Long resultFlag ;

		InsuranceNomineesEntity insuranceNomineesEntity = mapper.map(insuranceNomineesBean,
				InsuranceNomineesEntity.class);

		insuranceNomineesEntity.setTransactionCount(1L);
		insuranceNomineesEntity.setCreatedDate(new Date());
		insuranceNomineesEntity.setRecordType('I');

		resultFlag = vehicleInsuranceDao.addVehicleInsuranceDetails(insuranceNomineesEntity);
		if (resultFlag != null) {
			return "ADDED";
		} else
			return "";
	}

	
	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteNomineeDetails(InsuranceNomineesBean insuranceNomineesBean) {
		String resultString = "DELETED";

		InsuranceNomineesEntity latestData = (InsuranceNomineesEntity) vehicleInsuranceDao
				.fetchInsuranceDetailsByID("InsuranceNomineesEntity", insuranceNomineesBean.getInsuranceNomineeId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (insuranceNomineesBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(insuranceNomineesBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(insuranceNomineesBean.getLastModifiedBy());

		if (insuranceNomineesBean.getRecordType() != 'D') {
			// set all the fields and update the updated field automatically
			latestData.setUpdatedFields(insuranceNomineesBean);
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public List<InsuranceNomineesBean> getInsuranceNomineeDetails(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		return vehicleInsuranceDao.getInsuranceNomineeDetails(payloadBean.getId());
	}

	

	
	@Override
	@Transactional("db1Tx")
	public String uploadInsuranceDocument(ProjectDocumentBean projectDocumentBean, MultipartFile file) {
		String resultString = "";

		projectDocumentBean.setTransactionCount(1L);
		projectDocumentBean.setCreatedDate(new Date());
		projectDocumentBean.setRecordType('I');

		VehicleInsuranceDocumentsEntity insuranceDocumentsEntity = mapper.map(projectDocumentBean, VehicleInsuranceDocumentsEntity.class);
		Long insuranceDocumentId = vehicleInsuranceDao.addVehicleInsuranceDetails(insuranceDocumentsEntity);
		
		boolean result = false;
		if (insuranceDocumentId != null && file != null) {
			String locationName = messageSource.getMessage("locationForProjectDocument", null, "", Locale.US);
			StorageData storageData = storageLocationUtil.addDocument(insuranceDocumentId, locationName, file);
			result = vehicleInsuranceDao.uploadInsuranceDocumentEntity(insuranceDocumentId, storageData.getStorageId(),
					storageData.getFileName(), projectDocumentBean.getCreatedBy());
			if (result)
				return "ADDED";
		}
		if (insuranceDocumentId != null) {
			return "ADDED";
		}
		return resultString;
	}
	
	@Override
	@Transactional("db1Tx")
	public List<VendorDocumentTO> fetchInsuranceDocumentDetails(Long vehicleInsuranceId) {
		return vehicleInsuranceDao.fetchInsuranceDocumentDetails(vehicleInsuranceId);
	}
	
	@Override
	@Transactional("db1Tx")
	public String deleteInsuranceDocument(DeleteRecords deleteRecords) {
		String resultString = "";
		boolean resultFlag = false;
		Long l = Long.valueOf(deleteRecords.getModifiedBy());
		
		resultFlag = vehicleInsuranceDao.deleteInsuranceDocument(deleteRecords.getId(), l);
		if (resultFlag)
			return "DELETED";
		return resultString;
	}

	

}
