package com.edios.cdf.dao;

import org.hibernate.Session;

import com.edios.cdf.entity.AbstractEntity;

public interface BaseDao<T extends AbstractEntity> {

	public Session getSession();

	void persist(T entity);

	void merge(T entity);

	void delete(T entity);

	//T loadEntity(long id);

}
