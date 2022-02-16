package com.edios.csr.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.impl.BaseDaoImpl;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.util.TransactionData;
import com.edios.csr.dao.StaffDao;
import com.edios.csr.entity.StaffEntity;
import com.edios.csr.entity.to.StaffTo;

@SuppressWarnings({ "unchecked", "deprecation" })
@Repository
public class StaffDaoImpl extends BaseDaoImpl<StaffEntity> implements StaffDao {

	@Override
	public List<StaffTo> fetchSearchStaff(PayloadBean payloadBean) {

		List<StaffTo> staffEntityList = null;
		String sqlQuery = "";
		try {

			Session session = (Session) entityManager.getDelegate();

			if (payloadBean.getStatus().equalsIgnoreCase("Active")
					|| payloadBean.getStatus().equalsIgnoreCase("Inactive")) {
				sqlQuery = "select staff.staffId as staffId ,staff.mobileAccess as mobileAccess,staff.emailAddress as emailAddress"
						+ ", concat(staff.firstName , ' ' , COALESCE(staff.lastName)) as staffName ,staff.staffCode as staffCode,"
						+ " staff.staffType as staffType, staff.contactNumber as contactNumber, staff.designation as designation, "
						+ " site.siteName as siteName from StaffEntity staff  "
						+ " left join SiteEntity site on (site.siteID=staff.siteId) "
						+ " where staff.recordType<>'D' and staff.status='" + payloadBean.getStatus()
						+ "' and staff.accountId=:accountId and staff.siteId=:siteId  order by staff.staffId desc";
				staffEntityList = (List<StaffTo>) session.createQuery(sqlQuery)
						.setParameter("accountId", Long.valueOf(payloadBean.getAccountId()))
						.setParameter("siteId", payloadBean.getUserId())
						.setResultTransformer(Transformers.aliasToBean(StaffTo.class)).list();
			} else {
				sqlQuery = "select staff.staffId as staffId , concat(staff.firstName , ' ' , COALESCE(staff.lastName)) as staffName ,staff.staffCode as staffCode,staff.emailAddress as emailAddress,"
						+ " staff.mobileAccess as mobileAccess, staff.staffType as staffType, staff.contactNumber as contactNumber, "
						+ " staff.designation as designation,site.siteName as siteName from StaffEntity staff "
						+ " left join SiteEntity site on (site.siteID=staff.siteId) "
						+ " where staff.recordType<>'D' and staff.accountId=:accountId and staff.siteId=:siteId "
						+ " order by staff.staffId desc";
				staffEntityList = (List<StaffTo>) session.createQuery(sqlQuery)
						.setParameter("accountId", Long.valueOf(payloadBean.getAccountId()))
						.setParameter("siteId", payloadBean.getUserId())
						.setResultTransformer(Transformers.aliasToBean(StaffTo.class)).list();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return staffEntityList;

	}

	@Override
	public List<StaffTo> fetchLeadStaff(PayloadBean payloadBean) {

		List<StaffTo> staffEntityList = null;
		String sqlQuery = "";
		try {

			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select staff.staffId as staffLeaderID , concat(staff.firstName , ' ' , COALESCE(staff.lastName)) as staffName from StaffEntity staff "
					+ " where staff.recordType<>'D' and staff.status='Active' and staff.accountId=:accountId  order by staff.firstName Asc";
			staffEntityList = (List<StaffTo>) session.createQuery(sqlQuery)
//					.setParameter("siteId", payloadBean.getId())
					.setParameter("accountId", Long.parseLong(""+payloadBean.getAccountId()))
					.setResultTransformer(Transformers.aliasToBean(StaffTo.class)).list();

		} catch (Exception e) {
			e.printStackTrace();

		}

		return staffEntityList;

	}

	@Override
	public boolean addStaff(StaffEntity staffEntity) {
		boolean result = true;
		try {
			if (Optional.ofNullable(staffEntity.getAadharNo()).isPresent()) {
				staffEntity.setAadharNo(staffEntity.getAadharNo().replaceAll("-", ""));
				}
			entityManager.persist(staffEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public StaffEntity findStaffById(Long id) {
		StaffEntity staffEntity = null;
		try {
			entityManager.getDelegate();
			staffEntity = entityManager.find(StaffEntity.class, id);
		} catch (NoResultException noResultException) {
			noResultException.printStackTrace();
			return null;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return staffEntity;
	}

	@Override
	public boolean updateStaff(StaffEntity staffEntity) {
		boolean result = true;
		try {
			if (Optional.ofNullable(staffEntity.getAadharNo()).isPresent()) {
				staffEntity.setAadharNo(staffEntity.getAadharNo().replaceAll("-", ""));
				}
			// entityManager.merge(siteEntity);
			Session session = (Session) entityManager.getDelegate();
			session.update(staffEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public TransactionData fetchTransactionDataById(Long staffId) {
		TransactionData transactionData = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select table.transactionCount as transactionCount,table.recordType as recordType "
					+ " from  StaffEntity table where table.staffId=:staffId";
			transactionData = (TransactionData) session.createQuery(sqlQuery).setParameter("staffId", staffId)
					.setResultTransformer(Transformers.aliasToBean(TransactionData.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

	@Override
	public boolean isStaffExists(Long id) {
		try {
			return entityManager
					.createQuery(
							"select table.accountUserID  from AccountUserEntity table where " + " table.siteID.siteID="
									+ id + " and table.siteID.recordType<>'D' and table.recordType<>'D'")
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@Override
	public boolean deleteStaff(Long deleteRecordId, Long lastModifiedBy) {
		boolean result = true;
		int resultForCheck = checkStaffRecordIsUsed(deleteRecordId);
		if (resultForCheck != 0) {
			return false;
		}
		try {
			String hql = "update StaffEntity se set " + "  se.recordType='D' , se.lastModifiedBy=:lastModifiedBy"
					+ " WHERE se.staffId=:deleteRecordId";
			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("lastModifiedBy", lastModifiedBy);
			// query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("deleteRecordId", deleteRecordId);
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return result;
	}

	private int checkStaffRecordIsUsed(Long id) {
		int result = 0;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select i.staffId  from  InquiriesEntity i where i.sourceStaffId=" + id + ""
					+ " or i.assignedStaffId=" + id + "  or i.staffId= " + id + " ";
			result = session.createQuery(sqlQuery).list().size();
		} catch (Exception exception) {
			exception.printStackTrace();
			return result;
		}

		return result;
	}
	
	@Override
	public String findStaffCodeByStaffType(String staffType) {
		
			List<String> staffCodeList = entityManager
			.createQuery("select MAX(table.staffCode) as staffCode from StaffEntity table where "
					+ " table.staffType='" + staffType + "' ").getResultList();
			
			if(!staffCodeList.isEmpty())
				return staffCodeList.get(0);
					
			return null;
		
	}
	
	@Override
	public boolean isStaffCodeExistWhileUpdate(String staffCode,Long staffId) {
		try {
			return entityManager
					.createQuery("select table.staffCode as chargeCode from StaffEntity table where "
							+ " table.staffCode='" + staffCode + "' and table.staffId!="+staffId+" and table.recordType<>'D'")
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@Override
	public boolean isContactNoExist(String contactNumber,Long staffId) {
		try {
			String sqlQuery = "select table.contactNumber as contactNumber from StaffEntity table where "
					+ " table.contactNumber='" + contactNumber + "' and table.recordType<>'D'";
			
			if(staffId != null)
				sqlQuery += " and table.staffId!="+staffId;
			
			return entityManager
					.createQuery(sqlQuery)
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
	@Override
	public String findSiteById(Long siteId) {
		String sqlQuery = null;
		
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = " Select site.siteName from  SiteEntity site  "
					+ " where site.siteID=:id and site.recordType<>'D' ";
			
			
			List<String> siteName = (List<String>) session.createQuery(sqlQuery).setParameter("id",siteId)
					.list();
			
			if(siteName.isEmpty())
				return null;
			else
				return siteName.get(0);
		}
		catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
}
