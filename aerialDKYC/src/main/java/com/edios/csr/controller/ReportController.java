package com.edios.csr.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edios.cdf.controller.AbstractController;
import com.edios.csr.bean.BankLoanDetailsReportBean;
import com.edios.csr.bean.InquiryBean;
import com.edios.csr.entity.to.InquiryTO;
import com.edios.csr.manager.ReportManager;

@RestController
public class ReportController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	ReportManager reportManager;

	@PostMapping("/fetch-inquiries-pending-tasks-report")
	public ResponseEntity<List<InquiryTO>> fetchInquiriesPendingTasks(@RequestBody InquiryBean inquiryBean) {
		List<InquiryTO> inquiriesPendingTasksList = null;

		if (inquiryBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			inquiriesPendingTasksList = reportManager.fetchInquiriesPendingTasks(inquiryBean);
			return new ResponseEntity<List<InquiryTO>>(inquiriesPendingTasksList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping("/fetch-bank-loan-details-report")
	public ResponseEntity<List<InquiryTO>> fetchBankLoanDetails(@RequestBody BankLoanDetailsReportBean bankLoanDetailsReportBean) {
		List<InquiryTO> inquiriesPendingTasksList = null;
			inquiriesPendingTasksList = reportManager.fetchBankLoanDetails(bankLoanDetailsReportBean);
			return new ResponseEntity<List<InquiryTO>>(inquiriesPendingTasksList, HttpStatus.OK);
		}

	@PostMapping("/fetch-vehicle-car-stock-for-sale-report")
	public ResponseEntity<List<InquiryTO>> fetchVehicleCarStockForSale(@RequestBody InquiryBean inquiryBean) {
		List<InquiryTO> inquiriesPendingTasksList = null;

		if (inquiryBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			inquiriesPendingTasksList = reportManager.fetchVehicleCarStockForSale(inquiryBean);
			return new ResponseEntity<List<InquiryTO>>(inquiriesPendingTasksList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@PostMapping("/fetch-buisness-details-report")
	public ResponseEntity<List<InquiryTO>> fetchBuisnessDetails(@RequestBody InquiryBean inquiryBean) {
		List<InquiryTO> inquiriesPendingTasksList = null;

		if (inquiryBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			inquiriesPendingTasksList = reportManager.fetchBuisnessDetails(inquiryBean);
			return new ResponseEntity<List<InquiryTO>>(inquiriesPendingTasksList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	@PostMapping("/fetch-de-dupe-report")
	public ResponseEntity<List<InquiryTO>> fetchDedupeReportData(@RequestBody BankLoanDetailsReportBean bankLoanDetailsReportBean) {
		List<InquiryTO> dedupeList = null;
		dedupeList = reportManager.fetchDedupeReportData(bankLoanDetailsReportBean);
			return new ResponseEntity<List<InquiryTO>>(dedupeList, HttpStatus.OK);
		}
	

	@PostMapping("/fetch-pending-rc-details-report")
	public ResponseEntity<List<InquiryTO>> fetchPendingRCDetails(@RequestBody InquiryBean inquiryBean) {
		List<InquiryTO> inquiriesPendingTasksList = null;

		if (inquiryBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			inquiriesPendingTasksList = reportManager.fetchPendingRCDetails(inquiryBean);
			return new ResponseEntity<List<InquiryTO>>(inquiriesPendingTasksList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	@PostMapping("/fetch-loan-buisness-on-a-day-report")
	public ResponseEntity<List<InquiryTO>> fetchLoanBuisnessOnADayReportData(@RequestBody BankLoanDetailsReportBean loanBuisnessOnADayReportBean) {
		List<InquiryTO> dedupeList = null;
		dedupeList = reportManager.fetchLoanBuisnessOnADayReportData(loanBuisnessOnADayReportBean);
			return new ResponseEntity<List<InquiryTO>>(dedupeList, HttpStatus.OK);
		}
	
	
	@PostMapping("/fetch-rto-agent-charges-report")
	public ResponseEntity<List<InquiryTO>> fetchRtoAgentCharges(@RequestBody InquiryBean rtoAgentChargesBean) {
		List<InquiryTO> rtoAgentChargesList = null;

		if (rtoAgentChargesBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			rtoAgentChargesList = reportManager.fetchRtoAgentCharges(rtoAgentChargesBean);
			return new ResponseEntity<List<InquiryTO>>(rtoAgentChargesList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	@PostMapping("/fetch-car-cash-report")
	public ResponseEntity<List<InquiryTO>> fetchCarAndCashDetails(@RequestBody BankLoanDetailsReportBean loanBuisnessOnADayReportBean) {
		List<InquiryTO> dedupeList = null;
		dedupeList = reportManager.fetchCarAndCashDetails(loanBuisnessOnADayReportBean);
			return new ResponseEntity<List<InquiryTO>>(dedupeList, HttpStatus.OK);
		}
	
}
