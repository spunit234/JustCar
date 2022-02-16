package com.edios.csr.dao;

import java.util.List;

import com.edios.cdf.entity.AbstractEntity;
import com.edios.csr.bean.AggregatorLoanBean;
import com.edios.csr.bean.AggregatorPaymentReceivedBean;
import com.edios.csr.bean.AggregatorPaymentsBean;
import com.edios.csr.bean.AggregatorRCMovementBean;
import com.edios.csr.bean.AggregatorTasksBean;
import com.edios.csr.bean.AggregatorsBean;
import com.edios.csr.entity.AggregatorLoanEntity;
import com.edios.csr.entity.AggregatorPaymentReceivedEntity;
import com.edios.csr.entity.to.AggregatorTO;
import com.edios.csr.entity.to.VendorDocumentTO;

public interface AggregatorDao {

	List<AggregatorTO> getManageAggregatorDetails(AggregatorTO aggregatorManage);

	Long addAggregatorsDetails(AbstractEntity aggregatorsEntity);

	AggregatorsBean getAggregatorDetailsByInquiryId(Long id);

	AbstractEntity fetchAggregatorsDetailsByID(String string, Long aggregatorId);

	List<AggregatorPaymentsBean> getAggregatorPaymentsDetails(Long id);

	List<AggregatorRCMovementBean> getAggregatorRCMovementDetails(Long id);

	List<AggregatorTasksBean> getAggregatorTasksDetails(Long id);

	AggregatorPaymentsBean getCustomerDetailsForPaymentsByInquiryId(Long inquiryId);

	List<AggregatorTasksBean> getStaffDetailsForRCTasks();

	boolean updateAggregatorDocumentEntity(Long aggregatorsDocumentId, Long storageId, String fileName,
			Integer createdBy);

	List<VendorDocumentTO> fetchAggregatorDocumentDetails(Long projectId);

	boolean deleteAggregatorDocument(Long id, Long l);

	boolean addAggregatorLoan(AggregatorLoanEntity aggregatorLoanEntity);

	AggregatorLoanBean getAggregatorLoanDetails(Long aggregatorLoanId);

	boolean updateAggregatorLoan(AggregatorLoanEntity aggregatorLoanEntity);

	boolean updateAllRCTask(AggregatorTasksBean aggregatorTasksBean);

	boolean addAggregatorPaymentReceived(AggregatorPaymentReceivedEntity aggregatorPaymentReceivedEntity);

	List<AggregatorPaymentReceivedBean> getAggregatorPaymentReceivedDetails(Long aggregatorId);

	boolean updateOrDeleteAggregatorPaymentReceived(AggregatorPaymentReceivedEntity aggregatorPaymentReceivedEntity);

}
