package com.edios.csr.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;

import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.edios.cdf.controller.SendMailUsingAuthentication;
import com.edios.cdf.manager.impl.AbstractManagerImpl;
import com.edios.cdf.manager.impl.StorageLocationUtil;
import com.edios.cdf.util.AppConstants;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.util.StorageData;
import com.edios.cdf.util.TransactionData;
import com.edios.csr.bean.CustomerAddressBean;
import com.edios.csr.bean.CustomerBean;
import com.edios.csr.bean.InquiryBean;
import com.edios.csr.bean.InquiryImagesBean;
import com.edios.csr.bean.InquiryNotesBean;
import com.edios.csr.bean.LoanAddressBean;
import com.edios.csr.bean.LoanBean;
import com.edios.csr.bean.LoanCoborrowerBean;
import com.edios.csr.bean.LoanCommissionBean;
import com.edios.csr.bean.LoanCrossSaleBean;
import com.edios.csr.bean.LoanMembersBean;
import com.edios.csr.bean.LoanNotesBean;
import com.edios.csr.bean.LoanReferencesBean;
import com.edios.csr.bean.LoanStatusBean;
import com.edios.csr.bean.LoanSurityChequesBean;
import com.edios.csr.bean.LoanTransStatusBean;
import com.edios.csr.bean.LoanVehicleInvoiceBean;
import com.edios.csr.bean.ManageLoansBean;
import com.edios.csr.bean.ProjectDocumentBean;
import com.edios.csr.bean.StaffBean;
import com.edios.csr.bean.VehiclesBean;
import com.edios.csr.dao.InquiryDao;
import com.edios.csr.entity.CustomerAddressEntity;
import com.edios.csr.entity.CustomerEntity;
import com.edios.csr.entity.InquiriesEntity;
import com.edios.csr.entity.InquiriesNotesEntity;
import com.edios.csr.entity.InquiryImagesEntity;
import com.edios.csr.entity.LoanAddressEntity;
import com.edios.csr.entity.LoanCoborrowerEntity;
import com.edios.csr.entity.LoanCommissionEntity;
import com.edios.csr.entity.LoanCrossSaleEntity;
import com.edios.csr.entity.LoanEntity;
import com.edios.csr.entity.LoanMembersEntity;
import com.edios.csr.entity.LoanNotesEntity;
import com.edios.csr.entity.LoanReferencesEntity;
import com.edios.csr.entity.LoanStatusEntity;
import com.edios.csr.entity.LoanSurityChequesEntity;
import com.edios.csr.entity.LoanTransStatusEntity;
import com.edios.csr.entity.LoanVehicleInvoiceEntity;
import com.edios.csr.entity.ProjectDocumentEntity;
import com.edios.csr.entity.VehicelsEntity;
import com.edios.csr.entity.to.CustomersTO;
import com.edios.csr.entity.to.DealerExecutiveTo2;
import com.edios.csr.entity.to.DealerTo2;
import com.edios.csr.entity.to.InquiryDocumentTO;
import com.edios.csr.entity.to.InquiryNotesTO;
import com.edios.csr.entity.to.InquiryTO;
import com.edios.csr.entity.to.LoanCoborrowerTo;
import com.edios.csr.entity.to.LoanCommissionTo;
import com.edios.csr.entity.to.LoanCrossSaleTo;
import com.edios.csr.entity.to.LoanNotesTo;
import com.edios.csr.entity.to.LoanStatusTo;
import com.edios.csr.entity.to.LoanSurityChequesTo;
import com.edios.csr.entity.to.LoanTo;
import com.edios.csr.entity.to.LoanVehicleInvoiceTo;
import com.edios.csr.entity.to.MailDataTO;
import com.edios.csr.entity.to.StaffTo;
import com.edios.csr.entity.to.VehicleTo;
import com.edios.csr.entity.to.VendorDocumentTO;
import com.edios.csr.manager.InquiryManager;

@Service("inquiryManager")
public class InquiryManagerImpl extends AbstractManagerImpl<CustomerBean, CustomerEntity> implements InquiryManager {

	@Autowired
	InquiryDao inquiryDao;

	@Autowired
	MessageSource messageSource;
	@Autowired
	StorageLocationUtil storageLocationUtil;

	@Override
	@Transactional("db1Tx")
	public List<CustomersTO> fetchcustomerData(CustomerAddressBean customerAddressBean) {

		return inquiryDao.fetchcustomerData(customerAddressBean);
	}

	@Override
	@Transactional("db1Tx")
	public List<StaffTo> fetchStaffData(StaffBean staffBean) {
		return inquiryDao.fetchStaffData(staffBean);
	}

	private void setAuditInfo(InquiryBean inquiryBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			inquiryBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			inquiryBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			inquiryBean.setCreatedDate(new Date());
		} else {
			inquiryBean.setTransactionCount(inquiryBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			inquiryBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			inquiryBean.setLastModifiedDate(new Date());
		}
	}

	@Override
	@Transactional("db1Tx")
	public String addCustomer(CustomerBean customerBean) {
		String resultString = "";
		setAuditInfo(customerBean, "newFlag");
		try {
			CustomerEntity customerEntity = mapper.map(customerBean, CustomerEntity.class);
			resultString = inquiryDao.addCustomer(customerEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	private void setAuditInfo(CustomerBean customerBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			customerBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			customerBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			customerBean.setCreatedDate(new Date());
		} else {
			customerBean.setTransactionCount(customerBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			customerBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			customerBean.setLastModifiedDate(new Date());
		}
	}

	@Override
	@Transactional("db1Tx")
	public String addInquiryVehicle(VehiclesBean inquiryVehiclesBean) {
		String resultString = "";
		boolean resultFlag = false;
		setAuditInfo(inquiryVehiclesBean, "newFlag");
		try {
			VehicelsEntity inquiriesVehicelsEntity = mapper.map(inquiryVehiclesBean, VehicelsEntity.class);
			resultFlag = inquiryDao.addInquiryVehicle(inquiriesVehicelsEntity);
			if (resultFlag) {
				resultString = "Added";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	private void setAuditInfo(VehiclesBean inquiryVehiclesBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			inquiryVehiclesBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			inquiryVehiclesBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			inquiryVehiclesBean.setCreatedDate(new Date());
		} else {
			inquiryVehiclesBean
					.setTransactionCount(inquiryVehiclesBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			inquiryVehiclesBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			inquiryVehiclesBean.setLastModifiedDate(new Date());
		}
	}

	@Override
	@Transactional("db1Tx")
	public String addInquiry(InquiryBean inquiryBean) {
		String resultString = "";
		setAuditInfo(inquiryBean, "newFlag");
		try {
			
			InquiriesEntity inquiriesEntity = mapper.map(inquiryBean, InquiriesEntity.class);
			inquiriesEntity.setStatusDate(new Date());
			resultString = inquiryDao.addInquiry(inquiriesEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	private String createHtmlForEmailStaff(List<MailDataTO> mailDataTO) {
		StringBuilder tableBuilder = new StringBuilder();
		try {
			tableBuilder.append("<body style=\"font-family:Arial\"> "
					+ "<STYLE TYPE=\"text/css\"> * {  font-family: Arial;  }  table { table-layout:fixed;	word-break:break-all;}"
					+ " TH{font-family: Arial; font-size:14;}TD{font-family: Arial; font-size: 14; } p{font-family: Arial; font-size: 14;}"
					+ "</STYLE> Auto Generated Inquiry Email " + ""
					+ " <BR/><BR/><table border=\"1\" cellpadding=\"3\" cellspacing=\"2\">"
					+ "<tr><td width =\"250px\"><b>Inquiry Number</b></td>" + "<td  width =\"700px\">"
					+ mailDataTO.get(0).getInquiryNo() + ""
					+ "</td></tr><tr><td width =\"250px\"><b>Inquiry Date</b></td>" + "<td  width =\"700px\">"
					+ mailDataTO.get(0).getInquiryDate() + ""
					+ "</td></tr><tr><td width =\"250px\"><b>Product Type</b></td>" + "<td  width =\"700px\">"
					+ mailDataTO.get(0).getInquiryType() + ""
					+ "</td></tr><tr><td width =\"250px\"><b>Phone Number</b></td>" + "<td  width =\"700px\">"
					+ mailDataTO.get(0).getContactNumber() + "" + "</td></tr><tr><td width =\"250px\"><b>City</b></td>"
					+ "<td  width =\"700px\">" + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableBuilder.toString();
	}

	@Override
	@Transactional("db1Tx")
	public String sendMail(long id) {
		String emailCCRecipientsAddress = "";
		String[] emailRecipients1;
		List<MailDataTO> mailDataTO = inquiryDao.getMailData(id);
		if (mailDataTO.get(0).getInquiryType().equalsIgnoreCase("Help & Support")) {
			String[] emailRecipients = { messageSource.getMessage("emailPankjGoyal", null, "", Locale.US) };
//			String[] emailRecipients = {"hs202119@gmail.com"};
			emailRecipients1 = emailRecipients;
		} else {
			String[] emailRecipients = { mailDataTO.get(0).getEmailAddress(), mailDataTO.get(0).getEmailAddress1() };
			emailRecipients1 = emailRecipients;
		}
		SendMailUsingAuthentication sendMail = new SendMailUsingAuthentication();
		try {
			sendMail.postMail(emailRecipients1, emailCCRecipientsAddress, createHtmlForEmailStaff(mailDataTO),
					"Inquiry Number(" + mailDataTO.get(0).getInquiryNo() + ") Notification Mail");
			this.sendMailToCustomer(mailDataTO);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emailCCRecipientsAddress;

	}

	private void sendMailToCustomer(List<MailDataTO> mailDataTO) {
		String emailCCRecipientsAddress = "";
		String[] emailRecipients = { mailDataTO.get(0).getCustomerEmail() };
		SendMailUsingAuthentication sendMail = new SendMailUsingAuthentication();
		try {
			sendMail.postMail(emailRecipients, emailCCRecipientsAddress, createHtmlForEmailCustomer(mailDataTO),
					"Inquiry Number(" + mailDataTO.get(0).getInquiryNo() + ") Notification Mail");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private String createHtmlForEmailCustomer(List<MailDataTO> mailDataTO) {
		StringBuilder tableBuilder = new StringBuilder();
		try {
			tableBuilder.append("<body style=\"font-family:Arial\"> "
					+ "<STYLE TYPE=\"text/css\"> * {  font-family: Arial;  }  table { table-layout:fixed;	word-break:break-all;}"
					+ " TH{font-family: Arial; font-size:14;}TD{font-family: Arial; font-size: 14; } p{font-family: Arial; font-size: 14;}"
					+ "</STYLE> Auto Generated Inquiry Email " + ""
					+ " <BR/><BR/><table border=\"1\" cellpadding=\"3\" cellspacing=\"2\">"
					+ "<tr><td width =\"250px\"><b>Inquiry Number</b></td>" + "<td  width =\"700px\">"
					+ mailDataTO.get(0).getInquiryNo() + ""
					+ "</td></tr><tr><td width =\"250px\"><b>Inquiry Date</b></td>" + "<td  width =\"700px\">"
					+ mailDataTO.get(0).getInquiryDate() + ""
					+ "</td></tr><tr><td width =\"250px\"><b>Assignee Name</b></td>" + "<td  width =\"700px\">"
					+ mailDataTO.get(0).getAssigneeName() + ""
					+ "</td></tr><tr><td width =\"250px\"><b>Assignee Contact Number</b></td>"
					+ "<td  width =\"700px\">" + mailDataTO.get(0).getAssigneeContact() + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableBuilder.toString();
	}

	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchInquiryData(InquiryBean inquiryBean) {
		return inquiryDao.fetchInquiryData(inquiryBean);
	}

	@Override
	@Transactional("db1Tx")
	public String inquiryNotesEntry(InquiryNotesBean inquiryNotesBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			inquiryNotesBean.setTransactionCount(1L);
			inquiryNotesBean.setCreatedDate(new Date());
			inquiryNotesBean.setRecordType('I');
			inquiryNotesBean.setNoteDateTime(new Date());

			InquiriesNotesEntity inquiriesNotesEntity = mapper.map(inquiryNotesBean, InquiriesNotesEntity.class);
			resultFlag = inquiryDao.inquiryNotesEntry(inquiriesNotesEntity);
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
	public List<InquiryNotesTO> getInquiryNotes(PayloadBean payloadBean) {
		return inquiryDao.getInquiryNotes(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public InquiryBean findInquiryById(PayloadBean payloadBean) {
		InquiriesEntity inquiriesEntity = inquiryDao.findInquiryById(payloadBean);
		InquiryBean inquiryBean = mapper.map(inquiriesEntity, InquiryBean.class);
		return inquiryBean;
	}

	@Override
	@Transactional("db1Tx")
	public List<CustomersTO> editCustomer(PayloadBean payloadbean) {
		return inquiryDao.editCustomer(payloadbean);
	}

	@Override
	@Transactional("db1Tx")
	public List<VehicleTo> editVehicle(PayloadBean payloadbean) {
		return inquiryDao.editVehicle(payloadbean);
	}

	@Override
	@Transactional("db1Tx")
	public String updateVehicle(VehiclesBean vehiclesBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			TransactionData latestData = inquiryDao.fetchTransactionDataById(vehiclesBean.getVehicleId());
			if (latestData.getTransactionCount() > (vehiclesBean.getTransactionCount())) {
				return "TransactionFailed";
			}
			if (latestData.getRecordType() == 'D') {
				return "recordDeleted";
			}
			setAuditInfo1(vehiclesBean, "editFlag");

			VehicelsEntity vehicelsEntity = mapper.map(vehiclesBean, VehicelsEntity.class);
			resultFlag = inquiryDao.updateVehicle(vehicelsEntity);
			if (resultFlag) {
				return "UPDATED";
			}
		} catch (MappingException e) {
			e.printStackTrace();
		}
		return resultString;
	}

	private void setAuditInfo1(VehiclesBean vehiclesBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			vehiclesBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			vehiclesBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			vehiclesBean.setCreatedDate(new Date());
		} else {
			vehiclesBean.setTransactionCount(vehiclesBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			vehiclesBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			vehiclesBean.setLastModifiedDate(new Date());
		}
	}

	@Override
	@Transactional("db1Tx")
	public String addLoan(LoanBean loanBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			loanBean.setTransactionCount(1L);
			loanBean.setCreatedDate(new Date());
			loanBean.setRecordType('I');
			resultFlag = inquiryDao.isLoanNoExist(loanBean.getLoanNo());
			if (resultFlag) {
				return "LoanNoAlreadyExist";
			}
			LoanEntity loanEntity = mapper.map(loanBean, LoanEntity.class);
			resultString = inquiryDao.addLoan(loanEntity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public List<LoanTo> editloan(PayloadBean payloadbean) {
		return inquiryDao.editloan(payloadbean);
	}

	@Override
	@Transactional("db1Tx")
	public String updateLoan(LoanBean loanBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			TransactionData latestData = inquiryDao.fetchTransactionDataById1(loanBean.getLoanId());
			if (latestData.getTransactionCount() > (loanBean.getTransactionCount())) {
				return "TransactionFailed";
			}
			loanBean.setTransactionCount(loanBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			loanBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			loanBean.setLastModifiedDate(new Date());

			LoanEntity loanEntity = mapper.map(loanBean, LoanEntity.class);
			resultFlag = inquiryDao.updateLoan(loanEntity);
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
	public List<VendorDocumentTO> fetchProjectDocumentDetails(Long projectId) {
		return inquiryDao.fetchProjectDocumentDetails(projectId);
	}

	@Override
	@Transactional("db1Tx")
	public String uploadProjectDocument(ProjectDocumentBean ProjectDocumentBean, MultipartFile file) {
		String resultString = "";

		setAuditInfoProjectDocument(ProjectDocumentBean, "newFlag");
		ProjectDocumentEntity ProjectDocumentEntity = mapper.map(ProjectDocumentBean, ProjectDocumentEntity.class);
		System.out.println(ProjectDocumentBean.toString());
		Long pkId = inquiryDao.uploadProjectDocument(ProjectDocumentEntity);

		boolean result = false;
		if (pkId != null && file != null) {
			String locationName = messageSource.getMessage("locationForProjectDocument", null, "", Locale.US);
			StorageData storageData = storageLocationUtil.addDocument(pkId, locationName, file);
			result = inquiryDao.updateProjectDocumentEntity(pkId, storageData.getStorageId(), storageData.getFileName(),
					ProjectDocumentBean.getCreatedBy());
			if (result)
				return "ADDED";
		}
		if (pkId != null) {
			return "ADDED";
		}
		return resultString;
	}

	private void setAuditInfoProjectDocument(ProjectDocumentBean ProjectDocumentBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			ProjectDocumentBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			ProjectDocumentBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			ProjectDocumentBean.setCreatedDate(new Date());
		} else {
			ProjectDocumentBean
					.setTransactionCount(ProjectDocumentBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			ProjectDocumentBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			ProjectDocumentBean.setLastModifiedDate(new Date());

		}
	}

	@Override
	@Transactional("db1Tx")
	public String loanNotesEntry(LoanNotesBean loanNotesBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			loanNotesBean.setTransactionCount(1L);
			loanNotesBean.setCreatedDate(new Date());
			loanNotesBean.setRecordType('I');
			loanNotesBean.setNoteDateTime(new Date());

			LoanNotesEntity loanNotesEntity = mapper.map(loanNotesBean, LoanNotesEntity.class);
			resultFlag = inquiryDao.loanNotesEntry(loanNotesEntity);
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
	public List<LoanNotesTo> getLoanNotes(PayloadBean payloadBean) {
		return inquiryDao.getLoanNotes(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public String saveLoanCrossSale(LoanCrossSaleBean loanCrossSaleBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			loanCrossSaleBean.setTransactionCount(1L);
			loanCrossSaleBean.setCreatedDate(new Date());
			loanCrossSaleBean.setRecordType('I');

			LoanCrossSaleEntity loanCrossSaleEntity = mapper.map(loanCrossSaleBean, LoanCrossSaleEntity.class);
			resultFlag = inquiryDao.saveLoanCrossSale(loanCrossSaleEntity);
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
	public List<LoanCrossSaleTo> getLoanCrossSale(PayloadBean payloadBean) {
		return inquiryDao.getLoanCrossSale(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public String saveLoanSurityCheques(LoanSurityChequesBean loanSurityChequesBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			loanSurityChequesBean.setTransactionCount(1L);
			loanSurityChequesBean.setCreatedDate(new Date());
			loanSurityChequesBean.setRecordType('I');

			LoanSurityChequesEntity loanSurityChequesEntity = mapper.map(loanSurityChequesBean,
					LoanSurityChequesEntity.class);
			resultFlag = inquiryDao.saveLoanSurityCheques(loanSurityChequesEntity);
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
	public List<LoanSurityChequesTo> getLoanSurityCheques(PayloadBean payloadBean) {
		return inquiryDao.getLoanSurityCheques(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public String saveLoanTransStatus(LoanTransStatusBean loanTransStatusBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			loanTransStatusBean.setTransactionCount(1L);
			loanTransStatusBean.setCreatedDate(new Date());
			loanTransStatusBean.setRecordType('I');
			loanTransStatusBean.setLoanTransDatetime(new Date());
			LoanTransStatusEntity loanTransStatusEntity = mapper.map(loanTransStatusBean, LoanTransStatusEntity.class);
			resultFlag = inquiryDao.saveLoanTransStatus(loanTransStatusEntity);
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
	public String deleteLoanCrossSell(PayloadBean payloadBean) {
		Long id = Long.parseLong(payloadBean.getSearchParameter());
		TransactionData latestData = inquiryDao.fetchTransactionDataById2(id);
		if (latestData.getTransactionCount() > (1L)) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}

		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.deleteLoanCrossSell(payloadBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String deleteLoanSurityCheques(PayloadBean payloadBean) {
		Long id = Long.parseLong(payloadBean.getSearchParameter());
		TransactionData latestData = inquiryDao.fetchTransactionDataById3(id);
		if (latestData.getTransactionCount() > (1L)) {
			return "TransactionFailed";
		}
		if (latestData.getRecordType() == 'D') {
			return "recordDeleted";
		}

		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.deleteLoanSurityCheques(payloadBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	// punit
	@Override
	@Transactional("db1Tx")
	public String updateloanStatus(LoanStatusBean loanstatusbean) {
		String resultString = "";
		boolean resultFlag = false;
		SimpleDateFormat format;

		if (loanstatusbean.getDateForFormatting().length() > 23) {
			format = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
		} else {
			format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		}

		Date date = null;
		try {
			date = format.parse(loanstatusbean.getDateForFormatting());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		loanstatusbean.setOpenDate(date);
		setAuditInfo1(loanstatusbean, "newFlag");
		try {
			LoanStatusEntity loanstatusEntity = mapper.map(loanstatusbean, LoanStatusEntity.class);
			resultFlag = inquiryDao.updateloanStatus(loanstatusEntity);
			if (resultFlag) {
				resultString = "Updated";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultString;

	}

	@Override
	@Transactional("db1Tx")
	public String wvokupdateloanStatus(LoanStatusBean loanstatusbean) {
		String resultString = "";
		boolean resultFlag = false;
		setAuditInfo1(loanstatusbean, "updateFlag");
		SimpleDateFormat format;

		if (loanstatusbean.getDateForFormatting().length() > 23) {
			format = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
		} else {
			format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		}

		Date date = null;
		try {
			date = format.parse(loanstatusbean.getDateForFormatting());
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
		loanstatusbean.setOpenDate(date);

		try {
			LoanStatusEntity loanstatusEntity = mapper.map(loanstatusbean, LoanStatusEntity.class);
			resultFlag = inquiryDao.wvokupdateloanStatus(loanstatusEntity);
			if (resultFlag) {
				resultString = "Updated";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public List<LoanStatusTo> fetchloanstatus(int vehicleId) {
		List<LoanStatusTo> lsb = inquiryDao.fetchloanstatus(vehicleId);
		return lsb;
	}

	@Override
	@Transactional("db1Tx")
	public String delloanStatus(int vid) {
		String ok = "";
		inquiryDao.delloanStatus(vid);
		return ok;
	}

	// code
	private void setAuditInfo1(LoanStatusBean inquiryVehiclesBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			inquiryVehiclesBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			inquiryVehiclesBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			inquiryVehiclesBean.setCreatedDate(new Date());
		} else {
			inquiryVehiclesBean.setTransactionCount(inquiryVehiclesBean.getTransactionCount() + 1);
			inquiryVehiclesBean.setRecordType(AppConstants.RECORDTYPE_UPDATE);
			inquiryVehiclesBean.setLastModifiedDate(new Date());
		}
	}

	@Override
	@Transactional("db1Tx")
	public String saveTransactionNo(InquiryBean inquiryBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.saveTransactionNo(inquiryBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String saveVehicleTransactionDetail(VehiclesBean vehiclesBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.saveVehicleTransactionDetail(vehiclesBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchInquiryNo(InquiryBean inquiryBean) {
		return inquiryDao.fetchInquiryNo(inquiryBean);
	}

	@Override
	@Transactional("db1Tx")
	public List<InquiryTO> fetchInquiryLoanData(ManageLoansBean manageLoansBean) {
		return inquiryDao.fetchInquiryLoanData(manageLoansBean);
	}

	@Override
	@Transactional("db1Tx")
	public String uploadInquiryImages(InquiryImagesBean inquiryImagesBean, MultipartFile file) {
		String resultString = "";

		setAuditInfoProjectDocument(inquiryImagesBean, "newFlag");
		InquiryImagesEntity inquiryImagesEntity = mapper.map(inquiryImagesBean, InquiryImagesEntity.class);
		Long pkId = inquiryDao.uploadInquiryImages(inquiryImagesEntity);

		boolean result = false;
		if (pkId != null && file != null) {
			String locationName = messageSource.getMessage("locationForInquiryImages", null, "", Locale.US);
			StorageData storageData = storageLocationUtil.addDocument(pkId, locationName, file);
			result = inquiryDao.updateInquiryImages(pkId, storageData.getStorageId(), storageData.getFileName(),
					inquiryImagesBean.getCreatedBy());
			if (result)
				return "ADDED";
		}
		if (pkId != null) {
			return "ADDED";
		}
		return resultString;
	}

	private void setAuditInfoProjectDocument(InquiryImagesBean inquiryImagesBean, String string) {
		if (string.equalsIgnoreCase("newFlag")) {
			inquiryImagesBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
			inquiryImagesBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			inquiryImagesBean.setCreatedDate(new Date());
		} else {
			inquiryImagesBean
					.setTransactionCount(inquiryImagesBean.getTransactionCount() + AppConstants.TRANSACTION_BEGIN);
			inquiryImagesBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
			inquiryImagesBean.setLastModifiedDate(new Date());

		}
	}

	@Override
	@Transactional("db1Tx")
	public List<InquiryDocumentTO> fetchInquiryDocumentDetails(Long projectId) {
		return inquiryDao.fetchInquiryDocumentDetails(projectId);
	}

	@Override
	@Transactional("db1Tx")
	public String deleteDocument(DeleteRecords deleteRecords) {
		String resultString = "";
		boolean resultFlag = false;
		Long l = Long.valueOf(deleteRecords.getModifiedBy());

		resultFlag = inquiryDao.deleteDocument(deleteRecords.getId(), l);
		if (resultFlag)
			return "DELETED";
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public List<DealerTo2> fetchDealerName(PayloadBean payloadBean) {
		return inquiryDao.fetchDealerName(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public List<DealerExecutiveTo2> fetchDealerExecutiveName(PayloadBean payloadBean) {
		return inquiryDao.fetchDealerExecutiveName(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public String addCustomerAddress(CustomerAddressBean customerAddressBean) {
		String resultString = "";
		customerAddressBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
		customerAddressBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
		customerAddressBean.setCreatedDate(new Date());
		try {
			CustomerAddressEntity customerAddressEntity = mapper.map(customerAddressBean, CustomerAddressEntity.class);
			resultString = inquiryDao.addCustomerAddress(customerAddressEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String updateCustomerAddress(CustomerAddressBean customerAddressBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.updateCustomerAddress(customerAddressBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String updateLoanCrossSell(LoanCrossSaleBean loanCrossSaleBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.updateLoanCrossSell(loanCrossSaleBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String saveLoanCommission(LoanCommissionBean loanCommissionBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			loanCommissionBean.setTransactionCount(1L);
			loanCommissionBean.setCreatedDate(new Date());
			loanCommissionBean.setRecordType('I');

			LoanCommissionEntity loanCommissionEntity = mapper.map(loanCommissionBean, LoanCommissionEntity.class);
			resultFlag = inquiryDao.saveLoanCommission(loanCommissionEntity);
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
	public String saveVehicleInvoice(LoanVehicleInvoiceBean loanVehicleInvoiceBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			loanVehicleInvoiceBean.setTransactionCount(1L);
			loanVehicleInvoiceBean.setCreatedDate(new Date());
			loanVehicleInvoiceBean.setRecordType('I');

			LoanVehicleInvoiceEntity loanVehicleInvoiceEntity = mapper.map(loanVehicleInvoiceBean,
					LoanVehicleInvoiceEntity.class);
			resultFlag = inquiryDao.saveVehicleInvoice(loanVehicleInvoiceEntity);
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
	public String deleteLoanCommission(PayloadBean payloadBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.deleteLoanCommission(payloadBean);
		if (resultFlag) {
			return "Deleted";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String deleteLoanVehicleInvoice(PayloadBean payloadBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.deleteLoanVehicleInvoice(payloadBean);
		if (resultFlag) {
			return "Deleted";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public List<LoanCommissionTo> getLoanCommission(PayloadBean payloadBean) {
		return inquiryDao.getLoanCommission(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public List<LoanVehicleInvoiceTo> getLoanVehicleInvoice(PayloadBean payloadBean) {
		return inquiryDao.getLoanVehicleInvoice(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public String updateLoanSurityCheques(LoanSurityChequesBean loanSurityChequesBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.updateLoanSurityCheques(loanSurityChequesBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String updateLoanCommission(LoanCommissionBean loanCommissionBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.updateLoanCommission(loanCommissionBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String updateLoanVehicleInvoice(LoanVehicleInvoiceBean loanVehicleInvoiceBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.updateLoanVehicleInvoice(loanVehicleInvoiceBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String addLoanAddressDetails(LoanAddressBean loanAddressBean) {
		boolean resultFlag = false;

		loanAddressBean.setTransactionCount(1L);
		loanAddressBean.setCreatedDate(new Date());
		loanAddressBean.setRecordType('I');

		LoanAddressEntity loanAddressEntity = mapper.map(loanAddressBean, LoanAddressEntity.class);
		resultFlag = inquiryDao.addCustomersDetails(loanAddressEntity);
		if (resultFlag) {
			return "ADDED";
		} else
			return "";
	}

	@Override
	@Transactional("db1Tx")
	public String addLoanMembersDetails(LoanMembersBean loanMembersBean) {
		boolean resultFlag = false;

		loanMembersBean.setTransactionCount(1L);
		loanMembersBean.setCreatedDate(new Date());
		loanMembersBean.setRecordType('I');

		LoanMembersEntity loanMembersEntity = mapper.map(loanMembersBean, LoanMembersEntity.class);
		resultFlag = inquiryDao.addCustomersDetails(loanMembersEntity);
		if (resultFlag) {
			return "ADDED";
		} else
			return "";
	}

	@Override
	@Transactional("db1Tx")
	public String addLoanReferencesDetails(LoanReferencesBean loanReferencesBean) {
		boolean resultFlag = false;

		loanReferencesBean.setTransactionCount(1L);
		loanReferencesBean.setCreatedDate(new Date());
		loanReferencesBean.setRecordType('I');

		LoanReferencesEntity loanReferencesEntity = mapper.map(loanReferencesBean, LoanReferencesEntity.class);
		resultFlag = inquiryDao.addCustomersDetails(loanReferencesEntity);
		if (resultFlag) {
			return "ADDED";
		} else
			return "";
	}

	@Override
	@Transactional("db1Tx")
	public List<LoanAddressBean> getLoanAddressDetails(PayloadBean payloadBean) {

		return inquiryDao.getLoanAddressDetails(payloadBean.getId(),payloadBean.getUserId());
	}

	@Override
	@Transactional("db1Tx")
	public List<LoanReferencesBean> getLoanReferencesDetails(PayloadBean payloadBean) {

		return inquiryDao.getLoanReferencesDetails(payloadBean.getId(),payloadBean.getUserId());
	}

	@Override
	@Transactional("db1Tx")
	public List<LoanMembersBean> getLoanMembersDetails(PayloadBean payloadBean) {

		return inquiryDao.getLoanMembersDetails(payloadBean.getId(),payloadBean.getUserId());
	}

	@Override
	@Transactional("db1Tx")
	public String addLoanCoborrower(LoanCoborrowerBean loanCoborrowerBean) {
		boolean resultFlag = false;

		loanCoborrowerBean.setTransactionCount(1);
		loanCoborrowerBean.setCreatedDate(new Date());
		loanCoborrowerBean.setRecordType('I');

		LoanCoborrowerEntity loanCoborrowerEntity = mapper.map(loanCoborrowerBean, LoanCoborrowerEntity.class);
		resultFlag = inquiryDao.addCustomersDetails(loanCoborrowerEntity);
		if (resultFlag) {
			return "ADDED";
		} else
			return "";
	}

	@Override
	@Transactional("db1Tx")
	public List<LoanCoborrowerTo> getLoanCoborrower(PayloadBean payloadBean) {

		return inquiryDao.getLoanCoborrower(payloadBean.getId());
	}

	@Override
	@Transactional("db1Tx")
	public String updateLoanCoborrower(LoanCoborrowerBean loanCoborrowerBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.updateLoanCoborrower(loanCoborrowerBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String deleteLoanCoborrower(LoanCoborrowerBean loanCoborrowerBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.deleteLoanCoborrower(loanCoborrowerBean);
		if (resultFlag) {
			return "Deleted";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public List<LoanSurityChequesTo> getLoanSurityChequeValidation(PayloadBean payloadBean) {

		return inquiryDao.getLoanSurityChequeValidation(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public String primaryAccountExistorNot(PayloadBean payloadBean) {
		String resultString = "Primary Account Exist";
		boolean resultFlag = false;
		resultFlag = inquiryDao.primaryAccountExistorNot(payloadBean.getUserId());
		if (!resultFlag) {
			return "No Primary Account Exist";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String deleteLoanDocument(DeleteRecords deleteRecords) {
		String resultString = "";
		boolean resultFlag = false;
		Long l = Long.valueOf(deleteRecords.getModifiedBy());

		resultFlag = inquiryDao.deleteLoanDocument(deleteRecords.getId(), l);
		if (resultFlag)
			return "DELETED";
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String updateCustomer(CustomerBean customerBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.updateCustomer(customerBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public List<CustomersTO> fetchcustomerDataForMerging(CustomerBean customerBean) {

		return inquiryDao.fetchcustomerDataForMerging(customerBean);
	}

	@Override
	@Transactional("db1Tx")
	public String updateLoanAddressDetails(LoanAddressBean loanAddressBean) {
		String resultString = "";
		boolean resultFlag = false;
		
		LoanAddressEntity loanAddressEntity = mapper.map(loanAddressBean, LoanAddressEntity.class);
		resultFlag = inquiryDao.updateLoanAddressDetails(loanAddressEntity);
		if (resultFlag) {
			return "UPDATED";
		} else
			return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String updateLoanReferencesDetails(LoanReferencesBean loanReferencesBean) {
		String resultString = "";
		boolean resultFlag = false;
		
		LoanReferencesEntity loanReferencesEntity = mapper.map(loanReferencesBean, LoanReferencesEntity.class);
		resultFlag = inquiryDao.updateLoanReferencesDetails(loanReferencesEntity);
		if (resultFlag) {
			return "UPDATED";
		} else
			return resultString;
	}
	@Override
	@Transactional("db1Tx")
	public String updateLoanMembersDetails(LoanMembersBean loanMembersBean) {
		String resultString = "";
		boolean resultFlag = false;
		
		LoanMembersEntity loanMembersEntity = mapper.map(loanMembersBean, LoanMembersEntity.class);
		resultFlag = inquiryDao.updateLoanMembersDetails(loanMembersEntity);
		if (resultFlag) {
			return "UPDATED";
		} else
			return resultString;
	}
	
	@Override
	@Transactional("db1Tx")
	public String updateLoanBankDetails(LoanBean loanBean) {
		String resultString = "";
		boolean resultFlag = false;
		resultFlag = inquiryDao.updateLoanBankDetails(loanBean);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}
}
