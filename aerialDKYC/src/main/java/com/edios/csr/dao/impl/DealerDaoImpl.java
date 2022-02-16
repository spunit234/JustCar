package com.edios.csr.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.impl.BaseDaoImpl;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.csr.bean.DealerBean;
import com.edios.csr.bean.DealerExecutiveBean;
import com.edios.csr.dao.DealerDao;
import com.edios.csr.entity.DealerEntity;
import com.edios.csr.entity.DealerExecutiveEntity;
import com.edios.csr.entity.to.DealerExecutiveTO;
import com.edios.csr.entity.to.DealerTO;
@Repository
@SuppressWarnings({"rawtypes","deprecation","unchecked" })
public class DealerDaoImpl extends BaseDaoImpl<DealerEntity> implements DealerDao {

	@Autowired
	MessageSource messageSource;

	@Override
	public String addDealer(DealerEntity customerEntity) {
		try {
			entityManager.persist(customerEntity);

			return customerEntity.getDealerId() + "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}
	
	@Override
	public String addDealerExecutive(DealerExecutiveEntity customerEntity) {
		try {
			entityManager.persist(customerEntity);

			return customerEntity.getDealerId() + "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}
public int checkContactNumber(String contactNumber) {
		
		
		Session session = (Session) entityManager.getDelegate();
		String sqlQuery = "";
		sqlQuery = "select dealer_Id from dealers where contact_Number = " + contactNumber;
		
	  
	Query b =	session.createSQLQuery(sqlQuery);
	   int c =    b.list().size();
			
		
		return c;
		
	}
   public int checkContactNumberUpdate(String contactNumber,Long dealerId) {
		
		
		Session session = (Session) entityManager.getDelegate();
		String sqlQuery = "";
		sqlQuery = "select dealer_Id from dealers where contact_Number = " + contactNumber + " and dealer_Id != " + dealerId ;
		
	  Query b =	session.createSQLQuery(sqlQuery);
	   int c =    b.list().size();
			
		
		return c;
		
	}
   
   public int checkContactNumberExecutive(String contactNumber) {
		
		
		Session session = (Session) entityManager.getDelegate();
		String sqlQuery = "";
		sqlQuery = "select dealer_Executive_Id from dealer_executives where contact_Number = " + contactNumber;
		
	  Query b =	session.createSQLQuery(sqlQuery);
	   int c =    b.list().size();
			
		
		return c;
		
	}
   
   public int checkContactNumberUpdateExecutive(String contactNumber,Long dealerId) {
		
		
		Session session = (Session) entityManager.getDelegate();
		String sqlQuery = "";
		sqlQuery = "select dealer_Executive_Id from dealer_executives where contact_Number = " + contactNumber + " and dealer_Executive_Id != " + dealerId ;
		
	  Query b =	session.createSQLQuery(sqlQuery);
	   int c =    b.list().size();
			
		
		return c;
		
	}
   @Override
	public List<DealerTO> fetchdealerData(DealerBean customerBean) {
		List<DealerTO> customerTO = null;
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select c.dealerId as dealerId,c.makeId as makeId  ,c.dealerName as dealerName, c.dealerType as dealerType , c.dealerStatus as dealerStaus"
				+ " from DealerEntity c " + " where c.contactNumber =" + customerBean.getContactNumber() + "AND"
				+ " c.recordType<>'D'  order by c.dealerId desc ";
		customerTO = (List<DealerTO>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(DealerTO.class)).list();

		return customerTO;
	}
	
	
	@Override
	public List<DealerExecutiveBean> fetchdealerExecutiveData(PayloadBean customerBean) {
		List<DealerExecutiveBean> customerTO = null;	
		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select c.dealerExecutiveId as dealerExecutiveId, c.dealerId as dealerId  ,c.dealerExecutiveName as dealerExecutiveName, c.executiveDesignation as executiveDesignation , c.contactNumber as contactNumber"
				+ " ,c.emailAddress as emailAddress, c.altContactNumber as altContactNumber, c.executiveLocation as executiveLocation, c.executiveStatus as executiveStatus, c.userActivityId as userActivityId, "
				+ " c.transactionCount as transactionCount , c.ipAddress as ipAddress , c.recordType as recordType , c.createdBy as createdBy, c.createdDate as createdDate, c.lastModifiedBy as lastModifiedBy, c.lastModifiedDate as lastModifiedDate"
				+ " from DealerExecutiveEntity c  where "
				+ " c.recordType<>'D' and c.dealerExecutiveId =" + customerBean.getId() +" order by c.dealerExecutiveId desc";
		customerTO = (List<DealerExecutiveBean>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(DealerExecutiveBean.class)).list();
		return customerTO;
	}
	
	

	@Override
	public List<DealerTO> searchdealerData(DealerBean payloadBean) {
		List<DealerTO> dealerto = null;

		try {
			Session session = (Session) entityManager.getDelegate();
			if (payloadBean.getDealerStatus().equalsIgnoreCase("Active")
					|| payloadBean.getDealerStatus().equalsIgnoreCase("Inactive")) {
				String sqlQuery = "select c.dealerId as dealerId, c.dealerName as dealerName, v.makeName as makeName, c.dealerType as dealerType, c.dealerStatus as dealerStatus,"
						+ "c.makeId as makeId, c.contactNumber as contactNumber , c.city as city from DealerEntity c left join VehicleMakeEntity v on  c.makeId=v.makeId where c.recordType<>'D' and "
						+ " c.dealerStatus =:status";
				if (Optional.ofNullable(payloadBean.getDealerName()).isPresent()
						&& !payloadBean.getDealerName().equals("")) {
					sqlQuery += " and c.dealerName='" + payloadBean.getDealerName() + "' ";
				}
				if (Optional.ofNullable(payloadBean.getDealerType()).isPresent()
						&& !payloadBean.getDealerType().equals("")) {
					sqlQuery += " and c.dealerType='" + payloadBean.getDealerType() + "' ";
				}
				if (Optional.ofNullable(payloadBean.getMakeId()).isPresent()) {
					sqlQuery += " and c.makeId='" + payloadBean.getMakeId() + "' ";
				}
				sqlQuery += " order by c.dealerId desc ";
				dealerto = (List<DealerTO>) session.createQuery(sqlQuery)
						.setParameter("status", payloadBean.getDealerStatus())
						.setResultTransformer(Transformers.aliasToBean(DealerTO.class)).list();

			} else {

				String sqlQuery = "select c.dealerId as dealerId, c.dealerName as dealerName, v.makeName as makeName, c.dealerType as dealerType, c.dealerStatus as dealerStatus,"
						+ "c.makeId as makeId, c.contactNumber as contactNumber , c.city as city from DealerEntity c left join VehicleMakeEntity v on  c.makeId=v.makeId where c.recordType<>'D'";

				if (Optional.ofNullable(payloadBean.getDealerName()).isPresent()) {
					sqlQuery += " and c.dealerName='" + payloadBean.getDealerName() + "' ";
				}
				if (Optional.ofNullable(payloadBean.getDealerType()).isPresent()) {

					sqlQuery += " and c.dealerType='" + payloadBean.getDealerType() + "' ";
				}
				if (Optional.ofNullable(payloadBean.getMakeId()).isPresent()) {
					// dateto = formatter.format(inquiryBean.getInquiryDate()) + "00:00:00";
					sqlQuery += " and c.makeId='" + payloadBean.getMakeId() + "' ";
				}
				sqlQuery += " order by c.dealerId desc ";

				dealerto = (List<DealerTO>) session.createQuery(sqlQuery)
						// .setParameter("accountId",Long.valueOf(payloadBean.getAccountId()))

						.setResultTransformer(Transformers.aliasToBean(DealerTO.class)).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dealerto;

	}

	@Override
	public List<DealerExecutiveTO> searchdealerExecutiveData(PayloadBean payloadBean) {
		List<DealerExecutiveTO> dealerto = null;

		try {

			Session session = (Session) entityManager.getDelegate();
			if (payloadBean.getStatus().equalsIgnoreCase("Active")
					|| payloadBean.getStatus().equalsIgnoreCase("Inactive")) {
				String sqlQuery = "select c.dealerExecutiveId as dealerExecutiveId, c.dealerExecutiveName as dealerExecutiveName, c.executiveDesignation as executiveDesignation, c.contactNumber as contactNumber,"
						+ "c.altContactNumber as altContactNumber,  c.emailAddress as emailAddress, c.executiveStatus as executiveStatus, c.executiveLocation as executiveLocation "
						+ " from DealerExecutiveEntity c  where c.recordType<>'D' and "
						+ " c.executiveStatus =:status and c.dealerId = :dealerId  order by c.dealerExecutiveId desc";

				dealerto = (List<DealerExecutiveTO>) session.createQuery(sqlQuery)

						.setParameter("status", payloadBean.getStatus())
						.setParameter("dealerId", payloadBean.getUserId())

						.setResultTransformer(Transformers.aliasToBean(DealerExecutiveTO.class)).list();

			} else {

				String sqlQuery = "select c.dealerExecutiveId as dealerExecutiveId, c.dealerExecutiveName as dealerExecutiveName, c.executiveDesignation as executiveDesignation, c.contactNumber as contactNumber,"
						+ "c.altContactNumber as altContactNumber,  c.emailAddress as emailAddress, c.executiveStatus as executiveStatus from DealerExecutiveEntity c  where c.recordType<>'D' and c.dealerId = :dealerId ";

				dealerto = (List<DealerExecutiveTO>) session.createQuery(sqlQuery)
						.setParameter("dealerId", payloadBean.getUserId())

						.setResultTransformer(Transformers.aliasToBean(DealerExecutiveTO.class)).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dealerto;

	}

	
	@Override
	public List<DealerExecutiveTO> searchdealerExecutiveData1(String payloadBean) {
		List<DealerExecutiveTO> dealerto = null;

		try {

			Session session = (Session) entityManager.getDelegate();
			String sqlQuery = "select c.dealerExecutiveId as dealerExecutiveId, c.dealerExecutiveName as dealerExecutiveName, c.executiveDesignation as executiveDesignation, c.contactNumber as contactNumber,"
					+ "c.altContactNumber as altContactNumber,  c.emailAddress as emailAddress, c.executiveStatus as executiveStatus from DealerExecutiveEntity c   where c.executiveStatus=:status  and c.recordType<>'D'";

			dealerto = (List<DealerExecutiveTO>) session.createQuery(sqlQuery)

					.setParameter("status", payloadBean)

					.setResultTransformer(Transformers.aliasToBean(DealerExecutiveTO.class)).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dealerto;

	}

	@Override
	public boolean updateDealer(DealerEntity customerEntity) {
		boolean result = true;
		try {
			// entityManager.merge(siteEntity);
			Session session = (Session) entityManager.getDelegate();
			session.update(customerEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean updateDealerExecutive(DealerExecutiveEntity customerEntity) {
		boolean result = true;
		try {
			// entityManager.merge(siteEntity);
			Session session = (Session) entityManager.getDelegate();
			session.update(customerEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}
	@Override
	public List<DealerBean> editDealer(PayloadBean payloadbean) {
		List<DealerBean> customerTO = null;

		String sqlQuery = "";
		Session session = (Session) entityManager.getDelegate();
		sqlQuery = "Select c.dealerId as dealerId,  c.makeId as makeId,v.makeName as makeName, c.dealerName as dealerName, c.dealerType as dealerType, c.city as city, c.state as state,"
				+ "c.postalCode as postalCode, c.district as district, c.emailAddress as emailAddress, c.contactNumber as contactNumber , c.altContactNumber as altContactNumber "
				+ ", c.address as address, c.dealerStatus as dealerStatus, c.createdDate as createdDate, c.transactionCount as transactionCount from DealerEntity c left join VehicleMakeEntity v on c.makeId=v.makeId where c.dealerId = "
				+ +payloadbean.getId() + " AND c.recordType<>'D' ";
		// + " c.recordType<>'D' ";
		customerTO = (List<DealerBean>) session.createQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(DealerBean.class)).list();
		return customerTO;
	}
	
	
	@Override
	public boolean deleteDealerExecutive(DeleteRecords deleteRecords) {
		boolean result = true;
		String sqlQuery = "";
		try {

			sqlQuery = "update DealerExecutiveEntity c set record_type ='D' where dealerExecutiveId = "
					+ deleteRecords.getId() + "";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(sqlQuery);
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return result = false;
		}
		return result;

	}
	
	@Override
	public boolean isDealerExecutiveNumberExist(String contactNumber) {
		try {
			return entityManager
					.createQuery("select table.contactNumber as contactNumber from DealerExecutiveEntity table where "
							+ " table.contactNumber='" + contactNumber + "' and table.recordType<>'D'")
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
	
	@Override
	public boolean isDealerExecutiveNumberExistWhileUpdate(String contactNumber,Long dealerExecutiveId) {
		try {
			return entityManager
					.createQuery("select table.contactNumber as contactNumber from DealerExecutiveEntity table where "
							+ " table.contactNumber='" + contactNumber + "' and table.dealerExecutiveId!="+dealerExecutiveId+" and table.recordType<>'D'")
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
}
