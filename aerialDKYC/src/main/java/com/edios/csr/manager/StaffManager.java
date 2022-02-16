package com.edios.csr.manager;

import java.util.List;

import com.edios.cdf.manager.AbstractManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.StaffBean;
import com.edios.csr.entity.to.StaffTo;




public interface StaffManager extends AbstractManager {
	
	List<StaffTo> fetchSearchStaff(PayloadBean payloadBean);
	List<StaffTo> fetchLeadStaff(PayloadBean payloadBean);
	/*String addStaff(StaffTo staffTO);*/
	String addStaff(StaffBean staffBean);
	StaffBean findStaffById(Long id);
	String updateStaff(StaffBean staffBean);
	String deleteStaff(DeleteRecords deleteRecords);

}
