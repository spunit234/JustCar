package com.edios.csr.dao;

import java.util.HashMap;
import java.util.List;

import com.edios.csr.bean.BankLoanDetailsReportBean;
import com.edios.csr.bean.InquiryBean;
import com.edios.csr.entity.to.InquiryTO;

public interface ReportDao {

	List<InquiryBean> fetchInquiriesPendingTasks(InquiryBean inquiryBean);

	List<InquiryBean> fetchVehicleCarStockForSale(InquiryBean inquiryBean);

	HashMap<Long, String> fetchVehicleInfo(InquiryBean inquiryBean);

	List<InquiryTO> fetchBuisnessDetails(InquiryBean inquiryBean);

	List<InquiryTO> fetchBankLoanDetails(BankLoanDetailsReportBean bankLoanDetailsReportBean);

	List<InquiryTO> fetchDedupeReportData(BankLoanDetailsReportBean bankLoanDetailsReportBean);

	List<InquiryTO> fetchPendingRCDetails(InquiryBean inquiryBean);

	List<InquiryTO> fetchLoanBuisnessOnADayReportData(BankLoanDetailsReportBean loanBuisnessOnADayDetailsList);

	List<InquiryTO> fetchRtoAgentCharges(InquiryBean rtoAgentChargesBean);

	List<InquiryTO> fetchCarAndCashDetails(BankLoanDetailsReportBean bankLoanDetailsReportBean);

}
