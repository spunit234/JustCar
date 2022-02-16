
package com.edios.csr.manager;

import java.util.List;

import com.edios.cdf.manager.AbstractManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.VehicleMakeBean;
import com.edios.csr.bean.VehicleModelBean;
import com.edios.csr.bean.VehicleModelPricesBean;
import com.edios.csr.bean.VehicleModelSpecsBean;
import com.edios.csr.entity.to.VehicleMakeTo;
import com.edios.csr.entity.to.VehicleModelTo;
import com.edios.csr.entity.to.VehiclePricesTo;

public interface VehicleMakeModeManager extends AbstractManager{


	List<VehicleMakeTo> fetchVehicleMake(VehicleMakeBean vehicleMakeBean);
	List<VehicleModelTo> fetchVehicleModel(PayloadBean payloadBean);

	String deleteModel(DeleteRecords deleteRecords);
	String deleteMake(DeleteRecords deleteRecords);
	String addMake(VehicleMakeBean vehicleMakeBean);
	String addModel(VehicleModelBean vehicleModelBean);
	String updateMake(VehicleMakeBean vehicleMakeBean);
	String updateModel(VehicleModelBean vehicleModelBean);
	VehicleMakeBean findMakeById(Long id);
	VehicleModelBean findModelById(Long id);
	String addVehicleModelPrices(VehicleModelSpecsBean vehicleModelSpecsBean);
	String updateVehiclePrices(VehicleModelSpecsBean vehicleModelSpecsBean);
	List<VehiclePricesTo> fetchVehiclePriceDetails(PayloadBean payloadBean);
	List<VehiclePricesTo> getModelReport(PayloadBean payloadBean);
	List<VehiclePricesTo> getMakeReport(PayloadBean payloadBean);

	List<VehicleModelSpecsBean> fetchVehicleModelPrice(PayloadBean payloadBean);
	List<VehicleModelPricesBean> findModelPriceDetailsBySpecsId(Long id);

}
