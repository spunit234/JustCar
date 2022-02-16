package com.edios.csr.manager;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.manager.AbstractManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.InsuranceCoversBean;
import com.edios.csr.bean.InsuranceNomineesBean;
import com.edios.csr.bean.InsurancePaymentsBean;
import com.edios.csr.bean.ProjectDocumentBean;
import com.edios.csr.bean.VehicleInsuranceBean;
import com.edios.csr.entity.to.VehicleInsuranceManageTO;
import com.edios.csr.entity.to.VendorDocumentTO;

public interface VehicleInsuranceManager extends AbstractManager{

	String addVehicleInsuranceDetails(VehicleInsuranceBean vehicleInsuranceBean);

	List<VehicleInsuranceManageTO> getManageInsuranceDetails(VehicleInsuranceManageTO vehicleInsuranceManage);

	VehicleInsuranceManageTO getInsuranceDetailsByVehicleId(PayloadBean payloadBean);

	String updateOrDeleteInsuranceDetails(VehicleInsuranceBean vehicleInsuranceBean);

	String addVehicleInsuranceDetails(AbstractBean insurancePaymentsBean,String beanType);

	List<InsurancePaymentsBean> getInsurancePaymentDetails(PayloadBean payloadBean);

	String updateOrDeletePaymentDetails(InsurancePaymentsBean insurancePaymentsBean);

	String addInsuranceNewCoversDetails(InsuranceCoversBean insuranceNewCoversBean);

	String updateOrDeleteNewCoversDetails(InsuranceCoversBean insuranceNewCoversBean);

	List<InsuranceCoversBean> getInsuranceNewCoverDetails(PayloadBean payloadBean);

	List<InsuranceCoversBean> getInsurancePRVCoverDetails(PayloadBean payloadBean);

	String addInsurancePrvCoversDetails(InsuranceCoversBean insurancePrvCoversBean);

	String updateOrDeletePrvCoversDetails(InsuranceCoversBean insurancePrvCoversBean);

	String addInsuranceNomineeDetails(InsuranceNomineesBean insuranceNomineesBean);

	String updateOrDeleteNomineeDetails(InsuranceNomineesBean insuranceNomineesBean);

	List<InsuranceNomineesBean> getInsuranceNomineeDetails(PayloadBean payloadBean);

	List<VehicleInsuranceManageTO> getExistingInsuranceDetails(PayloadBean payloadbean);

	String uploadInsuranceDocument(ProjectDocumentBean projectDocumentBean, MultipartFile file);

	List<VendorDocumentTO> fetchInsuranceDocumentDetails(Long id);

	String deleteInsuranceDocument(DeleteRecords deleteRecords);

}
