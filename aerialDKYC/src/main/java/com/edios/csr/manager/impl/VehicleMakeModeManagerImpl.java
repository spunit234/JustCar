
package com.edios.csr.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.manager.impl.AbstractManagerImpl;
import com.edios.cdf.util.AppConstants;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.CustomerBean;
import com.edios.csr.bean.VehicleMakeBean;
import com.edios.csr.bean.VehicleModelBean;
import com.edios.csr.bean.VehicleModelPricesBean;
import com.edios.csr.bean.VehicleModelSpecsBean;
import com.edios.csr.dao.VehicleMakeModeDao;
import com.edios.csr.entity.CustomerEntity;
import com.edios.csr.entity.VehicleMakeEntity;
import com.edios.csr.entity.VehicleModelEntity;
import com.edios.csr.entity.VehicleModelPricesEntity;
import com.edios.csr.entity.VehicleModelSpecsEntity;
import com.edios.csr.entity.to.VehicleMakeTo;
import com.edios.csr.entity.to.VehicleModelTo;
import com.edios.csr.entity.to.VehiclePricesTo;
import com.edios.csr.manager.VehicleMakeModeManager;

@Service("vehicleMakeModeManager")
public class VehicleMakeModeManagerImpl extends AbstractManagerImpl<CustomerBean, CustomerEntity>
		implements VehicleMakeModeManager {

	@Autowired
	VehicleMakeModeDao inquiryDao;

	@Autowired
	MessageSource messageSource;

	@Override
	@Transactional("db1Tx")
	public List<VehicleMakeTo> fetchVehicleMake(VehicleMakeBean vehicleMakeBean) {
		return inquiryDao.fetchVehicleMake(vehicleMakeBean);
	}

	@Override
	@Transactional("db1Tx")
	public List<VehicleModelTo> fetchVehicleModel(PayloadBean payloadBean) {
		return inquiryDao.fetchVehicleModel(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public List<VehicleModelSpecsBean> fetchVehicleModelPrice(PayloadBean payloadBean) {
		return inquiryDao.fetchVehicleModelPrice(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public String deleteMake(DeleteRecords deleteRecords) {
		String resultString = "Used";
		boolean resultFlag = false;
		Long l = Long.valueOf(deleteRecords.getModifiedBy());

		resultFlag = inquiryDao.deleteMake(deleteRecords.getId(), l, deleteRecords.getTransactionCount());
		if (resultFlag)
			return "DELETED";
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String deleteModel(DeleteRecords deleteRecords) {
		String resultString = "Used";
		boolean resultFlag = false;
		Long l = Long.valueOf(deleteRecords.getModifiedBy());

		resultFlag = inquiryDao.deleteModel(deleteRecords.getId(), l, deleteRecords.getTransactionCount());
		if (resultFlag)
			return "DELETED";
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String addMake(VehicleMakeBean vehicleMakeBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			vehicleMakeBean.setTransactionCount(1L);
			vehicleMakeBean.setCreatedDate(new Date());
			vehicleMakeBean.setRecordType('I');
			resultFlag = inquiryDao.isMakeNameExist(vehicleMakeBean.getMakeName());
			if (resultFlag) {
				return "MakeNameAlreadyExist";
			}

			VehicleMakeEntity vehicleMakeEntity = mapper.map(vehicleMakeBean, VehicleMakeEntity.class);
			resultFlag = inquiryDao.addMake(vehicleMakeEntity);
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
	public String addModel(VehicleModelBean vehicleModelBean) {
		String resultString = "";
		boolean resultFlag = false;
		try {
			vehicleModelBean.setTransactionCount(1L);
			vehicleModelBean.setCreatedDate(new Date());
			vehicleModelBean.setRecordType('I');
			resultFlag = inquiryDao.isModelNameExist(vehicleModelBean.getModelName());
			if (resultFlag) {
				return "ModelNameAlreadyExist";
			}
			VehicleModelEntity vehicleModelEntity = mapper.map(vehicleModelBean, VehicleModelEntity.class);
			resultFlag = inquiryDao.addModel(vehicleModelEntity);
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
	public String updateMake(VehicleMakeBean vehicleMakeBean) {
		String resultString = "";
		boolean resultFlag = false;
		boolean resultFlag1 = false;
		resultFlag1 = inquiryDao.isMakeNameExistWhileUpdate(vehicleMakeBean.getMakeName(), vehicleMakeBean.getMakeId());
		if (resultFlag1) {
			return "CodeAlreadyExist";
		}
		VehicleMakeEntity vehicleMakeEntity = mapper.map(vehicleMakeBean, VehicleMakeEntity.class);
		resultFlag = inquiryDao.updateMake(vehicleMakeEntity);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String updateModel(VehicleModelBean vehicleModelBean) {
		String resultString = "";
		boolean resultFlag = false;
		boolean resultFlag1 = false;
		resultFlag1 = inquiryDao.isModelNameExistWhileUpdate(vehicleModelBean.getModelName(),
				vehicleModelBean.getModelId());
		if (resultFlag1) {
			return "CodeAlreadyExist";
		}
		VehicleModelEntity vehicleModelEntity = mapper.map(vehicleModelBean, VehicleModelEntity.class);
		resultFlag = inquiryDao.updateModel(vehicleModelEntity);
		if (resultFlag) {
			return "UPDATED";
		}
		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public VehicleMakeBean findMakeById(Long id) {
		VehicleMakeBean vehicleMakeBean = null;
		VehicleMakeEntity vehicleMakeEntity = inquiryDao.findMakeById(id);
		vehicleMakeBean = mapper.map(vehicleMakeEntity, VehicleMakeBean.class);
		return vehicleMakeBean;
	}

	@Override
	@Transactional("db1Tx")
	public VehicleModelBean findModelById(Long id) {
		VehicleModelBean vehicleModelBean = null;
		VehicleModelEntity vehicleModelEntity = inquiryDao.findModelById(id);
		vehicleModelBean = mapper.map(vehicleModelEntity, VehicleModelBean.class);
		return vehicleModelBean;
	}

	@Override
	@Transactional("db1Tx")
	public List<VehicleModelPricesBean> findModelPriceDetailsBySpecsId(Long id) {
		return inquiryDao.findModelPriceDetailsBySpecsId(id);
	}

	@Override
	@Transactional("db1Tx")
	public String addVehicleModelPrices(VehicleModelSpecsBean vehicleModelSpecsBean) {
		String resultString = "";

		boolean isSameModelExist = inquiryDao.isSameModelExist(vehicleModelSpecsBean);
		if (isSameModelExist)
			return "RecordAlreadyExist";

		vehicleModelSpecsBean.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
		vehicleModelSpecsBean.setRecordType(AppConstants.RECORDTYPE_INSERT);
		vehicleModelSpecsBean.setCreatedDate(new Date());
		vehicleModelSpecsBean.setPriceDate(vehicleModelSpecsBean.getCreatedDate());

		VehicleModelSpecsEntity vehicleModelSpecsEntity = mapper.map(vehicleModelSpecsBean,
				VehicleModelSpecsEntity.class);
		Long vehicleModelSpecId = inquiryDao.addVehicleModelSpecs(vehicleModelSpecsEntity);
		if (vehicleModelSpecId != null) {
			VehicleModelSpecsEntity vehicleModelSpecsId;
			for (VehicleModelPricesBean vehicleModelPricesBean : vehicleModelSpecsBean.getVehicleModelPricesBean()) {
				if (vehicleModelPricesBean.getPriceTypeId() != null && vehicleModelPricesBean.getPriceValue() != null) {
					VehicleModelPricesEntity vehicleModelPricesEntity = mapper.map(vehicleModelPricesBean,
							VehicleModelPricesEntity.class);

					vehicleModelSpecsId = new VehicleModelSpecsEntity();
					vehicleModelSpecsId.setVehicleModelSpecId(vehicleModelSpecId);
					vehicleModelPricesEntity.setVehicleModelSpecId(vehicleModelSpecsId);
					vehicleModelPricesEntity.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
					vehicleModelPricesEntity.setRecordType(AppConstants.RECORDTYPE_INSERT);
					vehicleModelPricesEntity.setCreatedDate(new Date());
					vehicleModelPricesEntity.setCreatedBy(vehicleModelSpecsBean.getCreatedBy());

					inquiryDao.addVehicleModelPrices(vehicleModelPricesEntity);
				}
			}
			resultString = "ADDED";
		}

		return resultString;
	}

	@Override
	@Transactional("db1Tx")
	public String updateVehiclePrices(VehicleModelSpecsBean vehicleModelSpecsBean) {

		VehicleModelSpecsEntity latestData = inquiryDao
				.fetchVehicleModelSpecsDetailsByID(vehicleModelSpecsBean.getVehicleModelSpecId());

		if (latestData.getTransactionCount() == 0 || latestData.getRecordType() == 'D')
			return "RecordDoesNotExist";
		else if (latestData.getTransactionCount() > (vehicleModelSpecsBean.getTransactionCount()))
			return "TransactionFailed";

		latestData.setTransactionCount(vehicleModelSpecsBean.getTransactionCount() + 1);
		latestData.setLastModifiedDate(new Date());
		latestData.setLastModifiedBy(vehicleModelSpecsBean.getLastModifiedBy());
		latestData.setPriceDate(new Date());

		if (vehicleModelSpecsBean.getRecordType().equals('U')) {
			latestData.setRecordType('U');

			for (VehicleModelPricesBean vehicleModelPricesBean : vehicleModelSpecsBean.getVehicleModelPricesBean()) {
				if (vehicleModelPricesBean.getPriceTypeId() != null) {
					if (vehicleModelPricesBean.getVehicleModelPriceId() != null) {
						if (vehicleModelPricesBean.getPriceValue() == null)
							vehicleModelPricesBean.setPriceValue(0.0);
						vehicleModelPricesBean.setLastModifiedBy(vehicleModelSpecsBean.getLastModifiedBy());
						inquiryDao.updateVehiclePrices(vehicleModelPricesBean);
					} else if (vehicleModelPricesBean.getPriceValue() != null) {

						VehicleModelPricesEntity vehicleModelPricesEntity = mapper.map(vehicleModelPricesBean,
								VehicleModelPricesEntity.class);
						vehicleModelPricesEntity.setVehicleModelSpecId(latestData);
						vehicleModelPricesEntity.setTransactionCount(AppConstants.TRANSACTION_BEGIN);
						vehicleModelPricesEntity.setRecordType(AppConstants.RECORDTYPE_INSERT);
						vehicleModelPricesEntity.setCreatedDate(new Date());
						vehicleModelPricesEntity.setCreatedBy(vehicleModelSpecsBean.getLastModifiedBy());

						inquiryDao.addVehicleModelPrices(vehicleModelPricesEntity);
					}

				}
			}
			return "UPDATED";
		} else {

			latestData.setRecordType('D');
			return "DELETED"; // automatically update the updated fields
		}

		// latestData will automatically call update for the updated fields

	}

	@Override
	@Transactional("db1Tx")
	public List<VehiclePricesTo> fetchVehiclePriceDetails(PayloadBean payloadBean) {
		return inquiryDao.fetchVehiclePriceDetails(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public List<VehiclePricesTo> getModelReport(PayloadBean payloadBean) {
		return inquiryDao.getModelReport(payloadBean);
	}

	@Override
	@Transactional("db1Tx")
	public List<VehiclePricesTo> getMakeReport(PayloadBean payloadBean) {
		return inquiryDao.getMakeReport(payloadBean);
	}

}
