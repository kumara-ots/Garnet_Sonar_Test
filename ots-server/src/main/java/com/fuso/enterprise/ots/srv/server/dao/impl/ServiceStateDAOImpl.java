package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceState;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ServiceStateDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceState;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Service
@Transactional
public class ServiceStateDAOImpl extends AbstractIptDao<OtsServiceState, String> implements ServiceStateDAO {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public ServiceStateDAOImpl() {
		super(OtsServiceState.class);
	}
	
	private ServiceState convertStateDetailsFromEntityToDomain(OtsServiceState otsServiceState) {
		ServiceState serviceStateDetails = new ServiceState();
		serviceStateDetails.setOtsStateId((otsServiceState.getOtsStateId()== null) ? null: otsServiceState.getOtsStateId().toString());
		serviceStateDetails.setOtsStateName((otsServiceState.getOtsStateName()== null) ? null: otsServiceState.getOtsStateName());
		serviceStateDetails.setOtsStateStatus((otsServiceState.getOtsStateStatus()== null) ? null: otsServiceState.getOtsStateStatus());
		return serviceStateDetails;
	}
	
	@Override
	public List<ServiceState> getAllStates() {
		List<ServiceState> stateList = new ArrayList<ServiceState>();
		List<OtsServiceState> otsServiceState = new ArrayList<OtsServiceState>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			otsServiceState = super.getResultListByNamedQuery("OtsServiceState.findAll", queryParameter);
			stateList = otsServiceState.stream().map(serviceState -> convertStateDetailsFromEntityToDomain(serviceState)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return stateList;
	}
}
