package com.fuso.enterprise.ots.srv.server.util;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractIptDao<T, PK extends Serializable> extends GenericDaoImpl<T, PK> {

	public AbstractIptDao(Class<T> type) {
		super(type);
	}

	@Override
	@PersistenceContext(unitName = "iptModel")
	public void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}

}