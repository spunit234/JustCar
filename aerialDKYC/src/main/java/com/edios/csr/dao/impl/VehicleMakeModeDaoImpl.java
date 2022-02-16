
package com.edios.csr.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.impl.BaseDaoImpl;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.VehicleMakeBean;
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

@Repository
@SuppressWarnings({ "unchecked", "deprecation" })
public class VehicleMakeModeDaoImpl extends BaseDaoImpl<CustomerEntity> implements VehicleMakeModeDao {

	@Autowired
	MessageSource messageSource;


	@Override
	public List<VehicleMakeTo> fetchVehicleMake(VehicleMakeBean vehicleMakeBean) {
		List<VehicleMakeTo> vehicleMakeTo = null;
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		if (Optional.ofNullable(vehicleMakeBean.getMakeStatus()).isPresent()) {
			if (vehicleMakeBean.getMakeStatus().equalsIgnoreCase("All")) {
				sqlQuery = "Select vm.makeId as makeId,vm.makeName as makeName,vm.transactionCount as transactionCount,vm.makeStatus as makeStatus "
						+ " from VehicleMakeEntity vm where vm.recordType<>'D'  order by vm.makeName asc";
			} else {
				sqlQuery = "Select vm.makeId as makeId,vm.makeName as makeName,vm.transactionCount as transactionCount,vm.makeStatus as makeStatus "
						+ " from VehicleMakeEntity vm where vm.recordType<>'D' and vm.makeStatus='"
						+ vehicleMakeBean.getMakeStatus() + "'  order by vm.makeName asc";
			}
		} else {
			sqlQuery = "Select vm.makeId as makeId,vm.makeName as makeName,vm.transactionCount as transactionCount,vm.makeStatus as makeStatus "
					+ " from VehicleMakeEntity vm where vm.recordType<>'D' and vm.makeStatus='Active' order by vm.makeName asc";
		}
		vehicleMakeTo = (List<VehicleMakeTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(VehicleMakeTo.class)).list();

		return vehicleMakeTo;
	}

	@Override
	public List<VehicleModelTo> fetchVehicleModel(PayloadBean payloadBean) {
		List<VehicleModelTo> vehicleModelTo = null;
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		if (Optional.ofNullable(payloadBean.getSearchParameter()).isPresent()) {
			if (payloadBean.getSearchParameter().equalsIgnoreCase("All")) {
				sqlQuery = "Select vm.modelId as modelId,vm.modelName as modelName,vm.transactionCount as transactionCount,vm.modelStatus as modelStatus from VehicleModelEntity vm"
						+ "  where vm.recordType<>'D'  and  vm.makeId=" + payloadBean.getId()
						+ " order by vm.modelName asc";
			} else {
				sqlQuery = "Select vm.modelId as modelId,vm.modelName as modelName,vm.transactionCount as transactionCount,vm.modelStatus as modelStatus from VehicleModelEntity vm"
						+ "  where vm.recordType<>'D' and vm.modelStatus='" + payloadBean.getSearchParameter()
						+ "' and  vm.makeId=" + payloadBean.getId() + " order by vm.modelName asc";
			}
		} else {
			sqlQuery = "Select vm.modelId as modelId,vm.modelName as modelName,vm.transactionCount as transactionCount,vm.modelStatus as modelStatus from VehicleModelEntity vm"
					+ "  where vm.recordType<>'D' and vm.modelStatus='Active' and  vm.makeId=" + payloadBean.getId()
					+ " order by vm.modelName asc";
		}
		vehicleModelTo = (List<VehicleModelTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(VehicleModelTo.class)).list();

		return vehicleModelTo;
	}

	

	@Override
	public List<VehicleModelSpecsBean> fetchVehicleModelPrice(PayloadBean payloadBean) {
		List<VehicleModelSpecsBean> vehicleModelSpecsBeanList = null;
		String dateFormat = messageSource.getMessage("dateFormat", null, "", Locale.US);
		String whereClause = "";
//		if(payloadBean.getStatus().equals("Active") || payloadBean.getStatus().equals("Inactive"))
//			whereClause = " AND prices.priceTypeStatus = '" + payloadBean.getStatus() + "' ";
		String sqlQuery = "Select  prices.vehicleModelSpecId as vehicleModelSpecId,DATE_FORMAT(prices.createdDate,'"+dateFormat+"') as priceDateStr, "
				+ " apleTransmission.parameterListValue as transmissionName, apleBodyType.parameterListValue as bodyTypeName,model.modelName as modelName,"
				+ " apleFuelType.parameterListValue as fuelTypeName , prices.transactionCount as transactionCount,model.modelId as vehModelId,"
				+ " prices.sunroof as sunroof, prices.musicSystem as musicSystem, prices.alloysWheel as alloysWheel , prices.engineCC as engineCC"
				+ ", prices.sittingCapacity as sittingCapacity, prices.vehicleCylinders as vehicleCylinders, apleColour.parameterListValue as colourName, "
				+ " prices.fuelType as fuelType, prices.transmission as transmission,prices.bodyType as bodyType ,prices.colour as colour "
				+ " from VehicleModelSpecsEntity prices "
				+ " inner join VehicleModelEntity model on ( prices.modelId = model.modelId AND model.makeId = " + payloadBean.getId()
				+ "  AND model.recordType<>'D' ) "
				+ " left join ApplicationParameterListEntity apleFuelType on (apleFuelType.parameterListID = prices.fuelType) "
				+ " left join ApplicationParameterListEntity apleTransmission on (apleTransmission.parameterListID = prices.transmission) "
				+ " left join ApplicationParameterListEntity apleBodyType on (apleBodyType.parameterListID = prices.bodyType) "
				+ " left join ApplicationParameterListEntity apleColour on (apleColour.parameterListID = prices.colour) "
				+ " where prices.recordType<>'D' " + whereClause;
//				+ " group by prices.priceDate, prices.modelId, prices.fuelTypeId , prices.transmissionId, prices.bodyTypeId,"
//				+ " prices.sunroof, prices.musicSystem, prices.alloyWheels, prices.engineCC"
//				+ ", prices.seatingCapacity, prices.cylinders, prices.colourId,prices.priceTypeStatus";
		
		vehicleModelSpecsBeanList = (List<VehicleModelSpecsBean>) getSession().createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(VehicleModelSpecsBean.class)).list();

		return vehicleModelSpecsBeanList;
	}



	@Override
	public boolean deleteMake(Long deleteRecordId, Long lastModifiedBy, Long transactionCount) {
		boolean result = true;
		try {
			String hql = "update VehicleMakeEntity vm set  vm.recordType='D' , vm.lastModifiedBy=:lastModifiedBy,vm.lastModifiedDate=:lastModifiedDate ,"
					+ " vm.transactionCount=:transactionCount WHERE vm.makeId=:deleteRecordId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("lastModifiedBy", lastModifiedBy);
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("deleteRecordId", deleteRecordId);
			query.setParameter("transactionCount", transactionCount + 1);
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return result;
	}

	@Override
	public boolean deleteModel(Long deleteRecordId, Long lastModifiedBy, Long transactionCount) {
		boolean result = true;
		try {
			String hql = "update VehicleModelEntity vm set  vm.recordType='D' , vm.lastModifiedBy=:lastModifiedBy,vm.lastModifiedDate=:lastModifiedDate ,"
					+ " vm.transactionCount=:transactionCount WHERE vm.modelId=:deleteRecordId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("lastModifiedBy", lastModifiedBy);
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("deleteRecordId", deleteRecordId);
			query.setParameter("transactionCount", transactionCount + 1);
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return result;
	}

	@Override
	public boolean addMake(VehicleMakeEntity vehicleMakeEntity) {
		boolean result = true;
		try {
			entityManager.persist(vehicleMakeEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean addModel(VehicleModelEntity vehicleModelEntity) {
		boolean result = true;
		try {
			entityManager.persist(vehicleModelEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean isMakeNameExist(String makeName) {
		try {
			return entityManager.createQuery("select table.makeName as makeName from VehicleMakeEntity table where "
					+ " table.makeName='" + makeName + "' and table.recordType<>'D'").getSingleResult() == null ? false
							: true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@Override
	public boolean isModelNameExist(String modelName) {
		try {
			return entityManager
					.createQuery("select table.modelName as modelName from VehicleModelEntity table where "
							+ " table.modelName='" + modelName + "' and table.recordType<>'D'")
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@Override
	public boolean isMakeNameExistWhileUpdate(String makeName, Long makeId) {
		try {
			return entityManager.createQuery("select table.makeName as makeName from VehicleMakeEntity table where "
					+ " table.makeName='" + makeName + "' and table.makeId!=" + makeId + " and table.recordType<>'D'")
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@Override
	public boolean isModelNameExistWhileUpdate(String modelName, Long modelId) {
		try {
			return entityManager.createQuery(
					"select table.modelName as modelName from VehicleModelEntity table where " + " table.modelName='"
							+ modelName + "' and table.modelId!=" + modelId + " and table.recordType<>'D'")
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@Override
	public boolean updateMake(VehicleMakeEntity vehicleMakeEntity) {
		boolean result = true;
		try {
			// entityManager.merge(siteEntity);
			Session session = (Session) entityManager.getDelegate();
			session.update(vehicleMakeEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean updateModel(VehicleModelEntity vehicleModelEntity) {
		boolean result = true;
		try {
			// entityManager.merge(siteEntity);
			Session session = (Session) entityManager.getDelegate();
			session.update(vehicleModelEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public VehicleMakeEntity findMakeById(Long id) {
		VehicleMakeEntity vehicleMakeEntity = null;
		try {
			entityManager.getDelegate();
			vehicleMakeEntity = entityManager.find(VehicleMakeEntity.class, id);
		} catch (NoResultException noResultException) {
			noResultException.printStackTrace();
			return null;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return vehicleMakeEntity;
	}

	@Override
	public VehicleModelEntity findModelById(Long id) {
		VehicleModelEntity vehicleModelEntity = null;
		try {
			entityManager.getDelegate();
			vehicleModelEntity = entityManager.find(VehicleModelEntity.class, id);
		} catch (NoResultException noResultException) {
			noResultException.printStackTrace();
			return null;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return vehicleModelEntity;
	}
	
	
	
	@Override
	public List<VehicleModelPricesBean> findModelPriceDetailsBySpecsId(Long vehicleModelSpecId) {
		List<VehicleModelPricesBean> vehicleModelPricesBeanList = null;
		try {

			String sqlQuery = "";
			sqlQuery = "select vmpe.priceTypeId as parameterListID, "
					+ " vmpe.priceValue as priceValue, vmpe.priceTypeStatus as priceTypeStatus,vmpe.vehicleModelPriceId as vehicleModelPriceId "
					+ " from VehicleModelPricesEntity vmpe "
					+ " where vmpe.vehicleModelSpecId.vehicleModelSpecId=:vehicleModelSpecId AND vmpe.recordType<>'D'";
			vehicleModelPricesBeanList = (List<VehicleModelPricesBean>) getSession().createQuery(sqlQuery)
					 .setParameter("vehicleModelSpecId", vehicleModelSpecId)
					.setResultTransformer(Transformers.aliasToBean(VehicleModelPricesBean.class)).list();
		} catch (NoResultException noResultException) {
			noResultException.printStackTrace();
			return null;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return vehicleModelPricesBeanList;

	}

	@Override
	public Long addVehicleModelSpecs(VehicleModelSpecsEntity vehicleModelSpecsEntity) {
		
		VehicleModelSpecsEntity vehicleModelSpecsId = entityManager.merge(vehicleModelSpecsEntity);
		
		
		return vehicleModelSpecsId.getVehicleModelSpecId();
	}

	
	@Override
	public void addVehicleModelPrices(VehicleModelPricesEntity vehicleModelPricesEntity) {
			entityManager.persist(vehicleModelPricesEntity);
	}

	@Override
	public void updateVehiclePrices(VehicleModelPricesBean vehicleModelPriceBean) {
		String hql = "";
//		VehicleModelSpecsEntity transactionData = null;
////		if(vehicleModelPricesEntity.getRecordType().equals("D")) {
////		  hql = "update VehicleModelPricesEntity vmpe set  vmpe.recordType='D' , vmpe.lastModifiedBy=:lastModifiedBy,"
////				+ " vmpe.lastModifiedDate=:lastModifiedDate ,"
////				+ " vmpe.transactionCount=transactionCount+1 WHERE vmpe.vehicleModelPriceId=:vehicleModelPriceId";
////		  	Query query = getSession().createQuery(hql);
////			query.setParameter("lastModifiedBy", vehicleModelPricesEntity.getLastModifiedBy());
////			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
////			query.setParameter("vehicleModelSpecId", vehicleModelPricesEntity.getVehicleModelPriceId());
////			query.setParameter("vehicleModelPriceId", vehicleModelPricesEntity.getRecordType());
////			query.executeUpdate();
////		}
////		else {
			 hql = "update VehicleModelPricesEntity vmpe set  vmpe.recordType='U' , vmpe.lastModifiedBy=:lastModifiedBy,"
						+ " vmpe.lastModifiedDate=:lastModifiedDate ,vmpe.priceValue=:priceValue,vmpe.priceTypeStatus=:priceTypeStatus, "
						+ " vmpe.transactionCount=transactionCount+1 WHERE vmpe.vehicleModelPriceId=:vehicleModelPriceId";
				  	int query = getSession().createQuery(hql)
					.setParameter("lastModifiedBy", vehicleModelPriceBean.getLastModifiedBy())
					.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP)
					.setParameter("vehicleModelPriceId", vehicleModelPriceBean.getVehicleModelPriceId())
//					.setParameter("recordType", vehicleModelPriceBean.getRecordType())
					.setParameter("priceValue", vehicleModelPriceBean.getPriceValue())
					.setParameter("priceTypeStatus", vehicleModelPriceBean.getPriceTypeStatus())
					.executeUpdate();
////		}
		
	}

	@Override
	public List<VehiclePricesTo> fetchVehiclePriceDetails(PayloadBean payloadBean) {
		List<VehiclePricesTo> vehiclePricesTo = null;
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();

		sqlQuery = "select apl.parameterListID As parameterListID  ,apl.parameterListValue As parameterListValue"
				+ " ,vp.modelId as modelId,vp.vehiclePriceId as vehiclePriceId,vp.transactionCount as transactionCount,vp.priceDate as priceDate,"
				+ "vp.priceTypeId as priceTypeId ,vp.priceValue as priceValue,vp.priceTypeStatus as priceTypeStatus ,vp.createdBy as createdBy, "
				+ " vp.createdDate as createdDate ,vp.lastModifiedBy as lastModifiedBy ,vp.lastModifiedDate as lastModifiedDate"
				+ "  ,vp.priceValue as price,case when vp.priceTypeStatus  is not null then vp.priceTypeStatus  else 'Active' end as status from   ApplicationParameterListEntity apl "
				+ " left JOIN  VehicleModelSpecsEntity vp on (apl.parameterListID=vp.priceTypeId and vp.modelId="
				+ payloadBean.getCustom1() + " ) "
				+ "  left join vp.modelId where apl.parameterID=(select parameterID from ApplicationParameterEntity a where a.parameterCode='PRICE_TYPE') "
				+ "order by apl.parameterListValue ASC";
		vehiclePricesTo = (List<VehiclePricesTo>) session.createQuery(sqlQuery)
				// .setParameter("modelId", model)
				.setResultTransformer(Transformers.aliasToBean(VehiclePricesTo.class)).list();
		return vehiclePricesTo;
	}

	@Override
	public List<VehiclePricesTo> getModelReport(PayloadBean payloadBean) {
		List<VehiclePricesTo> vehiclePricesTo = null;
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();

		sqlQuery = "select apl.parameterListValue As parameterListValue,mi.makeName as makeName,"
				+ " vp.priceValue as priceValue,vp.priceTypeStatus as priceTypeStatus,vm.modelName as modelName from   ApplicationParameterListEntity apl"
				+ " left JOIN  VehicleModelSpecsEntity vp on (apl.parameterListID=vp.priceTypeId and vp.modelId="
				+ payloadBean.getCustom1() + " ) "
				+ " left JOIN vp.modelId vm left JOIN  vm.makeId mi  where apl.parameterID=(select parameterID from ApplicationParameterEntity a where a.parameterCode='PRICE_TYPE') "
				+ " and vp.priceValue is not  null order by apl.parameterListValue ASC";
		vehiclePricesTo = (List<VehiclePricesTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(VehiclePricesTo.class)).list();
		return vehiclePricesTo;
	}

	@Override
	public List<VehiclePricesTo> getMakeReport(PayloadBean payloadBean) {
		List<VehiclePricesTo> vehiclePricesTo = null;
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select vm.makeName as makeName,vmo.modelName as modelName,vp.priceValue as priceValue,vp.priceTypeStatus as priceTypeStatus,"
				+ " apl.parameterListValue As parameterListValue  from VehicleMakeEntity vm left join VehicleModelEntity vmo on vmo.makeId = vm.makeId "
				+ " left join VehicleModelSpecsEntity vp on vp.modelId=vmo.modelId   left join ApplicationParameterListEntity apl on apl.parameterListID=vp.priceTypeId "
				+ " where vm.makeId=" + payloadBean.getCustom1() + " and vp.priceValue is not  null";

		vehiclePricesTo = (List<VehiclePricesTo>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(VehiclePricesTo.class)).list();
		return vehiclePricesTo;
	}


	@Override
	public boolean isSameModelExist(VehicleModelSpecsBean vehicleModelSpecsBean) {		
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select vmse.vehicleModelSpecId "
				+ " from VehicleModelSpecsEntity vmse  "
				+ " where vmse.modelId=:modelId AND vmse.bodyType=:bodyType AND vmse.fuelType=:fuelType  AND"
				+ " vmse.transmission=:transmission AND vmse.sunroof=:sunroof AND vmse.musicSystem=:musicSystem AND vmse.alloysWheel=:alloysWheel AND "
				+ " vmse.engineCC=:engineCC AND vmse.sittingCapacity=:sittingCapacity AND vmse.vehicleCylinders=:vehicleCylinders AND "
				+ " vmse.recordType<>'D' ";
		
		if(vehicleModelSpecsBean.getColour() != null)
			sqlQuery += (" AND vmse.colour=  " + vehicleModelSpecsBean.getColour());
		else 
			sqlQuery += (" AND vmse.colour is null  ");

		 List<Long> vehicleModelSpecIdList= (List<Long>) session.createQuery(sqlQuery)
				.setParameter("modelId", vehicleModelSpecsBean.getModelId())
				.setParameter("bodyType", vehicleModelSpecsBean.getBodyType())
				.setParameter("fuelType", vehicleModelSpecsBean.getFuelType())
//				.setParameter("colour", vehicleModelSpecsBean.getColour())
				.setParameter("transmission", vehicleModelSpecsBean.getTransmission())
				.setParameter("sunroof", vehicleModelSpecsBean.getSunroof())
				.setParameter("musicSystem", vehicleModelSpecsBean.getMusicSystem())
				.setParameter("alloysWheel", vehicleModelSpecsBean.getAlloysWheel())
				.setParameter("engineCC", vehicleModelSpecsBean.getEngineCC())
				.setParameter("sittingCapacity", vehicleModelSpecsBean.getSittingCapacity())
				.setParameter("vehicleCylinders", vehicleModelSpecsBean.getVehicleCylinders())
				.list();
		 
		 if(!vehicleModelSpecIdList.isEmpty())
			 return true;
		return false;
	}

	@Override
	public VehicleModelSpecsEntity fetchVehicleModelSpecsDetailsByID(Long vehicleModelSpecId) {
		VehicleModelSpecsEntity transactionData = null;
		try {
			
										
			transactionData =   getSession().get(VehicleModelSpecsEntity.class,vehicleModelSpecId);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;	
	}

	

}
