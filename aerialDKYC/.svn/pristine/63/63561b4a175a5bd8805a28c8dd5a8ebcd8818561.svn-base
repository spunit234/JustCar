package com.edios.cdf.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.ApplicationParameterDao;
import com.edios.cdf.entity.common.ApplicationParameterEntity;
import com.edios.cdf.entity.to.ApplicationParameterTO;
import com.edios.cdf.util.TransactionData;

@Repository
public class ApplicationParameterDaoImpl extends BaseDaoImpl<ApplicationParameterEntity>
		implements ApplicationParameterDao {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<ApplicationParameterTO> fetchAppParamDetails() {
		List<ApplicationParameterTO> applicationParameterTO = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select apl.parameterID As parameterID , apl.parameterName As parameterName , apl.parameterCode As parameterCode, "
					+ " apl.transactionCount as transactionCount,apl.parameterStatus As parameterStatus,"
					+ " apl.parameterType As parameterType, apl.parameterDataType As parameterDataType "
					+ " from  ApplicationParameterEntity apl "
					+ " where apl.recordType<>'D' and apl.parameterStatus=:parameterStatus order by apl.parameterName asc";

			applicationParameterTO = (List<ApplicationParameterTO>) session.createQuery(sqlQuery)
					.setParameter("parameterStatus", "Active")
					.setResultTransformer(Transformers.aliasToBean(ApplicationParameterTO.class)).list();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return applicationParameterTO;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<ApplicationParameterTO> fetchAppParameterDetails(String parameterStatus) {
		List<ApplicationParameterTO> applicationParameterTO = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			if (parameterStatus.equalsIgnoreCase("Active") || parameterStatus.equalsIgnoreCase("InActive")) {
				sqlQuery = "select apl.parameterID As parameterID , apl.parameterName As parameterName , apl.parameterCode As parameterCode, "
						+ " apl.transactionCount as transactionCount,apl.parameterStatus As parameterStatus,"
						+ " apl.parameterType As parameterType, apl.parameterDataType As parameterDataType "
						+ " from  ApplicationParameterEntity apl "
						+ " where apl.recordType<>'D' and apl.parameterStatus=:parameterStatus order by apl.parameterName asc";

				applicationParameterTO = (List<ApplicationParameterTO>) session.createQuery(sqlQuery)
						.setParameter("parameterStatus", parameterStatus)
						.setResultTransformer(Transformers.aliasToBean(ApplicationParameterTO.class)).list();
			} else {
				sqlQuery = "select apl.parameterID As parameterID , apl.parameterName As parameterName , apl.parameterCode As parameterCode, "
						+ " apl.transactionCount as transactionCount,apl.parameterStatus As parameterStatus,"
						+ " apl.parameterType As parameterType, apl.parameterDataType As parameterDataType "
						+ " from  ApplicationParameterEntity apl "
						+ " where apl.recordType<>'D' order by apl.parameterName asc";
				applicationParameterTO = (List<ApplicationParameterTO>) session.createQuery(sqlQuery)
						.setResultTransformer(Transformers.aliasToBean(ApplicationParameterTO.class)).list();
			}
		} catch (NoResultException noResultException) {
			return null;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return applicationParameterTO;
	}

	@Override
	public ApplicationParameterEntity findApplicationParameterById(Long parameterID) {
		ApplicationParameterEntity applicationParameterEntity = null;
		try {
			applicationParameterEntity = entityManager.find(ApplicationParameterEntity.class, parameterID);
		} catch (NoResultException noResultException) {
			return null;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return applicationParameterEntity;
	}

	@Override
	public boolean addParameter(ApplicationParameterEntity applicationParameterEntity) {
		boolean result = true;
		try {
			entityManager.persist(applicationParameterEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean updateParameter(ApplicationParameterEntity applicationParameterEntity) {
		boolean result = true;
		try {
			//entityManager.merge(applicationParameterEntity);
			Session session = (Session) entityManager.getDelegate();
			session.update(applicationParameterEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean deleteParameter(Long deleteRecordId, Integer lastModifiedBy) {
		boolean result = true;
		try {
			String hql = "update ApplicationParameterEntity ape set ape.lastModifiedDate=:lastModifiedDate"
					+ " , ape.recordType='D' , ape.lastModifiedBy=:lastModifiedBy"
					+ " WHERE ape.parameterID=:deleteRecordId";

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

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public boolean getAppParameterNameExists(String parameterName) {
		try {
			List<ApplicationParameterEntity> list = null;
			Session session = (Session) entityManager.getDelegate();
			list = session.createQuery(
					"select table.parameterName from ApplicationParameterEntity table where table.parameterName=:parameterName")
					.setParameter("parameterName", parameterName)
					.setResultTransformer(Transformers.aliasToBean(ApplicationParameterEntity.class)).list();
			if (list.size() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public boolean getAppParameterCodeExists(String parameterCode) {
		try {
			List<ApplicationParameterEntity> list = null;
			Session session = (Session) entityManager.getDelegate();
			list = session.createQuery(
					"select table.parameterName from ApplicationParameterEntity table where table.parameterCode=:parameterCode")
					.setParameter("parameterCode", parameterCode)
					.setResultTransformer(Transformers.aliasToBean(ApplicationParameterEntity.class)).list();
			if (list.size() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public boolean getAppParameterNameExists(String parameterName, Long parameterID) {
		try {
			List<ApplicationParameterEntity> list = null;
			Session session = (Session) entityManager.getDelegate();
			list = session.createQuery(
					"select table.parameterName from ApplicationParameterEntity table where table.parameterName=:parameterName and table.parameterID!=:parameterID")
					.setParameter("parameterName", parameterName).setLong("parameterID", parameterID)
					.setResultTransformer(Transformers.aliasToBean(ApplicationParameterEntity.class)).list();
			if (list.size() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public boolean isAppParameterCodeExists(String parameterCode, Long parameterID) {
		try {
			List<ApplicationParameterEntity> list = null;
			Session session = (Session) entityManager.getDelegate();
			list = session.createQuery(
					"select table.parameterName from ApplicationParameterEntity table where table.parameterCode=:parameterCode and table.parameterID!=:parameterID")
					.setParameter("parameterCode", parameterCode).setLong("parameterID", parameterID)
					.setResultTransformer(Transformers.aliasToBean(ApplicationParameterEntity.class)).list();
			if (list.size() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public TransactionData fetchTransactionDataById(Long parameterID) {
		TransactionData transactionData = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select apl.transactionCount as transactionCount,apl.recordType as recordType "
					+ " from  ApplicationParameterEntity apl  " + " where apl.parameterID=:parameterID";

			transactionData = (TransactionData) session.createQuery(sqlQuery).setParameter("parameterID", parameterID)
					.setResultTransformer(Transformers.aliasToBean(TransactionData.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

}
