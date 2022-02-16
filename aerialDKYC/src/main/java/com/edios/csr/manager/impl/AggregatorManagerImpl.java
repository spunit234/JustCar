package com.edios.csr.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.edios.cdf.manager.impl.AbstractManagerImpl;
import com.edios.cdf.manager.impl.StorageLocationUtil;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.util.StorageData;
import com.edios.csr.bean.AggregatorLoanBean;
import com.edios.csr.bean.AggregatorPaymentReceivedBean;
import com.edios.csr.bean.AggregatorPaymentsBean;
import com.edios.csr.bean.AggregatorRCMovementBean;
import com.edios.csr.bean.AggregatorTasksBean;
import com.edios.csr.bean.AggregatorsBean;
import com.edios.csr.bean.ProjectDocumentBean;
import com.edios.csr.dao.AggregatorDao;
import com.edios.csr.entity.AggregatorDocumentsEntity;
import com.edios.csr.entity.AggregatorLoanEntity;
import com.edios.csr.entity.AggregatorPaymentReceivedEntity;
import com.edios.csr.entity.AggregatorPaymentsEntity;
import com.edios.csr.entity.AggregatorRCMovementEntity;
import com.edios.csr.entity.AggregatorTasksEntity;
import com.edios.csr.entity.AggregatorsEntity;
import com.edios.csr.entity.to.AggregatorTO;
import com.edios.csr.entity.to.VendorDocumentTO;
import com.edios.csr.manager.AggregatorManager;

@Service("aggregatorManager")
public class AggregatorManagerImpl extends AbstractManagerImpl<AggregatorsBean, AggregatorsEntity>
		implements AggregatorManager {

	@Autowired
	AggregatorDao aggregatorDao;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	StorageLocationUtil storageLocationUtil;


	@Transactional("db1Tx")
	@Override
	public List<AggregatorTO> getManageAggregatorDetails(AggregatorTO aggregatorManage) {
		return aggregatorDao.getManageAggregatorDetails(aggregatorManage);
	}

	@Override
	@Transactional("db1Tx")
	public String addAggregatorDetails(AggregatorsBean aggregatorsBean) {
		Long aggregatorsBeanId;

		aggregatorsBean.setTransactionCount(1);
		aggregatorsBean.setCreatedDate(new Date());
		aggregatorsBean.setRecordType('I');
		//aggregatorsBean.setAggregatorDate(aggregatorsBean.getCreatedDate());

		AggregatorsEntity aggregatorsEntity = mapper.map(aggregatorsBean, AggregatorsEntity.class);
		aggregatorsBeanId = aggregatorDao.addAggregatorsDetails(aggregatorsEntity);

		return "ADDED";

	}

	@Override
	@Transactional("db1Tx")
	public AggregatorsBean getAggregatorDetailsByInquiryId(PayloadBean payloadBean) {
		return aggregatorDao.getAggregatorDetailsByInquiryId(payloadBean.getId());

	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteAggregatorDetails(AggregatorsBean aggregatorsBean) {
		String resultString = "DELETED";

		AggregatorsEntity latestData = (AggregatorsEntity) aggregatorDao
				.fetchAggregatorsDetailsByID("AggregatorsEntity", aggregatorsBean.getAggregatorId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (aggregatorsBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(aggregatorsBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(aggregatorsBean.getLastModifiedBy());

		if (aggregatorsBean.getRecordType() != 'D') {
			latestData.setUpdatedDetails(aggregatorsBean);
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		return resultString;
	}
	
	@Override
	@Transactional("db1Tx")
	public AggregatorPaymentsBean getCustomerDetailsForPaymentsByInquiryId(PayloadBean payloadBean) {
		return aggregatorDao.getCustomerDetailsForPaymentsByInquiryId(payloadBean.getId());

	}

	

	@Override
	@Transactional("db1Tx")
	public String addAggregatorPaymentsDetails(AggregatorPaymentsBean aggregatorPaymentsBean) {
		Long aggregatorsBeanId;

		aggregatorPaymentsBean.setTransactionCount(1);
		aggregatorPaymentsBean.setCreatedDate(new Date());
		aggregatorPaymentsBean.setRecordType('I');
//		aggregatorPaymentsBean.setAggregatorDate(aggregatorPaymentsBean.getCreatedDate());

		AggregatorPaymentsEntity aggregatorPaymentsEntity = mapper.map(aggregatorPaymentsBean, AggregatorPaymentsEntity.class);
		aggregatorsBeanId = aggregatorDao.addAggregatorsDetails(aggregatorPaymentsEntity);

		return "ADDED";

	}

	@Override
	@Transactional("db1Tx")
	public List<AggregatorPaymentsBean> getAggregatorPaymentsDetails(PayloadBean payloadBean){
		return aggregatorDao.getAggregatorPaymentsDetails(payloadBean.getId());

	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteAggregatorPaymentsDetails(AggregatorPaymentsBean aggregatorPaymentsBean) {
		String resultString = "DELETED";

		AggregatorPaymentsEntity latestData = (AggregatorPaymentsEntity) aggregatorDao
				.fetchAggregatorsDetailsByID("AggregatorPaymentsEntity", aggregatorPaymentsBean.getAggregatorPaymentId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (aggregatorPaymentsBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(aggregatorPaymentsBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(aggregatorPaymentsBean.getLastModifiedBy());

		if (aggregatorPaymentsBean.getRecordType() != 'D') {
			latestData.setUpdatedDetails(aggregatorPaymentsBean);
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		return resultString;
	}

	

	@Override
	@Transactional("db1Tx")
	public String addAggregatorRCMovementDetails(AggregatorRCMovementBean aggregatorRCMovementBean) {
		Long aggregatorsBeanId;

		aggregatorRCMovementBean.setTransactionCount(1);
		aggregatorRCMovementBean.setCreatedDate(new Date());
		aggregatorRCMovementBean.setRecordType('I');
//		aggregatorsBean.setAggregatorDate(aggregatorsBean.getCreatedDate());

		AggregatorRCMovementEntity aggregatorRCMovementEntity = mapper.map(aggregatorRCMovementBean, AggregatorRCMovementEntity.class);
		aggregatorsBeanId = aggregatorDao.addAggregatorsDetails(aggregatorRCMovementEntity);

		return "ADDED";

	}

	@Override
	@Transactional("db1Tx")
	public List<AggregatorRCMovementBean> getAggregatorRCMovementDetails(PayloadBean payloadBean) {
		return aggregatorDao.getAggregatorRCMovementDetails(payloadBean.getId());

	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteAggregatorRCMovementDetails(AggregatorRCMovementBean aggregatorRCMovementBean) {
		String resultString = "DELETED";

		AggregatorRCMovementEntity latestData = (AggregatorRCMovementEntity) aggregatorDao
				.fetchAggregatorsDetailsByID("AggregatorRCMovementEntity", aggregatorRCMovementBean.getAggregatorRCMovementId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (aggregatorRCMovementBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(aggregatorRCMovementBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(aggregatorRCMovementBean.getLastModifiedBy());

		if (aggregatorRCMovementBean.getRecordType() != 'D') {
			latestData.setUpdatedDetails(aggregatorRCMovementBean);
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		return resultString;
	}

	

	@Override
	@Transactional("db1Tx")
	public String addAggregatorTasksDetails(AggregatorTasksBean aggregatorTasksBean) {
		Long aggregatorsBeanId;

		aggregatorTasksBean.setTransactionCount(1);
		aggregatorTasksBean.setCreatedDate(new Date());
		aggregatorTasksBean.setRecordType('I');
//		aggregatorTasksBean.setAggregatorDate(aggregatorsBean.getCreatedDate());

		AggregatorTasksEntity aggregatorTasksEntity = mapper.map(aggregatorTasksBean, AggregatorTasksEntity.class);
		aggregatorsBeanId = aggregatorDao.addAggregatorsDetails(aggregatorTasksEntity);

		return "ADDED";

	}

	@Override
	@Transactional("db1Tx")
	public List<AggregatorTasksBean> getAggregatorTasksDetails(PayloadBean payloadBean) {
		return aggregatorDao.getAggregatorTasksDetails(payloadBean.getId());

	}

	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteAggregatorTasksDetails(AggregatorTasksBean aggregatorTasksBean) {
		String resultString = "DELETED";

		AggregatorTasksEntity latestData = (AggregatorTasksEntity) aggregatorDao
				.fetchAggregatorsDetailsByID("AggregatorTasksEntity", aggregatorTasksBean.getAggregatorTaskId());
		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (aggregatorTasksBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(aggregatorTasksBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(aggregatorTasksBean.getLastModifiedBy());

		if (aggregatorTasksBean.getRecordType() != 'D') {
			latestData.setUpdatedDetails(aggregatorTasksBean);
			latestData.setRecordType('U');
			resultString = "UPDATED";
		} else
			latestData.setRecordType('D');

		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public List<AggregatorTasksBean> getStaffDetailsForRCTasks() {
		return aggregatorDao.getStaffDetailsForRCTasks();
	}

	
	
	@Override
	@Transactional("db1Tx")
	public String uploadAggregatorDocument(ProjectDocumentBean projectDocumentBean, MultipartFile file) {
		String resultString = "";

		projectDocumentBean.setTransactionCount(1L);
		projectDocumentBean.setCreatedDate(new Date());
		projectDocumentBean.setRecordType('I');

		AggregatorDocumentsEntity aggregatorDocumentsEntity = mapper.map(projectDocumentBean, AggregatorDocumentsEntity.class);
		Long aggregatorsDocumentId = aggregatorDao.addAggregatorsDetails(aggregatorDocumentsEntity);
		
		boolean result = false;
		if (aggregatorsDocumentId != null && file != null) {
			String locationName = messageSource.getMessage("locationForProjectDocument", null, "", Locale.US);
			StorageData storageData = storageLocationUtil.addDocument(aggregatorsDocumentId, locationName, file);
			result = aggregatorDao.updateAggregatorDocumentEntity(aggregatorsDocumentId, storageData.getStorageId(),
					storageData.getFileName(), projectDocumentBean.getCreatedBy());
			if (result)
				return "ADDED";
		}
		if (aggregatorsDocumentId != null) {
			return "ADDED";
		}
		return resultString;
	}
	
	@Override
	@Transactional("db1Tx")
	public List<VendorDocumentTO> fetchAggregatorDocumentDetails(Long projectId) {
		return aggregatorDao.fetchAggregatorDocumentDetails(projectId);
	}
	
	@Override
	@Transactional("db1Tx")
	public String deleteAggregatorDocument(DeleteRecords deleteRecords) {
		String resultString = "";
		boolean resultFlag = false;
		Long l = Long.valueOf(deleteRecords.getModifiedBy());
		
		resultFlag = aggregatorDao.deleteAggregatorDocument(deleteRecords.getId(), l);
		if (resultFlag)
			return "DELETED";
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String addAggregatorLoan(AggregatorLoanBean aggregatorLoanBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			aggregatorLoanBean.setTransactionCount(1L);
			aggregatorLoanBean.setCreatedDate(new Date());
			aggregatorLoanBean.setRecordType('I');
			
			AggregatorLoanEntity aggregatorLoanEntity = mapper.map(aggregatorLoanBean, AggregatorLoanEntity.class);
			resultFlag = aggregatorDao.addAggregatorLoan(aggregatorLoanEntity);
			if (resultFlag) {
				resultString = "Added";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	@Override
	@Transactional("db1Tx")
	public AggregatorLoanBean getAggregatorLoanDetails(PayloadBean payloadBean) {
		return aggregatorDao.getAggregatorLoanDetails(payloadBean.getId());

	}

	@Override
	@Transactional("db1Tx")
	public String updateAggregatorLoan(AggregatorLoanBean aggregatorLoanBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			aggregatorLoanBean.setLastModifiedDate(new Date());
			AggregatorLoanEntity aggregatorLoanEntity = mapper.map(aggregatorLoanBean, AggregatorLoanEntity.class);
			resultFlag = aggregatorDao.updateAggregatorLoan(aggregatorLoanEntity);
			if (resultFlag) {
				return "UPDATED";
			}
		} catch (MappingException e) {
			e.printStackTrace();
		}
		return resultString;
	}
	@Override
	@Transactional("db1Tx")
	public String updateAllRCTask(AggregatorTasksBean aggregatorTasksBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = aggregatorDao.updateAllRCTask(aggregatorTasksBean);
		if (resultFlag)
			return "UPDATED";
		return resultString;
	}
	@Override
	@Transactional("db1Tx")
	public String addAggregatorPaymentReceived(AggregatorPaymentReceivedBean aggregatorPaymentReceivedBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			aggregatorPaymentReceivedBean.setTransactionCount(1L);
			aggregatorPaymentReceivedBean.setCreatedDate(new Date());
			aggregatorPaymentReceivedBean.setRecordType('I');
			
			AggregatorPaymentReceivedEntity aggregatorPaymentReceivedEntity = mapper.map(aggregatorPaymentReceivedBean, AggregatorPaymentReceivedEntity.class);
			resultFlag = aggregatorDao.addAggregatorPaymentReceived(aggregatorPaymentReceivedEntity);
			if (resultFlag) {
				resultString = "Added";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	
	@Override
	@Transactional("db1Tx")
	public List<AggregatorPaymentReceivedBean> getAggregatorPaymentReceivedDetails(PayloadBean payloadBean){
		return aggregatorDao.getAggregatorPaymentReceivedDetails(payloadBean.getId());
	}
	
	@Override
	@Transactional("db1Tx")
	public String updateOrDeleteAggregatorPaymentReceived(AggregatorPaymentReceivedBean aggregatorPaymentReceivedBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			aggregatorPaymentReceivedBean.setLastModifiedDate(new Date());
			AggregatorPaymentReceivedEntity aggregatorPaymentReceivedEntity= mapper.map(aggregatorPaymentReceivedBean, AggregatorPaymentReceivedEntity.class);
			resultFlag = aggregatorDao.updateOrDeleteAggregatorPaymentReceived(aggregatorPaymentReceivedEntity);
			if (resultFlag) {
				return "UPDATED";
			}
		} catch (MappingException e) {
			e.printStackTrace();
		}
		return resultString;
	}
	
}
