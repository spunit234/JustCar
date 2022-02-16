package com.edios.csr.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.manager.impl.AbstractManagerImpl;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.StaffBean;
import com.edios.csr.dao.StaffDao;
import com.edios.csr.entity.StaffEntity;
import com.edios.csr.entity.to.StaffTo;
import com.edios.csr.manager.StaffManager;



@Service("staffManage")
public class StaffManagerImpl extends AbstractManagerImpl<StaffBean, StaffEntity>  implements StaffManager  {
	@Autowired
	StaffDao staffDao;
	
	@Override
	@Transactional("db1Tx")
	public List<StaffTo> fetchSearchStaff(PayloadBean payloadBean) {
		return staffDao.fetchSearchStaff(payloadBean);
	}
	
	@Override
	@Transactional("db1Tx")
	public List<StaffTo> fetchLeadStaff(PayloadBean payloadBean){
		return staffDao.fetchLeadStaff(payloadBean);
	}
	
	@Override
	@Transactional("db1Tx")
	public String addStaff(StaffBean staffTO) {
		boolean resultFlag = false;
		
		String staffCodePreviousUser = staffDao.findStaffCodeByStaffType(staffTO.getStaffType());
		String staffCode;

			if(staffTO.getStaffType().equals("Employee")) 
				staffCode = makeStaffCode(staffCodePreviousUser,"01");
			else if(staffTO.getStaffType().equals("Bank Staff"))
				staffCode = makeStaffCode(staffCodePreviousUser,"02");
			else if(staffTO.getStaffType().equals("RTO Agent"))
				staffCode = makeStaffCode(staffCodePreviousUser,"03");
			else if(staffTO.getStaffType().equals("Freelancer"))
				staffCode = makeStaffCode(staffCodePreviousUser,"04");
			else if(staffTO.getStaffType().equals("Others"))
				staffCode = makeStaffCode(staffCodePreviousUser,"99");
			else
				return "StaffCodeMappingNotFound";
			
			if(staffCode == null)
				return "StaffCodeMappingNotFound";
			else
				staffTO.setStaffCode(staffCode);;

		
		
		resultFlag = staffDao.isContactNoExist(staffTO.getContactNumber(),null);
		if(resultFlag)
			return "ContactNoAlreadyExist";
		
		staffTO.setCreatedDate(new Date());
		staffTO.setRecordType('I');
		staffTO.setTransactionCount(1L);
		StaffEntity driverEntity = mapper.map(staffTO, StaffEntity.class);
		staffDao.addStaff(driverEntity);
		return "Added";
	}

	
	private String makeStaffCode(String staffCodePreviousUser, String staffTypeCode) {
		String staffCode;
		if(staffCodePreviousUser != null) {
			// first split the staffCode in two parts and then convert the second part into integer after that increment the value by 1 then for zeros format the string and then in last add it to the first part
			if(staffCodePreviousUser.substring(0,2).equals(staffTypeCode))
				staffCode = staffCodePreviousUser.substring(0,2) + String.format("%04d", (Integer.parseInt(staffCodePreviousUser.substring(2))+1));
			else 
				return null;

		}
		else
			staffCode = staffTypeCode + "0001";
		
		return staffCode;
	}

	@Override
	@Transactional("db1Tx")
	public StaffBean findStaffById(Long id) {
		StaffBean  siteBean= null;
		StaffEntity siteEntity = staffDao.findStaffById(id);
		if(siteEntity instanceof StaffEntity) {
			siteBean = mapper.map(siteEntity, StaffBean.class);
			siteBean.setSiteName(staffDao.findSiteById(siteBean.getSiteId()));
			
		}
		return siteBean;
	}
	@Override
	@Transactional("db1Tx")
	public synchronized String updateStaff(StaffBean siteBean) {
		String resultString = "";
		boolean resultFlag = false;
		boolean resultFlag1 = false;
//		resultFlag1 = staffDao.isStaffCodeExistWhileUpdate(siteBean.getStaffCode(),siteBean.getStaffId());
//		if (resultFlag1) {
//			return "CodeAlreadyExist";
//		}
		resultFlag = staffDao.isContactNoExist(siteBean.getContactNumber(),siteBean.getStaffId());
		if(resultFlag)
			return "ContactNoAlreadyExist";
		StaffEntity siteEntity = mapper.map(siteBean,StaffEntity.class);
		resultFlag = staffDao.updateStaff(siteEntity);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}
	@Override
	@Transactional("db1Tx")
	public synchronized String deleteStaff(DeleteRecords deleteRecords) {
		String resultString = "Used";
		boolean resultFlag = false;
		Long l = Long.valueOf(deleteRecords.getModifiedBy());
		
		resultFlag = staffDao.deleteStaff(deleteRecords.getId(), l);
		if (resultFlag)
			return "DELETED";
		return resultString;
	}




}
