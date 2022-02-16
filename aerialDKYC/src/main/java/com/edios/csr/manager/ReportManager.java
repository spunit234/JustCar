package com.edios.csr.manager;

import java.util.List;

import com.edios.csr.bean.BankLoanDetailsReportBean;
import com.edios.csr.bean.InquiryBean;
import com.edios.csr.entity.to.InquiryTO;

public interface ReportManager {

	List<InquiryTO> fetchInquiriesPendingTasks(InquiryBean inquiryBean);

	List<InquiryTO> fetchVehicleCarStockForSale(InquiryBean inquiryBean);

	List<InquiryTO> fetchBuisnessDetails(InquiryBean inquiryBean);

	List<InquiryTO> fetchBankLoanDetails(BankLoanDetailsReportBean bankLoanDetailsReportBean);

	List<InquiryTO> fetchDedupeReportData(BankLoanDetailsReportBean bankLoanDetailsReportBean);

	List<InquiryTO> fetchPendingRCDetails(InquiryBean inquiryBean);

	List<InquiryTO> fetchLoanBuisnessOnADayReportData(BankLoanDetailsReportBean loanBuisnessOnADayReportBean);

	List<InquiryTO> fetchRtoAgentCharges(InquiryBean rtoAgentChargesBean);

	List<InquiryTO> fetchCarAndCashDetails(BankLoanDetailsReportBean bankLoanDetailsReportBean);

}
