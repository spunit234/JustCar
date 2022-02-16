package com.edios.csr.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.impl.BaseDaoImpl;
import com.edios.cdf.entity.AbstractEntity;
import com.edios.csr.bean.BankLoanDetailsReportBean;
import com.edios.csr.bean.InquiryBean;
import com.edios.csr.dao.ReportDao;
import com.edios.csr.entity.to.InquiryTO;

@Repository
@SuppressWarnings({ "unchecked", "deprecation" })
public class ReportDaoImpl extends BaseDaoImpl<AbstractEntity> implements ReportDao {

	@Autowired
	MessageSource messageSource;

	@Override
	public List<InquiryBean> fetchInquiriesPendingTasks(InquiryBean inquiryBean) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<InquiryBean> inquiryBeanList = null;
		String sql = "";
		try {
			sql = "Select inqe.inquiryId as inquiryId, inqe.inquiryNo as inquiryNo, inqe.inquiryDate as inqDate, inqe.inquiryType as inquiryType,"
					+ " concat(inqe.firstName , ' ' , inqe.lastName) as customerFullname, inqe.contactNumber as contactNumber,"
					+ " inqe.inquiryStatus as inquiryStatus, inqe.callbackDate as callBackOn, COALESCE(inqe.lastModifiedDate,inqe.createdDate) as lastUpdatedOn,"
					+ " inqe.assignedStaffId as assignedStaffId, inqe.sourceStaffId as sourceStaffId, inqe.sourceStaffId2 as sourceStaffId2,"
					+ " inqe.sourceStaffId3 as sourceStaffId3, inqe.sourceStaffId4 as sourceStaffId4,"
					+ " se.staffId as staffId, concat(se.firstName,' ',se.lastName) as staffFullName "
					+ " from InquiriesEntity inqe left join StaffEntity se on  (se.staffId in  (inqe.assignedStaffId, inqe.sourceStaffId"
					+ " , inqe.sourceStaffId2 , inqe.sourceStaffId3 , inqe.sourceStaffId4 ) AND se.recordType<>'D'  ) "
					+ " where inqe.recordType<>'D' and inqe.callbackDate <= '"
					+ simpleDateFormat.format(inquiryBean.getCallbackDate()) + "' order by "
					+ " inqe.inquiryId,inqe.inquiryNo";
			inquiryBeanList = (List<InquiryBean>) getSession().createQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(InquiryBean.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
			return inquiryBeanList;
		}
		return inquiryBeanList;

	}

	@Override
	public List<InquiryBean> fetchVehicleCarStockForSale(InquiryBean inquiryBean) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		List<InquiryBean> vehicleCarStockForSaleList = null;

		StringBuilder whereClause = new StringBuilder();
		String sql = "";
		try {

			if (inquiryBean.getInquiryNo() != null)
				whereClause.append(" and inqe.inquiryNo = '").append(inquiryBean.getInquiryNo()).append("' ");
			if (inquiryBean.getInquiryType() != null)
				whereClause.append(" and inqe.inquiryType = '").append(inquiryBean.getInquiryType()).append("' ");
			if (inquiryBean.getInquiryFromDate() != null) {
				whereClause.append(" and inqe.inquiryDate >= '")
						.append(simpleDateFormat.format(inquiryBean.getInquiryFromDate())).append("' ");
			}
			if (inquiryBean.getInquiryToDate() != null) {
				whereClause.append(" and inqe.inquiryDate <= '")
						.append(simpleDateFormat.format(inquiryBean.getInquiryToDate())).append("' ");
			}
			if (inquiryBean.getAssignedStaffId() != null)
				whereClause.append(" and inqe.assignedStaffId = ").append(inquiryBean.getAssignedStaffId());
			if (inquiryBean.getSourceStaffId() != null) {
				whereClause.append(" and ( inqe.sourceStaffId = ").append(inquiryBean.getSourceStaffId())
						.append(" OR inqe.sourceStaffId2 = ").append(inquiryBean.getSourceStaffId())
						.append(" OR inqe.sourceStaffId3 = ").append(inquiryBean.getSourceStaffId())
						.append(" OR inqe.sourceStaffId4 = ").append(inquiryBean.getSourceStaffId()).append(" ) ");
			}

			whereClause
					.append(" GROUP BY inqe.assignedStaffId,inqe.sourceStaffId,inqe.sourceStaffId2,inqe.sourceStaffId3,"
							+ "inqe.sourceStaffId4,se.staffId,vehicle.vehicleId,inqe.inquiryNo ")
					.append(" order by inqe.inquiryId,inqe.inquiryNo");

			sql = "Select inqe.inquiryId as inquiryId, inqe.inquiryNo as inquiryNo, inqe.inquiryDate as inqDate, inqe.inquiryType as inquiryType,"
					+ " concat(inqe.firstName , ' ' , inqe.lastName, ' ,PHONE:',inqe.contactNumber) as customerFullname,"
					+ " CONCAT(make.makeName,', ',model.modelName,', ',GROUP_CONCAT(aple.parameterListValue),', ',vehicle.vehicleRegNo) as vehicleInfo, "
					+ " inqe.inquiryStatus as inquiryStatus,vehicle.odoReading as odoReading,vehicle.vehicleValue as vehicleValue,vehicle.vehicleId as vehicleId, "
					+ " inqe.assignedStaffId as assignedStaffId, inqe.sourceStaffId as sourceStaffId, inqe.sourceStaffId2 as sourceStaffId2,"
					+ " inqe.sourceStaffId3 as sourceStaffId3, inqe.sourceStaffId4 as sourceStaffId4,"
					+ " se.staffId as staffId, concat(se.firstName,' ',se.lastName) as staffFullName "
					+ " from InquiriesEntity inqe "
					+ " left join StaffEntity se on  (se.staffId in  (inqe.assignedStaffId, inqe.sourceStaffId, "
					+ " inqe.sourceStaffId2 , inqe.sourceStaffId3 , inqe.sourceStaffId4 ) AND se.recordType<>'D'  ) "
					+ " left join VehicelsEntity vehicle on (vehicle.inquiryId = inqe.inquiryId AND vehicle.recordType<>'D' ) "
					+ " left join VehicleMakeEntity make on (make.makeId = vehicle.make) "
					+ " left join VehicleModelEntity model on (model.modelId = vehicle.model) "
					+ " left join ApplicationParameterListEntity aple on  (aple.parameterListID in  "
					+ " (vehicle.modelVariant, vehicle.colour, vehicle.vehicleType) AND aple.recordType<>'D') "
					+ " where inqe.recordType<>'D' and inqe.inquiryStatus = :inquiryStatus  " + whereClause.toString();

			vehicleCarStockForSaleList = (List<InquiryBean>) getSession().createQuery(sql)
					.setParameter("inquiryStatus", inquiryBean.getInquiryStatus())
					.setResultTransformer(Transformers.aliasToBean(InquiryBean.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
			return vehicleCarStockForSaleList;
		}
		return vehicleCarStockForSaleList;
	}

	@Override
	public HashMap<Long, String> fetchVehicleInfo(InquiryBean inquiryBean) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		HashMap<Long, String> vehicleInfoList = new HashMap<Long, String>();

		StringBuilder whereClause = new StringBuilder();
		String sql = "";
		try {

			if (inquiryBean.getInquiryFromDate() != null) {
				whereClause.append(" and vehicle.createdDate >= '")
						.append(simpleDateFormat.format(inquiryBean.getInquiryFromDate())).append("' ");
			}
			if (inquiryBean.getInquiryToDate() != null) {
				whereClause.append(" and vehicle.createdDate < '")
						.append(simpleDateFormat.format(inquiryBean.getInquiryToDate())).append("' ");
			}

			whereClause.append(" group by vehicle.vehicleId");

			sql = "Select  vehicle.vehicleId as vehicleId,"
					+ " CONCAT(make.makeName,', ',model.modelName,', ',GROUP_CONCAT(aple.parameterListValue),', ',vehicle.vehicleRegNo) as vehicleInfo "
					+ " from VehicelsEntity vehicle  "
					+ " left join VehicleMakeEntity make on (make.makeId = vehicle.make) "
					+ " left join VehicleModelEntity model on (model.modelId = vehicle.model) "
					+ " left join ApplicationParameterListEntity aple on  (aple.parameterListID in  "
					+ " (vehicle.modelVariant, vehicle.colour, vehicle.vehicleType) AND aple.recordType<>'D') "
					+ " where vehicle.recordType<>'D'  " + whereClause.toString();

			List<Object[]> list = getSession().createQuery(sql).getResultList();

			for (Object[] result : list) {
				vehicleInfoList.put((Long) result[0], result[1] != null ? result[1].toString() : "");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return vehicleInfoList;
		}
		return vehicleInfoList;
	}

	@Override
	public List<InquiryTO> fetchBuisnessDetails(InquiryBean inquiryBean) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		List<InquiryTO> buisnessDetailsList = null;

		StringBuilder whereClause = new StringBuilder();
		String sql = "";
		try {

			if (inquiryBean.getInquiryNo() != null)
				whereClause.append(" and inqe.inquiryNo = '").append(inquiryBean.getInquiryNo()).append("' ");
			if (inquiryBean.getInquiryType() != null)
				whereClause.append(" and inqe.inquiryType = '").append(inquiryBean.getInquiryType()).append("' ");
			if (inquiryBean.getInquiryFromDate() != null) {
				whereClause.append(" and inqe.inquiryDate >= '")
						.append(simpleDateFormat.format(inquiryBean.getInquiryFromDate())).append("' ");
			}
			if (inquiryBean.getInquiryToDate() != null) {
				whereClause.append(" and inqe.inquiryDate <= '")
						.append(simpleDateFormat.format(inquiryBean.getInquiryToDate())).append("' ");
			}
			if (inquiryBean.getAssignedStaffId() != null)
				whereClause.append(" and inqe.assignedStaffId = ").append(inquiryBean.getAssignedStaffId());
			if (inquiryBean.getSourceStaffId() != null) {
				whereClause.append(" AND se.staffId =").append(inquiryBean.getSourceStaffId());
			}

			if (inquiryBean.getReportType().equals("inquiryType")) {
				sql = " Select inqe.inquiryId as inquiryId, inqe.inquiryType as inquiryType,count(inqe.inquiryType) as count "
						+ " from InquiriesEntity inqe "
						+ " where inqe.recordType<>'D' and inqe.inquiryStatus = :inquiryStatus  "
						+ whereClause.toString() + " group by inqe.inquiryType";
			} else if (inquiryBean.getReportType().equals("inquirySource")) {
				sql = " Select inqe.inquiryId as inquiryId, inqe.inquiryType as inquiryType,"
						+ " concat(se.firstName,' ',se.lastName) as sourceStaff1,"
						+ " count(inqe.sourceStaffId) as count " + " from InquiriesEntity inqe "
						+ " left join StaffEntity se on  (se.staffId in  ( inqe.sourceStaffId, "
						+ " inqe.sourceStaffId2 , inqe.sourceStaffId3 , inqe.sourceStaffId4 ) AND se.recordType<>'D'  ) "
						+ " where inqe.recordType<>'D' and inqe.inquiryStatus = :inquiryStatus  "
						+ whereClause.toString() + " group by se.staffId,inqe.inquiryType";
			} else if (inquiryBean.getReportType().equals("inquiryAssignee")) {
				sql = " Select inqe.inquiryId as inquiryId, inqe.inquiryType as inquiryType,"
						+ " concat(se.firstName,' ',se.lastName) as inquiryAssigne,count(inqe.assignedStaffId) as count"
						+ " from InquiriesEntity inqe "
						+ " left join StaffEntity se on  (se.staffId = inqe.assignedStaffId AND se.recordType<>'D'  ) "
						+ " where inqe.recordType<>'D' and inqe.inquiryStatus = :inquiryStatus  "
						+ whereClause.toString() + " group by se.staffId,inqe.inquiryType";

			}

			buisnessDetailsList = (List<InquiryTO>) getSession().createQuery(sql)
					.setParameter("inquiryStatus", inquiryBean.getInquiryStatus())
					.setResultTransformer(Transformers.aliasToBean(InquiryTO.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
			return buisnessDetailsList;
		}
		return buisnessDetailsList;
	}

	@Override
	public List<InquiryTO> fetchBankLoanDetails(BankLoanDetailsReportBean bankLoanDetailsReportBean) {
		List<InquiryTO> buisnessDetailsList = null;
		String sql = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateto = null;
		try {
			if (bankLoanDetailsReportBean.getReportType().equals("Bank Wise")) {
				sql = " Select apl.parameterListValue as loanBank, sum(le.loanAmount) as totalLoanAmount,count(le.loanBank) as count "
						+ " from LoanEntity le left join ApplicationParameterListEntity apl on apl.parameterListID = le.loanBank "
						+ " where  le.loanStatus in (select apl1.parameterListID from ApplicationParameterListEntity apl1 where apl1.parameterListValue='Disbursed' )";
			} else if (bankLoanDetailsReportBean.getReportType().equals("Inquiry Source")) {
				sql = " Select apl.parameterListValue as loanBank, sum(le.loanAmount) as totalLoanAmount,count(le.loanBank) as count,"
						+ "concat(s.firstName,' ', s.lastName) as sourceName "
						+ " from LoanEntity le  left join le.inquiryId i left join StaffEntity s "
						+ " on s.staffId in(i.sourceStaffId,i.sourceStaffId2,i.sourceStaffId3,i.sourceStaffId4)"
						+ " left join ApplicationParameterListEntity apl on apl.parameterListID = le.loanBank "
						+ " where  le.loanStatus in (select apl1.parameterListID from ApplicationParameterListEntity apl1 where apl1.parameterListValue='Disbursed' ) and s.staffId is not Null ";
			} else if (bankLoanDetailsReportBean.getReportType().equals("Inquiry Assignee")) {
				sql = " Select apl.parameterListValue as loanBank, sum(le.loanAmount) as totalLoanAmount,count(le.loanBank) as count,"
						+ "concat(s.firstName,' ', s.lastName) as sourceName "
						+ " from LoanEntity le  left join le.inquiryId i left join StaffEntity s "
						+ " on s.staffId in(i.assignedStaffId)"
						+ " left join ApplicationParameterListEntity apl on apl.parameterListID = le.loanBank "
						+ " where  le.loanStatus in (select apl1.parameterListID from ApplicationParameterListEntity apl1 where apl1.parameterListValue='Disbursed' ) and s.staffId is not Null ";
			}
			if (Optional.ofNullable(bankLoanDetailsReportBean.getBankName()).isPresent()) {
				sql += " and le.loanBank=" + bankLoanDetailsReportBean.getBankName() + " ";
			}
			if (Optional.ofNullable(bankLoanDetailsReportBean.getLoanClass()).isPresent()) {
				sql += " and le.loanClass=" + bankLoanDetailsReportBean.getLoanClass() + " ";
			}
			if (Optional.ofNullable(bankLoanDetailsReportBean.getFromDate()).isPresent()) {
				dateto = formatter.format(bankLoanDetailsReportBean.getFromDate()) + " 00:00:00";
				sql += " and le.amountDisbursedDate>='" + dateto + "' ";
			}
			if (Optional.ofNullable(bankLoanDetailsReportBean.getToDate()).isPresent()) {
				dateto = formatter.format(bankLoanDetailsReportBean.getToDate()) + " 00:00:00";
				sql += " and le.amountDisbursedDate<='" + dateto + "' ";
			}
			if (bankLoanDetailsReportBean.getReportType().equals("Bank Wise")) {
				sql += " and le.loanBank is not Null group by le.loanBank ";
			} else if (bankLoanDetailsReportBean.getReportType().equals("Inquiry Source")) {
				sql += "  group by s.staffId ";
			} else if (bankLoanDetailsReportBean.getReportType().equals("Inquiry Assignee")) {
				sql += "  group by s.staffId ";
			}

			buisnessDetailsList = (List<InquiryTO>) getSession().createQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(InquiryTO.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
			return buisnessDetailsList;
		}
		return buisnessDetailsList;
	}

	@Override
	public List<InquiryTO> fetchDedupeReportData(BankLoanDetailsReportBean bankLoanDetailsReportBean) {
		List<InquiryTO> dedupeDetailsList = null;
		String sql = "";
		try {
			if (bankLoanDetailsReportBean.getReportType().equals("Aadhar No.")) {
				sql = "select c1.firstName as firstName, c1.lastName as lastName,c1.aadharNo as duplicateValue "
						+ "  from CustomerEntity c1 where c1.aadharNo in (Select c.aadharNo "
						+ " from CustomerEntity c group by c.aadharNo having count(c.aadharNo)>1)  ";
				if (Optional.ofNullable(bankLoanDetailsReportBean.getCustomerType()).isPresent()) {
					sql += " and c1.customerType='" + bankLoanDetailsReportBean.getCustomerType()
							+ "' and c1.recordType<>'D'  and c1.aadharNo is not null order by c1.aadharNo ";
				} else {
					sql += " and c1.recordType<>'D' and c1.aadharNo is not null order by c1.aadharNo";
				}
			}
			if (bankLoanDetailsReportBean.getReportType().equals("DL No.")) {
				sql = "select c1.firstName as firstName, c1.lastName as lastName,c1.dlNo as duplicateValue "
						+ "  from CustomerEntity c1 where c1.dlNo in (Select c.dlNo "
						+ " from CustomerEntity c group by c.dlNo having count(c.dlNo)>1)  ";
				if (Optional.ofNullable(bankLoanDetailsReportBean.getCustomerType()).isPresent()) {
					sql += " and c1.customerType='" + bankLoanDetailsReportBean.getCustomerType()
							+ "' and c1.recordType<>'D' and c1.dlNo is not null order by c1.dlNo ";
				} else {
					sql += " and c1.recordType<>'D'and c1.dlNo is not null order by c1.dlNo";
				}
			}
			if (bankLoanDetailsReportBean.getReportType().equals("PAN No.")) {
				sql = "select c1.firstName as firstName, c1.lastName as lastName,c1.panNo as duplicateValue "
						+ "  from CustomerEntity c1 where c1.panNo in (Select c.panNo "
						+ " from CustomerEntity c group by c.panNo having count(c.panNo)>1)  ";
				if (Optional.ofNullable(bankLoanDetailsReportBean.getCustomerType()).isPresent()) {
					sql += " and c1.customerType='" + bankLoanDetailsReportBean.getCustomerType()
							+ "' and c1.recordType<>'D' and c1.panNo is not null order by c1.panNo ";
				} else {
					sql += " and c1.recordType<>'D' and c1.panNo is not null order by c1.panNo";
				}
			}
			if (bankLoanDetailsReportBean.getReportType().equals("GST No.")) {
				sql = "select c1.firstName as firstName, c1.lastName as lastName,c1.gstNo as duplicateValue "
						+ "  from CustomerEntity c1 where c1.gstNo in (Select c.gstNo "
						+ " from CustomerEntity c group by c.gstNo having count(c.gstNo)>1)  ";
				if (Optional.ofNullable(bankLoanDetailsReportBean.getCustomerType()).isPresent()) {
					sql += " and c1.customerType='" + bankLoanDetailsReportBean.getCustomerType()
							+ "' and c1.recordType<>'D' and c1.gstNo is not null order by c1.gstNo ";
				} else {
					sql += " and c1.recordType<>'D' and c1.gstNo is not null order by c1.gstNo";
				}
			}
			if (bankLoanDetailsReportBean.getReportType().equals("Voter Id")) {
				sql = "select c1.firstName as firstName, c1.lastName as lastName,c1.voterId as duplicateValue "
						+ "  from CustomerEntity c1 where c1.voterId in (Select c.voterId "
						+ " from CustomerEntity c group by c.voterId having count(c.voterId)>1)  ";
				if (Optional.ofNullable(bankLoanDetailsReportBean.getCustomerType()).isPresent()) {
					sql += " and c1.customerType='" + bankLoanDetailsReportBean.getCustomerType()
							+ "' and c1.recordType<>'D' and c1.voterId is not null order by c1.voterId ";
				} else {
					sql += " and c1.recordType<>'D' and c1.voterId is not null order by c1.voterId";
				}
			}
			if (bankLoanDetailsReportBean.getReportType().equals("Passport No.")) {
				sql = "select c1.firstName as firstName, c1.lastName as lastName,c1.passportNumber as duplicateValue "
						+ "  from CustomerEntity c1 where c1.passportNumber in (Select c.passportNumber "
						+ " from CustomerEntity c group by c.passportNumber having count(c.passportNumber)>1)  ";
				if (Optional.ofNullable(bankLoanDetailsReportBean.getCustomerType()).isPresent()) {
					sql += " and c1.customerType='" + bankLoanDetailsReportBean.getCustomerType()
							+ "' and c1.recordType<>'D' and c1.passportNumber is not null order by c1.passportNumber ";
				} else {
					sql += " and c1.recordType<>'D' and c1.passportNumber is not null order by c1.passportNumber";
				}
			}

			dedupeDetailsList = (List<InquiryTO>) getSession().createQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(InquiryTO.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
			return dedupeDetailsList;
		}
		return dedupeDetailsList;
	}

	@Override
	public List<InquiryTO> fetchPendingRCDetails(InquiryBean inquiryBean) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		List<InquiryTO> buisnessDetailsList = null;

		StringBuilder whereClause = new StringBuilder();
		String sql = "";
		try {

			if (inquiryBean.getFirstName() != null && !(inquiryBean.getFirstName().equals(""))) {
				whereClause.append(" AND (inq.FIRST_NAME like '%").append(inquiryBean.getFirstName()).append("%'");
				if (inquiryBean.getLastName() != null && !(inquiryBean.getLastName().equals("")))
					whereClause.append(" AND inq.LAST_NAME like '%").append(inquiryBean.getLastName()).append("%')");
				else
					whereClause.append(" OR inq.LAST_NAME like '%").append(inquiryBean.getFirstName()).append("%')");
			}
			if (inquiryBean.getContactNumber() != null && !(inquiryBean.getContactNumber().equals("")))
				whereClause.append(" and inq.CONTACT_NUMBER = '").append(inquiryBean.getContactNumber()).append("' ");
			if (inquiryBean.getInquiryFromDate() != null) {
				whereClause.append(" and inq.INQUIRY_DATE >= '")
						.append(simpleDateFormat.format(inquiryBean.getInquiryFromDate())).append("' ");
			}
			if (inquiryBean.getInquiryToDate() != null) {
				whereClause.append(" and inq.INQUIRY_DATE <= '")
						.append(simpleDateFormat.format(inquiryBean.getInquiryToDate())).append(" 23:59:59' ");
			}
			if (inquiryBean.getAssignedStaffId() != null)
				whereClause.append(" and inq.ASSIGNED_STAFF_ID = ").append(inquiryBean.getAssignedStaffId());
			if (inquiryBean.getSourceStaffId() != null) {
				whereClause.append(" and ( inq.SOURCE_STAFF_ID1 = ").append(inquiryBean.getSourceStaffId())
						.append(" OR inq.SOURCE_STAFF_ID2 = ").append(inquiryBean.getSourceStaffId())
						.append(" OR inq.SOURCE_STAFF_ID3 = ").append(inquiryBean.getSourceStaffId())
						.append(" OR inq.SOURCE_STAFF_ID4 = ").append(inquiryBean.getSourceStaffId()).append(" ) ");
			}

			String curDate = simpleDateFormat.format(new Date());
//			whereClause.append(" order by inqe.inquiryId");

			sql = "SELECT loan.LOAN_NO as loanNo,veh.RC_STATUS as rcStatus,concat(inq.FIRST_NAME , ' ' , inq.LAST_NAME) as customerFullname, "
					+ "   DATEDIFF('" + curDate
					+ "',agg.AGGREGATOR_DATE) as pendingDays,veh.VEHICLE_REG_NO as vehicleRegNo,veh.VEHICLE_CHESIS_NO as vehicleChassisNo, "
					+ "	GROUP_CONCAT((case when staff.STAFF_ID = inq.SOURCE_STAFF_ID1 then  concat(staff.FIRST_NAME , ' ' , staff.LAST_NAME)END)) AS sourceStaff1, "
					+ "	GROUP_CONCAT(case when staff.STAFF_ID = inq.SOURCE_STAFF_ID2 then  concat(staff.FIRST_NAME , ' ' , staff.LAST_NAME)END) AS sourceStaff2, "
					+ "	GROUP_CONCAT(case when staff.STAFF_ID = inq.SOURCE_STAFF_ID3 then  concat(staff.FIRST_NAME , ' ' , staff.LAST_NAME)END) AS sourceStaff3, "
					+ "	GROUP_CONCAT(case when staff.STAFF_ID = inq.SOURCE_STAFF_ID4 then  concat(staff.FIRST_NAME , ' ' , staff.LAST_NAME)END ) AS sourceStaff4, "
					+ "	GROUP_CONCAT(case when staff.STAFF_ID = inq.ASSIGNED_STAFF_ID then  concat(staff.FIRST_NAME , ' ' , staff.LAST_NAME)END) AS inquiryAssigne "
					+ "	from aggregators agg "
					+ "	LEFT join inquiries inq ON ( inq.INQUIRY_ID=agg.INQUIRY_ID AND inq.RECORD_TYPE<>'D') "
					+ "	LEFT JOIN loans loan ON (inq.INQUIRY_ID=loan.INQUIRY_ID AND loan.RECORD_TYPE<>'D') "
					+ "   LEFT JOIN vehicles veh ON (inq.INQUIRY_ID=veh.INQUIRY_ID AND veh.RECORD_TYPE<>'D') "
					+ "  	LEFT JOIN staff staff ON "
					+ "   (staff.STAFF_ID in (inq.SOURCE_STAFF_ID1,inq.SOURCE_STAFF_ID2, inq.SOURCE_STAFF_ID3,inq.SOURCE_STAFF_ID4,inq.ASSIGNED_STAFF_ID)) "
					+ " WHERE  agg.RECORD_TYPE<>'D'  " + whereClause.toString() + "  	GROUP BY inq.INQUIRY_ID "
					+ "   ORDER BY inq.INQUIRY_DATE ";

			buisnessDetailsList = (List<InquiryTO>) getSession().createNativeQuery(sql)
//					.setParameter("inquiryStatus", inquiryBean.getInquiryStatus())
					.setResultTransformer(Transformers.aliasToBean(InquiryTO.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
			return buisnessDetailsList;
		}
		return buisnessDetailsList;
	}

	@Override
	public List<InquiryTO> fetchLoanBuisnessOnADayReportData(BankLoanDetailsReportBean loanBuisnessOnADayReportBean) {
		List<InquiryTO> loanBuisnessOnADayDetailsList = null;
		String sql = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateto = null;
		try {
			if (loanBuisnessOnADayReportBean.getReportType().equals("Loans Disbursed")) {
				sql = " Select apl.parameterListValue as loanBank, sum(le.loanAmount) as totalLoanAmount,count(le.loanBank) as count "
						+ " from LoanEntity le left join ApplicationParameterListEntity apl on apl.parameterListID = le.loanBank where le.loanBank is not null  ";
//						+ " where  le.loanStatus='Disbursed' ";
				if (Optional.ofNullable(loanBuisnessOnADayReportBean.getBankName()).isPresent()) {
					sql += " and le.loanBank =" + loanBuisnessOnADayReportBean.getBankName() + " ";
				}
				if (Optional.ofNullable(loanBuisnessOnADayReportBean.getFromDate()).isPresent()) {
					dateto = formatter.format(loanBuisnessOnADayReportBean.getFromDate()) + " 00:00:00";
					sql += " and le.amountDisbursedDate>='" + dateto + "' ";
				}
				if (Optional.ofNullable(loanBuisnessOnADayReportBean.getToDate()).isPresent()) {
					dateto = formatter.format(loanBuisnessOnADayReportBean.getToDate()) + " 00:00:00";
					sql += " and le.amountDisbursedDate<='" + dateto + "' ";
				}

				sql += " group by apl.parameterListValue ";
			} else if (loanBuisnessOnADayReportBean.getReportType().equals("Loan Applications Sent to Bank")) {
				sql = " Select apl.parameterListValue as loanBank,count(le.loanBank) as count "
						+ " from LoanEntity le  left join le.inquiryId i  "
						+ " left join ApplicationParameterListEntity apl on apl.parameterListID = le.loanBank "
						+ " where le.loanBank is not null and i.inquiryStatus='With Bank' ";
				if (Optional.ofNullable(loanBuisnessOnADayReportBean.getBankName()).isPresent()) {
					sql += " and le.loanBank =" + loanBuisnessOnADayReportBean.getBankName() + " ";
				}
				if (Optional.ofNullable(loanBuisnessOnADayReportBean.getFromDate()).isPresent()) {
					dateto = formatter.format(loanBuisnessOnADayReportBean.getFromDate()) + " 00:00:00";
					sql += " and i.statusDate>='" + dateto + "' ";
				}
				if (Optional.ofNullable(loanBuisnessOnADayReportBean.getToDate()).isPresent()) {
					dateto = formatter.format(loanBuisnessOnADayReportBean.getToDate()) + " 00:00:00";
					sql += " and i.statusDate<='" + dateto + "' ";
				}

				sql += " group by apl.parameterListValue ";
			}

			loanBuisnessOnADayDetailsList = (List<InquiryTO>) getSession().createQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(InquiryTO.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
			return loanBuisnessOnADayDetailsList;
		}
		return loanBuisnessOnADayDetailsList;
	}

	@Override
	public List<InquiryTO> fetchRtoAgentCharges(InquiryBean rtoAgentChargesBean) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		List<InquiryTO> rtoAgentChargesList = null;
		String dateFormat = messageSource.getMessage("dateFormat", null, "", Locale.US);
		StringBuilder whereClause = new StringBuilder();
		String sql = "";
		try {

			if (rtoAgentChargesBean.getFirstName() != null && !(rtoAgentChargesBean.getFirstName().equals(""))) {
				whereClause.append(" AND (inq.FIRST_NAME like '%").append(rtoAgentChargesBean.getFirstName())
						.append("%'");
				if (rtoAgentChargesBean.getLastName() != null && !(rtoAgentChargesBean.getLastName().equals("")))
					whereClause.append(" AND inq.LAST_NAME like '%").append(rtoAgentChargesBean.getLastName())
							.append("%')");
				else
					whereClause.append(" OR inq.LAST_NAME like '%").append(rtoAgentChargesBean.getFirstName())
							.append("%')");
			}
			if (rtoAgentChargesBean.getFromDate() != null) {
				whereClause.append(" and aggtask.AGGREGATOR_TASK_DATE >= '")
						.append(simpleDateFormat.format(rtoAgentChargesBean.getFromDate())).append("' ");
			}
			if (rtoAgentChargesBean.getToDate() != null) {
				whereClause.append(" and aggtask.AGGREGATOR_TASK_DATE <= '")
						.append(simpleDateFormat.format(rtoAgentChargesBean.getToDate())).append(" 23:59:59' ");
			}
			if (rtoAgentChargesBean.getStaffId() != null)
				whereClause.append(" and aggtask.STAFF_ID = ").append(rtoAgentChargesBean.getStaffId());

//				whereClause.append(" order by inqe.inquiryId");

			sql = "SELECT " + "  DATE_FORMAT(aggtask.AGGREGATOR_TASK_DATE,'" + dateFormat
					+ "') as aggTaskDateStr,veh.VEHICLE_REG_NO as vehicleRegNo ,concat(inq.FIRST_NAME , ' ' , inq.LAST_NAME) as customerFullname,"
					+ " aggtask.AGGREGATOR_TASK_STATUS as aggTaskStatus,aggtask.AGGREGATOR_TASK_CHARGES as aggTaskCharges,apl.PARAMETER_LIST_VALUE as aggTaskName, "
					+ " agg.AGGREGATOR_ID as aggregatorId,(case "
					+ " when inq.INQUIRY_TYPE IN ('Vehicle Loan-New Car','Vehicle Loan-Used Car Purchase','Vehicle Loan-Refinance Car') AND loan.LOAN_ID IS NOT null then 'Loan' "
					+ " when inq.INQUIRY_TYPE='Aggregator' AND loan.LOAN_ID IS NOT NULL then 'Aggregator' "
					+ " ELSE 'Self' "
					+ " END) AS aggTaskType, IF(rcMovt1.RCMOVEMENT_NAME='To Bank', 'YES', 'NO') AS  rcOnline , IF(rcMovt2.RCMOVEMENT_NAME='From RTO', 'YES', 'NO') AS  rcReceived "
					+ " FROM aggregator_tasks aggtask "
					+ " INNER JOIN aggregators agg ON (agg.AGGREGATOR_ID = aggtask.AGGREGATOR_ID AND agg.RECORD_TYPE<>'D') "
					+ " LEFT JOIN aggregator_rcmovement rcMovt1 ON (rcMovt1.AGGREGATOR_ID = agg.AGGREGATOR_ID AND rcMovt1.RCMOVEMENT_NAME = ('To Bank') AND rcMovt1.RECORD_TYPE<>'D') "
					+ " LEFT JOIN aggregator_rcmovement rcMovt2 ON (rcMovt2.AGGREGATOR_ID = agg.AGGREGATOR_ID AND rcMovt2.RCMOVEMENT_NAME = ('From RTO') AND rcMovt2.RECORD_TYPE<>'D') "
					+ " LEFT JOIN inquiries inq ON (inq.INQUIRY_ID = agg.INQUIRY_ID AND inq.RECORD_TYPE<>'D')"
					+ " LEFT JOIN vehicles veh ON (veh.INQUIRY_ID = inq.INQUIRY_ID AND veh.RECORD_TYPE<>'D') "
					+ " LEFT JOIN loans loan ON (loan.INQUIRY_ID = inq.INQUIRY_ID AND loan.RECORD_TYPE<>'D')"
					+ " LEFT JOIN application_parameter_lists apl ON (apl.PARAMETER_LIST_ID=aggtask.AGGREGATOR_TASK_NAME_ID AND apl.RECORD_TYPE<>'D' ) "
					+ " WHERE aggtask.AGGREGATOR_TASK_STATUS='Complete' AND aggtask.RECORD_TYPE<>'D'"
					+ whereClause.toString() + " GROUP BY aggtask.AGGREGATOR_TASK_ID ";

			rtoAgentChargesList = (List<InquiryTO>) getSession().createNativeQuery(sql)
//						.setParameter("inquiryStatus", inquiryBean.getInquiryStatus())
					.setResultTransformer(Transformers.aliasToBean(InquiryTO.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
			return rtoAgentChargesList;
		}
		return rtoAgentChargesList;
	}

	@Override
	public List<InquiryTO> fetchCarAndCashDetails(BankLoanDetailsReportBean bankLoanDetailsReportBean) {
		List<InquiryTO> buisnessDetailsList = null;
		String sql = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateFormat = messageSource.getMessage("dateFormat", null, "", Locale.US);
		String dateto = null;
		try {

			sql = " Select le.loanNo as loanNumber, apl.parameterListValue as loanStatus ,DATE_FORMAT(le.amountDisbursedDate,'"
					+ dateFormat + "') as disbursedDate,concat('Make =',make.makeName,' Model =' ,model.modelName) as vehicleInformation, "
					+ "concat(inqe.firstName,' ',case when inqe.lastName is not null then inqe.lastName else '' end) as customerName ,"
					+ " DATE_FORMAT(le.loanStartDate,'" + dateFormat
					+ "') as loanStartDate,le.loanInstallment as installmentAmount,le.advanceAmount as advanceAmount "
					+ " ,cre.referenceName as refrence1 "
					+ " from LoanEntity le left join le.inquiryId inqe left join ApplicationParameterListEntity apl on apl.parameterListID = le.loanStatus "
					+ " left join VehicelsEntity ve on ve.inquiryId = inqe.inquiryId "
					+ " left join VehicleMakeEntity make on (make.makeId = ve.make  and make.recordType<>'D')"
					+ " left join VehicleModelEntity model on (model.modelId = ve.model and model.recordType<>'D')"
					+ " left join   CustomerEntity  c    on  inqe.customerId=c.customerId "
					+ " left join CustomerReferencesEntity cre on (c.customerId=cre.customerId and cre.recordType<>'D')" 
					+ " where  le.recordType<>'D' ";

			if (Optional.ofNullable(bankLoanDetailsReportBean.getLoanStatus()).isPresent()) {
				sql += " and le.loanStatus=" + bankLoanDetailsReportBean.getLoanStatus() + " ";
			}
			if (Optional.ofNullable(bankLoanDetailsReportBean.getCustomerName()).isPresent()) {
				sql += " and concat(inqe.firstName,' ',inqe.lastName ) like '%" + bankLoanDetailsReportBean.getCustomerName() + "%'";
			}
			if (Optional.ofNullable(bankLoanDetailsReportBean.getFromDate()).isPresent()) {
				dateto = formatter.format(bankLoanDetailsReportBean.getFromDate()) + " 00:00:00";
				sql += " and le.amountDisbursedDate>='" + dateto + "' ";
			}
			if (Optional.ofNullable(bankLoanDetailsReportBean.getToDate()).isPresent()) {
				dateto = formatter.format(bankLoanDetailsReportBean.getToDate()) + " 00:00:00";
				sql += " and le.amountDisbursedDate<='" + dateto + "' ";
			}
			sql += " group by le.loanNo order by le.loanId desc";
			buisnessDetailsList = (List<InquiryTO>) getSession().createQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(InquiryTO.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
			return buisnessDetailsList;
		}
		return buisnessDetailsList;
	}
}
