package com.edios.cdf.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.edios.cdf.bean.common.ApplicationParameterListBean;
import com.edios.cdf.dao.DisplayDao;
import com.edios.cdf.display.ParameterDropDownTO;
import com.edios.cdf.entity.common.ApplicationParameterValuesEntity;
import com.edios.cdf.entity.to.ApplicationParameterValueTO;

@Repository
public class DisplayDaoImpl extends BaseDaoImpl<ApplicationParameterValuesEntity> implements DisplayDao {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<ParameterDropDownTO> parameterListDropdown(String parameterCode) {
		List<ParameterDropDownTO> applicationParameterListTO = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select apl.parameterListID As parameterListID , apl.parameterListCode As parameterListCode ,apl.parameterListValue As parameterListValue "
					+ "  , apl.parameterListDesc As parameterListDesc,apl.customValue1 as customValue1,apl.customValue2 as customValue2,apl.customValue3 as customValue3 "
					+ " from   ApplicationParameterListEntity apl LEFT JOIN apl.parameterID As ap "
					+ "  where ap.parameterID=(select parameterID from ApplicationParameterEntity a where a.parameterCode=:parameterCode) order by apl.parameterListValue ASC";
			applicationParameterListTO = (List<ParameterDropDownTO>) session.createQuery(sqlQuery)
					.setParameter("parameterCode", parameterCode)
					.setResultTransformer(Transformers.aliasToBean(ParameterDropDownTO.class)).list();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return applicationParameterListTO;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ApplicationParameterValueTO parameterValues(String parameterCode) {
		ApplicationParameterValueTO parameterValueTO = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select apve.parameterValueID As parameterValueID , apve.parameterValue As parameterValue ,apve.parameterBinaryValue As parameterBinaryValue "
					+ "  , apve.parameterValueDescription As parameterValueDescription "
					+ "  , apve.customValue1 As customValue1, apve.customValue2 As customValue2,"
					+ " apve.customValue3 As customValue3, apve.customValue4 As customValue4 "
					+ " from   ApplicationParameterValuesEntity apve LEFT JOIN apve.parameterID As ap "
					+ "  where  ap.parameterCode=:parameterCode";
			parameterValueTO = (ApplicationParameterValueTO) session.createQuery(sqlQuery)
					.setParameter("parameterCode", parameterCode)
					.setResultTransformer(Transformers.aliasToBean(ApplicationParameterValueTO.class)).list().get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return parameterValueTO;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<ApplicationParameterListBean> fetchParameterDetails(String parameterCode) {
		List<ApplicationParameterListBean> applicationParameterListTO = null;
		String sqlQuery = "";
		try {
			Session session = (Session) entityManager.getDelegate();
			sqlQuery = "select apl.parameterListID As parameterListID , apl.parameterListCode As parameterListCode ,apl.parameterListValue As parameterListValue "
					+ "  , apl.parameterListDesc As parameterListDesc, apl.customValue1 As customValue1, apl.customValue2 As customValue2, apl.customValue3 As customValue3,apl.customValue4 As customValue4 from   ApplicationParameterListEntity apl LEFT JOIN apl.parameterID As ap "
					+ "  where ap.parameterID=(select parameterID from ApplicationParameterEntity a where a.parameterCode=:parameterCode) order by apl.parameterListID ASC";
			applicationParameterListTO = (List<ApplicationParameterListBean>) session.createQuery(sqlQuery)
					.setParameter("parameterCode", parameterCode)
					.setResultTransformer(Transformers.aliasToBean(ApplicationParameterListBean.class)).list();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return applicationParameterListTO;
	}

}
