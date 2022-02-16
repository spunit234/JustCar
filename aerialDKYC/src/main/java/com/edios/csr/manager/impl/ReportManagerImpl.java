package com.edios.csr.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.entity.AbstractEntity;
import com.edios.cdf.manager.impl.AbstractManagerImpl;
import com.edios.csr.bean.BankLoanDetailsReportBean;
import com.edios.csr.bean.InquiryBean;
import com.edios.csr.dao.ReportDao;
import com.edios.csr.entity.to.InquiryTO;
import com.edios.csr.manager.ReportManager;

@Service("reportManager")
public class ReportManagerImpl extends AbstractManagerImpl<AbstractBean, AbstractEntity> implements ReportManager {

	@Autowired
	ReportDao reportDao;

	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchInquiriesPendingTasks(InquiryBean inquiryBean) {
		List<InquiryTO> inquiryPendingTasksList = null;
		InquiryTO inquiryPendingTask = null;
		List<InquiryBean> inquiryBeanList = reportDao.fetchInquiriesPendingTasks(inquiryBean);

		if (inquiryBeanList.isEmpty())
			return inquiryPendingTasksList;

		inquiryBean = null;
		inquiryPendingTasksList = new ArrayList<InquiryTO>();

		for (int index = 0; index < inquiryBeanList.size(); index++) {
			inquiryPendingTask = mapper.map(inquiryBeanList.get(index), InquiryTO.class);
			while (inquiryPendingTask.getInquiryNo().equals(inquiryBeanList.get(index).getInquiryNo())) {
				if (inquiryBeanList.get(index).getAssignedStaffId() != null
						&& inquiryBeanList.get(index).getAssignedStaffId() == inquiryBeanList.get(index).getStaffId())
					inquiryPendingTask.setInquiryAssigne(inquiryBeanList.get(index).getStaffFullName());

				if (inquiryBeanList.get(index).getSourceStaffId() == inquiryBeanList.get(index).getStaffId())
					inquiryPendingTask.setSourceStaff1(inquiryBeanList.get(index).getStaffFullName());
				else if (inquiryBeanList.get(index).getSourceStaffId2() == inquiryBeanList.get(index).getStaffId())
					inquiryPendingTask.setSourceStaff2(inquiryBeanList.get(index).getStaffFullName());
				else if (inquiryBeanList.get(index).getSourceStaffId3() == inquiryBeanList.get(index).getStaffId())
					inquiryPendingTask.setSourceStaff3(inquiryBeanList.get(index).getStaffFullName());
				else if (inquiryBeanList.get(index).getSourceStaffId4() == inquiryBeanList.get(index).getStaffId())
					inquiryPendingTask.setSourceStaff4(inquiryBeanList.get(index).getStaffFullName());

				index++;
				if (index >= inquiryBeanList.size())
					break;

			}

			index--;
			inquiryPendingTasksList.add(inquiryPendingTask);

		}

		return inquiryPendingTasksList;
	}

	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchVehicleCarStockForSale(InquiryBean inquiryBean) {
		List<InquiryTO> inquiryPendingTasksList = null;
		InquiryTO inquiryPendingTask = null;
		List<InquiryBean> inquiryBeanList = reportDao.fetchVehicleCarStockForSale(inquiryBean);

		if (inquiryBeanList.isEmpty())
			return inquiryPendingTasksList;
		
//		HashMap<Long, String> vehicleInformationList =  reportDao.fetchVehicleInfo(inquiryBean);
		inquiryBean = null;
		inquiryPendingTasksList = new ArrayList<InquiryTO>();

		for (int index = 0; index < inquiryBeanList.size(); index++) {
			inquiryPendingTask = mapper.map(inquiryBeanList.get(index), InquiryTO.class);
//			String vehicleInfo = vehicleInformationList.get(inquiryBeanList.get(index).getVehicleId());
//			inquiryPendingTask.setVehicleInfo(vehicleInfo);
			while (inquiryPendingTask.getInquiryNo().equals(inquiryBeanList.get(index).getInquiryNo())) {
				if (inquiryBeanList.get(index).getAssignedStaffId() != null
						&& inquiryBeanList.get(index).getAssignedStaffId() == inquiryBeanList.get(index).getStaffId())
					inquiryPendingTask.setInquiryAssigne(inquiryBeanList.get(index).getStaffFullName());

				if (inquiryBeanList.get(index).getSourceStaffId() == inquiryBeanList.get(index).getStaffId())
					inquiryPendingTask.setSourceStaff1(inquiryBeanList.get(index).getStaffFullName());
				else if (inquiryBeanList.get(index).getSourceStaffId2() == inquiryBeanList.get(index).getStaffId())
					inquiryPendingTask.setSourceStaff2(inquiryBeanList.get(index).getStaffFullName());
				else if (inquiryBeanList.get(index).getSourceStaffId3() == inquiryBeanList.get(index).getStaffId())
					inquiryPendingTask.setSourceStaff3(inquiryBeanList.get(index).getStaffFullName());
				else if (inquiryBeanList.get(index).getSourceStaffId4() == inquiryBeanList.get(index).getStaffId())
					inquiryPendingTask.setSourceStaff4(inquiryBeanList.get(index).getStaffFullName());

				index++;
				if (index >= inquiryBeanList.size())
					break;

			}

			index--;
			inquiryPendingTasksList.add(inquiryPendingTask);

		}

		return inquiryPendingTasksList;
	}

	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchBuisnessDetails(InquiryBean inquiryBean) {
		return reportDao.fetchBuisnessDetails(inquiryBean);
	}
	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchBankLoanDetails(BankLoanDetailsReportBean bankLoanDetailsReportBean) {
		return reportDao.fetchBankLoanDetails(bankLoanDetailsReportBean);
	}
	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchDedupeReportData(BankLoanDetailsReportBean bankLoanDetailsReportBean) {
		return reportDao.fetchDedupeReportData(bankLoanDetailsReportBean);
	}
	

	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchPendingRCDetails(InquiryBean inquiryBean) {
		return reportDao.fetchPendingRCDetails(inquiryBean);
	}
	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchLoanBuisnessOnADayReportData(BankLoanDetailsReportBean loanBuisnessOnADayReportBean) {
		return reportDao.fetchLoanBuisnessOnADayReportData(loanBuisnessOnADayReportBean);
	}

	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchRtoAgentCharges(InquiryBean rtoAgentChargesBean) {
		List<InquiryTO> rtoAgentChargesList = new ArrayList<InquiryTO>();
		InquiryTO rtoAgentTotalCharges = new InquiryTO();
		rtoAgentTotalCharges.setAggTaskName("TOTAL");
		
		List<InquiryTO> existigRtoAgentChargesList = reportDao.fetchRtoAgentCharges(rtoAgentChargesBean);
		int aggregatorId = 0;
		for (InquiryTO rtoAgentCharges : existigRtoAgentChargesList) {
			if (rtoAgentCharges.getAggregatorId() == aggregatorId)
				rtoAgentTotalCharges.setAggTaskCharges(rtoAgentTotalCharges.getAggTaskCharges()+rtoAgentCharges.getAggTaskCharges()) ;
			else if(aggregatorId != 0){
				rtoAgentChargesList.add(rtoAgentTotalCharges);
				rtoAgentTotalCharges = new InquiryTO();
				rtoAgentTotalCharges.setAggTaskName("TOTAL");
				rtoAgentTotalCharges.setAggTaskCharges(rtoAgentCharges.getAggTaskCharges()) ;
			}
			else
				rtoAgentTotalCharges.setAggTaskCharges(rtoAgentCharges.getAggTaskCharges()) ;

			aggregatorId = rtoAgentCharges.getAggregatorId();
			rtoAgentChargesList.add(rtoAgentCharges);
		}
		rtoAgentChargesList.add(rtoAgentTotalCharges);

		return rtoAgentChargesList;

	}

	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchCarAndCashDetails(BankLoanDetailsReportBean bankLoanDetailsReportBean) {
		return reportDao.fetchCarAndCashDetails(bankLoanDetailsReportBean);
	}
}
