package com.edios.cdf.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.entity.AbstractEntity;
import com.edios.cdf.manager.AbstractManager;

public abstract class AbstractManagerImpl<T extends AbstractBean, P extends AbstractEntity> implements AbstractManager {

	protected static Mapper mapper = new DozerBeanMapper();

	protected List<T> mapList(List<P> entityList, Class<T> destinationClass) {
		List<T> toList = new ArrayList<T>();

		if (null != entityList) {
			for (P entity : entityList) {
				toList.add((T) mapper.map(entity, destinationClass));
			}
		}

		return toList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<? extends AbstractBean> mapListx(List<? extends AbstractEntity> entityList, Class destinationClass) {
		List<T> toList = new ArrayList<T>();

		if (null != entityList) {
			for (AbstractEntity entity : entityList) {
				toList.add((T) mapper.map(entity, destinationClass));
			}
		}

		return toList;
	}

	
	
}
