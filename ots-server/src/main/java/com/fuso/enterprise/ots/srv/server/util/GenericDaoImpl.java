package com.fuso.enterprise.ots.srv.server.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;

import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.common.exception.ErrorEnumeration;

@Repository
public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private Class<T> type;

	public GenericDaoImpl(Class<T> type) {
		this.type = type;
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

	public void save(T entity) {
		try {
			entityManager.persist(entity);
			entityManager.flush();
		} catch (OptimisticLockException e) {
			throw new BusinessException(e, ErrorEnumeration.EXCEPTION_IN_OPTIMISTIC_LOCK);
		} catch (RuntimeException e) {
			throw new BusinessException(e, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public T findById(PK id) {
		try {
			return entityManager.find(type, id);
		} catch (RuntimeException e) {
			throw new BusinessException(e, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public Object findByIdAndType(PK id, Class clazz) {
		try {
			return entityManager.find(clazz, id);
		} catch (RuntimeException e) {
			throw new BusinessException(e, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public T saveOrUpdate(T entity) {
		try {
			T newEntity = entityManager.merge(entity);
			entityManager.flush();
			return newEntity;
		} catch (OptimisticLockException e) {
			throw new BusinessException(e, ErrorEnumeration.EXCEPTION_IN_OPTIMISTIC_LOCK);
		} catch (RuntimeException e) {
			throw new BusinessException(e, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public Object saveOrUpdateByType(Object entity) {
		try {
			Object newEntity = entityManager.merge(entity);
			entityManager.flush();
			return newEntity;
		} catch (OptimisticLockException e) {
			throw new BusinessException(e, ErrorEnumeration.EXCEPTION_IN_OPTIMISTIC_LOCK);
		} catch (RuntimeException e) {
			throw new BusinessException(e, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public T update(T entity) throws NoResultException {
		try {
			T newEntity = entityManager.merge(entity);
			entityManager.flush();
			return newEntity;
		} catch (NonUniqueResultException nonUniqueResultException) {
			throw new NonUniqueResultException(nonUniqueResultException.getMessage());
		} catch (NoResultException nores) {
			throw nores;
		} catch (OptimisticLockException e) {
			throw new BusinessException(e, ErrorEnumeration.EXCEPTION_IN_OPTIMISTIC_LOCK);
		} catch (RuntimeException e) {
			throw new BusinessException(e, ErrorEnumeration.SYSTEM_ERROR);
		}

	}

	public void delete(T entity) {
		try {
			entityManager.remove(entity);
			entityManager.flush();
		} catch (OptimisticLockException e) {
			throw new BusinessException(e, ErrorEnumeration.EXCEPTION_IN_OPTIMISTIC_LOCK);
		} catch (RuntimeException e) {
			throw new BusinessException(e, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public void detach(T entity) {
		try {
			entityManager.detach(entity);
			entityManager.flush();
		} catch (RuntimeException e) {
			throw new BusinessException(e, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public T getResultByNamedQuery(String queryName, Map<String, Object> queryParameters) {
		try {
			TypedQuery<T> query = entityManager.createNamedQuery(queryName, type);
			addQueryParameters(query, queryParameters);
			return query.getSingleResult();
		} catch (NoResultException nores) {
			throw nores;
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public <RT> RT getResultByNamedQuery(String queryName, Map<String, Object> queryParameters, Class<RT> rtType) {
		try {
			TypedQuery<RT> query = entityManager.createNamedQuery(queryName, rtType);
			addQueryParameters(query, queryParameters);
			return query.getSingleResult();
		} catch (NonUniqueResultException nonUniqueResultException) {
			throw new NonUniqueResultException(nonUniqueResultException.getMessage());
		} catch (NoResultException nores) {
			throw nores;
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}

	}

	public List<T> getResultListByNamedQuery(String queryName, Map<String, Object> queryParameters) {
		try {
			TypedQuery<T> query = entityManager.createNamedQuery(queryName, type);
			addQueryParameters(query, queryParameters);
			return query.getResultList();
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public T getResultByCriteria(CriteriaQuery<T> criteriaQuery) {
		try {
			TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
			return query.getSingleResult();
		} catch (NoResultException nores) {
			throw nores;
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public T getResultByCriteria(CriteriaQuery<T> criteriaQuery, Map<ParameterExpression, Object> bindParametersMap) {
		try {
			TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
			if (null != bindParametersMap && bindParametersMap.size() > 0) {
				TypedQuery<T> updatedQuery = query;
				bindParametersMap.entrySet().stream()
						.forEach(entry -> updatedQuery.setParameter(entry.getKey(), entry.getValue()));
			}
			return query.getSingleResult();
		} catch (NoResultException nores) {
			throw nores;
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public List<T> getResultListByCriteria(CriteriaQuery<T> criteriaQuery) {
		try {
			TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
			return query.getResultList();
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public Object getResultByNativeQuery(String nativeQueryString, Map<String, Object> queryParameters, int maxResult) {
		try {
			Query query = entityManager.createNativeQuery(nativeQueryString);
			addQueryParameters(query, queryParameters);
			if (maxResult > 0) {
				query.setMaxResults(maxResult);
			}
			return query.getSingleResult();
		} catch (NoResultException nores) {
			throw nores;
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public int executeNamedQuery(String queryName, Map<String, Object> queryParameters) {
		try {
			Query query = entityManager.createNamedQuery(queryName);
			addQueryParameters(query, queryParameters);

			return query.executeUpdate();
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	private void addQueryParameters(Query query, Map<String, Object> queryParameters) {
		if (queryParameters != null && !queryParameters.isEmpty()) {
			Set<Map.Entry<String, Object>> queryParametersMapKeySet = queryParameters.entrySet();
			for (Map.Entry<String, Object> iter : queryParametersMapKeySet) {
				query.setParameter(iter.getKey(), iter.getValue());
			}
		}

	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void flush() {
		try {
			entityManager.flush();
		} catch (RuntimeException e) {
			throw new BusinessException(e, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public long countByNamedQuery(String queryName, Map<String, Object> queryParameters) {
		try {
			Query query = entityManager.createNamedQuery(queryName, Long.class);
			addQueryParameters(query, queryParameters);
			return (Long) query.getSingleResult();
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}

	}

	public <R> List<R> getResultListByNamedQuery(String queryName, Class<R> rtType) {
		try {
			TypedQuery<R> query = entityManager.createNamedQuery(queryName, rtType);
			return query.getResultList();
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	public String executeStoredProcedure(String storedProcedureName, Map<String, Object> procedureParameters) {

		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(storedProcedureName);

		addQueryParameters(query, procedureParameters);

		query.execute();

		return "";
	}

	@Override
	public Object getResultByNativeQuery(String nativeQueryString, Map<String, Object> queryParameters) {
		try {
			Query query = entityManager.createNativeQuery(nativeQueryString);
			addQueryParameters(query, queryParameters);
			return query.getSingleResult();
		} catch (NoResultException nores) {
			throw nores;
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	@Override
	public List<Object> getResultListByNativeQuery(String nativeQueryString, Map<String, Object> queryParameters,
			int maxResult) {
		try {
			Query query = entityManager.createNativeQuery(nativeQueryString);
			addQueryParameters(query, queryParameters);
			if (maxResult > 0) {
				query.setMaxResults(maxResult);
			}
			return query.getResultList();
		} catch (NoResultException nores) {
			throw nores;
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	@Override
	public int updateByNativeQuery(String nativeQueryString, Map<String, Object> queryParameters) {
		Query query = entityManager.createNativeQuery(nativeQueryString);
		addQueryParameters(query, queryParameters);
		try {
			return query.executeUpdate();
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}

	@Override
	public String getIdByNamedQuery(String queryName, Map<String, Object> queryParameters) {
		try {
			Query query = entityManager.createNamedQuery(queryName, String.class);
			addQueryParameters(query, queryParameters);
			return (String) query.getSingleResult();
		} catch (NoResultException nores) {
			throw nores;
		} catch (RuntimeException exp) {
			throw new BusinessException(exp, ErrorEnumeration.SYSTEM_ERROR);
		}
	}
}
