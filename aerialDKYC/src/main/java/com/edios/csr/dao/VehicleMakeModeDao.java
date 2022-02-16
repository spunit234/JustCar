
package com.edios.csr.dao;

import java.util.List;

import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.VehicleMakeBean;
import com.edios.csr.bean.VehicleModelPricesBean;
import com.edios.csr.bean.VehicleModelSpecsBean;
import com.edios.csr.entity.VehicleMakeEntity;
import com.edios.csr.entity.VehicleModelEntity;
import com.edios.csr.entity.VehicleModelPricesEntity;
import com.edios.csr.entity.VehicleModelSpecsEntity;
import com.edios.csr.entity.to.VehicleMakeTo;
import com.edios.csr.entity.to.VehicleModelTo;
import com.edios.csr.entity.to.VehiclePricesTo;

public interface VehicleMakeModeDao {



	List<VehicleMakeTo> fetchVehicleMake(VehicleMakeBean vehicleMakeBean);

	List<VehicleModelTo> fetchVehicleModel(PayloadBean payloadBean);



	boolean deleteMake(Long deleteRecordId, Long lastModifiedBy, Long transactionCount);

	boolean deleteModel(Long deleteRecordId, Long lastModifiedBy, Long transactionCount);

	boolean addMake(VehicleMakeEntity vehicleMakeEntity);

	boolean addModel(VehicleModelEntity vehicleModelEntity);

	boolean isMakeNameExist(String makeName);

 	boolean isModelNameExist(String modelName);

 	boolean isMakeNameExistWhileUpdate(String makeName, Long makeId);

 	boolean isModelNameExistWhileUpdate(String modelName, Long modelId);

 	boolean updateMake(VehicleMakeEntity vehicleMakeEntity);
 	boolean updateModel(VehicleModelEntity vehicleModelEntity);

 	VehicleMakeEntity findMakeById(Long id);

 	VehicleModelEntity findModelById(Long id);

 	Long addVehicleModelSpecs(VehicleModelSpecsEntity vehicleModelSpecsEntity);
	
 	void addVehicleModelPrices(VehicleModelPricesEntity vehicleModelPricesEntity);


 	void updateVehiclePrices(VehicleModelPricesBean vehicleModelPriceBean);

 	List<VehiclePricesTo> fetchVehiclePriceDetails(PayloadBean payloadBean);

 	List<VehiclePricesTo> getModelReport(PayloadBean payloadBean);

 	List<VehiclePricesTo> getMakeReport(PayloadBean payloadBean);

	

 	List<VehicleModelSpecsBean> fetchVehicleModelPrice(PayloadBean payloadBean);

 	List<VehicleModelPricesBean> findModelPriceDetailsBySpecsId(Long vehicleModelSpecId);

 	boolean isSameModelExist(VehicleModelSpecsBean vehicleModelSpecsBean);

 	VehicleModelSpecsEntity fetchVehicleModelSpecsDetailsByID(Long vehicleModelSpecId);

}
