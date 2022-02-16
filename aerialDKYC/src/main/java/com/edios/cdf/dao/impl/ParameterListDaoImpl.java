package com.edios.cdf.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.ParameterListDao;
import com.edios.cdf.entity.common.ApplicationParameterEntity;
import com.edios.cdf.entity.common.ApplicationParameterListEntity;
import com.edios.cdf.entity.to.ApplicationParameterListTO;
import com.edios.cdf.util.TransactionData;

@Repository
public class ParameterListDaoImpl extends BaseDaoImpl<ApplicationParameterListEntity> implements ParameterListDao {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<ApplicationParameterListTO> fetchParameterListByParameterId(Long parameterID) {
		List<ApplicationParameterListTO> applicationParameterListTO = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();

			sqlQuery = "select aple.parameterListID As parameterListID ,aple.parameterListCode As parameterListCode , aple.parameterListValue As parameterListValue,"
					+ " ape.parameterID As parameterID ,aple.parameterListStatus as parameterListStatus,aple.transactionCount as transactionCount"
					+ " from  ApplicationParameterListEntity aple  LEFT JOIN aple.parameterID as ape "
					+ " where aple.recordType<>'D' and ape.parameterID=:parameterID order by aple.parameterListID asc";
			applicationParameterListTO = (List<ApplicationParameterListTO>) session.createQuery(sqlQuery)
					.setParameter("parameterID", parameterID)
					.setResultTransformer(Transformers.aliasToBean(ApplicationParameterListTO.class)).list();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return applicationParameterListTO;
	}

	@Override
	public boolean addParameterList(ApplicationParameterListEntity applicationParameterListEntity) {
		boolean result = true;
		try {
			entityManager.persist(applicationParameterListEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean getAppParameterListNameExists(Long parameterID, String parameterListValue) {
		try {
			return entityManager.createQuery(
					"select table.parameterListValue as parameterListValue from ApplicationParameterListEntity table where "
							+ " table.parameterListValue='" + parameterListValue
							+ "' AND table.parameterID.parameterID=" + parameterID)
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@Override
	public boolean getAppParameterListCodeExists(Long parameterID, String parameterCode) {
		try {
			return entityManager.createQuery(
					"select table.parameterListCode as parameterListCode from ApplicationParameterListEntity table where table.parameterListCode='"
							+ parameterCode + "' AND table.parameterID.parameterID=" + parameterID)
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@Override
	public ApplicationParameterListEntity findApplicationParameterListById(Long id) {
		try {
			return entityManager.find(ApplicationParameterListEntity.class, id);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean getAppParameterListNameExists(Long parameterID, String parameterListValue, Long parameterListID) {
		try {
			return entityManager.createQuery(
					"select table.parameterListValue as parameterListValue from ApplicationParameterListEntity table where "
							+ " table.parameterListValue='" + parameterListValue
							+ "' AND table.parameterID.parameterID=" + parameterID + " and table.parameterListID!="
							+ parameterListID)
					.getSingleResult() == null ? false : true;
		} catch (NoResultException ex) {
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}

	@Override
	public boolean updateParameterList(ApplicationParameterListEntity applicationParameterListEntity) {
		boolean result = true;
		try {
		//	entityManager.merge(applicationParameterListEntity);
			Session session = (Session) entityManager.getDelegate();
			session.update(applicationParameterListEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean deleteParameterList(Long deleteRecordId, Integer lastModifiedBy) {
		boolean result = true;
		try {
			String hql = "update ApplicationParameterListEntity aple set aple.lastModifiedDate=:lastModifiedDate"
					+ " , aple.recordType='D' , aple.lastModifiedBy=:lastModifiedBy"
					+ " WHERE aple.parameterListID=:deleteRecordId";

			Session session = (Session) entityManager.getDelegate();
			Query query = session.createQuery(hql);
			query.setParameter("lastModifiedBy", lastModifiedBy);
			query.setParameter("lastModifiedDate", new Date(), TemporalType.TIMESTAMP);
			query.setParameter("deleteRecordId", deleteRecordId);
			int resultID = query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public TransactionData fetchTransactionDataById(Long parameterListID) {
		TransactionData transactionData = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select aple.transactionCount as transactionCount,aple.recordType as recordType "
					+ " from  ApplicationParameterListEntity aple  "
					+ " where aple.parameterListID=:parameterListID";

			transactionData = (TransactionData) session.createQuery(sqlQuery)
					.setParameter("parameterListID", parameterListID)
					.setResultTransformer(Transformers.aliasToBean(TransactionData.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

}
