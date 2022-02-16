package com.edios.csr.dao;

import java.util.List;

import com.edios.cdf.dao.BaseDao;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.util.TransactionData;
import com.edios.csr.entity.StaffEntity;
import com.edios.csr.entity.to.StaffTo;





public interface StaffDao extends BaseDao<StaffEntity> {
	List<StaffTo> fetchSearchStaff(PayloadBean payloadBean);
	List<StaffTo> fetchLeadStaff(PayloadBean payloadBean);
	boolean addStaff(StaffEntity driverEntity);
	StaffEntity findStaffById(Long id);
	boolean updateStaff(StaffEntity siteEntity);
	TransactionData fetchTransactionDataById(Long staffID);
	boolean isStaffExists(Long id);
	boolean deleteStaff(Long id, Long modifiedBy);
	String findStaffCodeByStaffType(String staffCode);
	boolean isStaffCodeExistWhileUpdate(String staffCode, Long staffId);
	String findSiteById(Long siteId);
	boolean isContactNoExist(String contactNumber,Long staffId);
	

}
