package com.edios.csr.dao;

import java.util.List;

import com.edios.cdf.entity.AbstractEntity;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.InsuranceCoversBean;
import com.edios.csr.bean.InsuranceNomineesBean;
import com.edios.csr.bean.InsurancePaymentsBean;
import com.edios.csr.entity.to.VehicleInsuranceManageTO;
import com.edios.csr.entity.to.VendorDocumentTO;

public interface VehicleInsuranceDao {

	Long addVehicleInsuranceDetails(AbstractEntity vehicleInsuranceEntity);

	List<VehicleInsuranceManageTO> getManageInsuranceDetails(VehicleInsuranceManageTO vehicleInsuranceManag);

	VehicleInsuranceManageTO getInsuranceDetailsByVehicleId(Long id);

	AbstractEntity fetchInsuranceDetailsByID(String string, Long vehicleInsuranceId);

	List<InsurancePaymentsBean> getInsurancePaymentDetails(Long id);

	List<InsuranceCoversBean> getInsuranceNewCoversDetails(Long id);

	List<InsuranceCoversBean> getInsurancePRVCoverDetails(Long id);

	List<InsuranceNomineesBean> getInsuranceNomineeDetails(Long id);

	List<VehicleInsuranceManageTO> getExistingInsuranceDetails(PayloadBean payloadbean);

	List<AbstractEntity> fetchInsuranceDetailsByInsuranceID(String entityName, Long vehicleInsuranceId);

	List<VendorDocumentTO> fetchInsuranceDocumentDetails(Long vehicleInsuranceId);

	boolean deleteInsuranceDocument(Long id, Long l);

	boolean uploadInsuranceDocumentEntity(Long insuranceDocumentId, Long storageId, String fileName, Integer createdBy);
}
