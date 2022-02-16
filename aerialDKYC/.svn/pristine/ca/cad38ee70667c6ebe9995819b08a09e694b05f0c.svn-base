package com.edios.cdf.dao.impl;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.edios.cdf.dao.ParameterValueDao;
import com.edios.cdf.entity.common.ApplicationParameterValuesEntity;
import com.edios.cdf.util.TransactionData;

@Repository
public class ParameterValueDaoImpl extends BaseDaoImpl<ApplicationParameterValuesEntity> implements ParameterValueDao {

	@Override
	public ApplicationParameterValuesEntity fetchParameterValueByParameterId(Long parameterID) {
		ApplicationParameterValuesEntity applicationParameterListTO = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "from  ApplicationParameterValuesEntity aple left join fetch aple.parameterID as p  where p.parameterID=:parameterID ";
			applicationParameterListTO = (ApplicationParameterValuesEntity) session.createQuery(sqlQuery)
					.setParameter("parameterID", parameterID).uniqueResult();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return applicationParameterListTO;
	}

	@Override
	public boolean addParameterValue(ApplicationParameterValuesEntity applicationParameterValuesEntity) {
		boolean result = true;
		try {
			entityManager.persist(applicationParameterValuesEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	@Override
	public TransactionData fetchTransactionDataById(Long parameterValueID) {
		TransactionData transactionData = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select apve.transactionCount as transactionCount,apve.recordType as recordType "
					+ " from  ApplicationParameterValuesEntity apve  "
					+ " where apve.parameterValueID=:parameterValueID";
			transactionData = (TransactionData) session.createQuery(sqlQuery)
					.setParameter("parameterValueID", parameterValueID)
					.setResultTransformer(Transformers.aliasToBean(TransactionData.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return transactionData;
	}

	@Override
	public boolean updateParameterValue(ApplicationParameterValuesEntity applicationParameterValuesEntity) {
		boolean result = true;
		try {
			entityManager.merge(applicationParameterValuesEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return result;
	   }

}
