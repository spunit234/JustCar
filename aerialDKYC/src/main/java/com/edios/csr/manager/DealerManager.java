package com.edios.csr.manager;

import java.util.List;

import com.edios.cdf.manager.AbstractManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.DealerBean;
import com.edios.csr.bean.DealerExecutiveBean;
import com.edios.csr.entity.to.DealerExecutiveTO;
import com.edios.csr.entity.to.DealerTO;

public interface DealerManager extends AbstractManager {

	String addDealer(DealerBean customerBean);

	String addDealerExecutive(DealerExecutiveBean customerBean);

	List<DealerTO> fetchdealerData(DealerBean customerBean);

	List<DealerExecutiveBean> fetchdealerExecutiveData(PayloadBean customerBean);

	List<DealerExecutiveTO> searchdealerExecutiveData(PayloadBean payloadBean);

	List<DealerExecutiveTO> searchdealerExecutiveData1(String payloadBean);

	int checkContactNumber(String contactNumber);

	public int checkContactNumberUpdate(String contactNumber, Long dealerId);

	public int checkContactNumberExecutive(String contactNumber);

	public int checkContactNumberUpdateExecutive(String contactNumber, Long dealerId);

	String updateDealer(DealerBean customerBean);

	String updateDealerExecutive(DealerExecutiveBean customerBean);

	List<DealerTO> searchdealerData(DealerBean payloadBean);

	List<DealerBean> editDealer(PayloadBean payloadbean);

	String deleteDealer(DeleteRecords deleteRecords);

	String deleteDealerExecutive(DeleteRecords deleteRecords);
}
