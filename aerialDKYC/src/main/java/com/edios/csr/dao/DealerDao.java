package com.edios.csr.dao;

import java.util.List;

import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.DealerBean;
import com.edios.csr.bean.DealerExecutiveBean;
import com.edios.csr.entity.DealerEntity;
import com.edios.csr.entity.DealerExecutiveEntity;
import com.edios.csr.entity.to.DealerExecutiveTO;
import com.edios.csr.entity.to.DealerTO;

public interface DealerDao {
	String addDealer(DealerEntity customerEntity);

	String addDealerExecutive(DealerExecutiveEntity customerEntity);

	int checkContactNumber(String contactNumber);

	int checkContactNumberUpdate(String contactNumber, Long dealerId);

	public int checkContactNumberExecutive(String contactNumber);

	public int checkContactNumberUpdateExecutive(String contactNumber, Long dealerId);

	List<DealerExecutiveBean> fetchdealerExecutiveData(PayloadBean customerBean);

	List<DealerTO> fetchdealerData(DealerBean customerBean);

	List<DealerTO> searchdealerData(DealerBean payloadBean);

	List<DealerExecutiveTO> searchdealerExecutiveData(PayloadBean payloadBean);

	List<DealerExecutiveTO> searchdealerExecutiveData1(String payloadBean);

	boolean updateDealer(DealerEntity customerEntity);

	boolean updateDealerExecutive(DealerExecutiveEntity customerEntity);

	List<DealerBean> editDealer(PayloadBean payloadbean);

	boolean deleteDealerExecutive(DeleteRecords deleteRecords);

	boolean isDealerExecutiveNumberExist(String contactNumber);

	boolean isDealerExecutiveNumberExistWhileUpdate(String contactNumber, Long dealerExecutiveId);
}
