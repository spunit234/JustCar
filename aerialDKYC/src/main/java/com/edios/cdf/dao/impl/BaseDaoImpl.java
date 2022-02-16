package com.edios.cdf.dao.impl;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.dao.BaseDao;
import com.edios.cdf.entity.AbstractEntity;

public abstract class BaseDaoImpl<T extends AbstractEntity> implements BaseDao<T> {

	//@Qualifier("entityManagerFactory")
	protected EntityManager entityManager;

	@PersistenceContext(unitName="mysqlDBUnit")
	public void setEntityManager(@Qualifier("entityManagerFactory")EntityManager em) {
		this.entityManager = em;
	}

	@Override
	public Session getSession() {
		return (Session) entityManager.getDelegate();
	}

	@Override
	@Transactional(propagation = MANDATORY)
	public void persist(T entity) {
		entityManager.persist(entity);
	}

	@Override
	@Transactional(propagation = MANDATORY)
	public void merge(T entity) {
		entityManager.merge(entity);
	}

	@Override
	@Transactional(propagation = MANDATORY)
	public void delete(T entity) {
		entityManager.remove(entity);
	}

	public void closeEntityManager() {
		if (entityManager != null) {
			entityManager.close();
		}
	}
}
