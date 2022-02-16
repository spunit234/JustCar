package com.edios.csr.manager;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.AggregatorLoanBean;
import com.edios.csr.bean.AggregatorPaymentReceivedBean;
import com.edios.csr.bean.AggregatorPaymentsBean;
import com.edios.csr.bean.AggregatorRCMovementBean;
import com.edios.csr.bean.AggregatorTasksBean;
import com.edios.csr.bean.AggregatorsBean;
import com.edios.csr.bean.ProjectDocumentBean;
import com.edios.csr.entity.to.AggregatorTO;
import com.edios.csr.entity.to.VendorDocumentTO;

public interface AggregatorManager {

	List<AggregatorTO> getManageAggregatorDetails(AggregatorTO aggregatorManage);

	String addAggregatorDetails(AggregatorsBean aggregatorsBean);

	AggregatorsBean getAggregatorDetailsByInquiryId(PayloadBean payloadBean);

	String updateOrDeleteAggregatorDetails(AggregatorsBean aggregatorsBean);

	String addAggregatorPaymentsDetails(AggregatorPaymentsBean aggregatorPaymentsBean);

	List<AggregatorPaymentsBean> getAggregatorPaymentsDetails(PayloadBean payloadBean);

	String updateOrDeleteAggregatorPaymentsDetails(AggregatorPaymentsBean aggregatorPaymentsBean);

	String addAggregatorRCMovementDetails(AggregatorRCMovementBean aggregatorRCMovementBean);

	List<AggregatorRCMovementBean> getAggregatorRCMovementDetails(PayloadBean payloadBean);

	String updateOrDeleteAggregatorRCMovementDetails(AggregatorRCMovementBean aggregatorRCMovementBean);

	String addAggregatorTasksDetails(AggregatorTasksBean aggregatorTasksBean);

	List<AggregatorTasksBean> getAggregatorTasksDetails(PayloadBean payloadBean);

	String updateOrDeleteAggregatorTasksDetails(AggregatorTasksBean aggregatorTasksBean);

	AggregatorPaymentsBean getCustomerDetailsForPaymentsByInquiryId(PayloadBean payloadBean);

	List<AggregatorTasksBean> getStaffDetailsForRCTasks();

	String uploadAggregatorDocument(ProjectDocumentBean projectDocumentBean, MultipartFile file);


	String deleteAggregatorDocument(DeleteRecords deleteRecords);

	List<VendorDocumentTO> fetchAggregatorDocumentDetails(Long id);

	String addAggregatorLoan(AggregatorLoanBean aggregatorLoanBean);

	AggregatorLoanBean getAggregatorLoanDetails(PayloadBean payloadBean);

	String updateAggregatorLoan(AggregatorLoanBean aggregatorLoanBean);

	String updateAllRCTask(AggregatorTasksBean aggregatorTasksBean);

	String addAggregatorPaymentReceived(AggregatorPaymentReceivedBean aggregatorPaymentReceivedBean);

	List<AggregatorPaymentReceivedBean> getAggregatorPaymentReceivedDetails(PayloadBean payloadBean);

	String updateOrDeleteAggregatorPaymentReceived(AggregatorPaymentReceivedBean aggregatorPaymentReceivedBean);

}
