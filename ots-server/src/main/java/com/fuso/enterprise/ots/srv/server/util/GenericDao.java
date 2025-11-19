package com.fuso.enterprise.ots.srv.server.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;

public interface GenericDao<T, PK extends Serializable> {

	void save(T entity);

	T findById(PK id);

	Object findByIdAndType(PK id, Class clazz);

	Object saveOrUpdateByType(Object entity);

	T update(T entity);

	T saveOrUpdate(T entity);

	void delete(T entity);

	void detach(T entity);

	T getResultByNamedQuery(String queryName, Map<String, Object> queryParameters);

	<RT> RT getResultByNamedQuery(String queryName, Map<String, Object> queryParameters, Class<RT> resultType);

	List<T> getResultListByNamedQuery(String queryName, Map<String, Object> queryParameters);

	T getResultByCriteria(CriteriaQuery<T> criteriaQuery);

	T getResultByCriteria(CriteriaQuery<T> criteriaQuery, Map<ParameterExpression, Object> bindParametersMap);

	List<T> getResultListByCriteria(CriteriaQuery<T> criteriaQuery);

	Object getResultByNativeQuery(String nativeQueryString, Map<String, Object> queryParameters, int maxResult);

	long countByNamedQuery(String queryName, Map<String, Object> queryParameters);

	String executeStoredProcedure(String storedProcedureName, Map<String, Object> queryParameters);

	List<Object> getResultListByNativeQuery(String nativeQueryString, Map<String, Object> queryParameters,
			int maxResult);

	int updateByNativeQuery(String nativeQueryString, Map<String, Object> queryParameters);

	Object getResultByNativeQuery(String nativeQueryString, Map<String, Object> queryParameters);

	String getIdByNamedQuery(String queryName, Map<String, Object> queryParameters);

}
